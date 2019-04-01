package lesson4.hw1.service;

import lesson4.hw1.Exception.ExistFileException;
import lesson4.hw1.Exception.OverloadSizeException;
import lesson4.hw1.Exception.UnsupportedFormatException;
import lesson4.hw1.dao.FileDAO;
import lesson4.hw1.entity.File;
import lesson4.hw1.entity.Storage;

import java.util.List;

public class FileService {
    FileDAO fileDAO = new FileDAO();
    StorageServise storageServise = new StorageServise();

    /*
     добавляет файл в хранилище
     если  файл уже есть в системе (с дефолтным хранилищем )- то просто изменяет  ИД хранилища.
     если файла нету - добавляет с нужным ИД хранилища
     */
    public File put(Storage storage, File file) throws UnsupportedFormatException, OverloadSizeException, ExistFileException {
        checkIfFileIsAlreadyInStorage(storage, file);
        checkIfFormatSupported(storage, file);
        checkIfSizeAviable(storage, file);
        if (checkIfFileExistinSystem(file)) {
            if (checkIfFileExistInStorage(storage, file)) {
                throw new ExistFileException("File ID : " + file.getId() + " not added to Storage ID : " + storage.getId() + " by reason : file is already present in Storage");
            }
// файл уже есть в сисмеме но с дефолтным ИД хранилища.  меняем ИД хранилища на нужное.
            fileDAO.update(file, storage);
        } else {
// файла нету в системе. добавляем.
            fileDAO.save(file, storage);
        }
        return file;
    }

    private void checkIfFileIsAlreadyInStorage(Storage storage, File file) throws ExistFileException {
        if (file.getStorage() == null) {
            return;
        }
        if (storage.getId() == file.getStorage().getId()) {
            throw new ExistFileException("File ID: " + file.getId() + " is already present in Storage ID : " + storage.getId());
        }
    }


    /*
    проверяет есть ли файл в системе (независимо или в конкретном хранилище или дефолтном хранилище)
     */
    private boolean checkIfFileExistinSystem(File file) {
        List<File> allFilesInStorage = fileDAO.getAllFilesInSystem();
        if (allFilesInStorage != null) {
            for (File fileInStorage : allFilesInStorage) {
                if (fileInStorage.equals(file)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
        проверяет есть ли файл в конкретном хранилище
         */
    private boolean checkIfFileExistInStorage(Storage storage, File file) {
        List<File> allFilesInStorage = fileDAO.getAllFilesInStorageById(storage.getId());
        File file1 = fileDAO.findByID(file.getId());
        if (allFilesInStorage != null) {
            for (File fileInStorage : allFilesInStorage) {
                if (fileInStorage.equals(file)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void checkIfSizeAviable(Storage storage, File file) throws OverloadSizeException {
        List<File> allFilesInStorage = fileDAO.getAllFilesInStorageById(storage.getId());
        long sizeAllFilesInStorage = 0;
        if (allFilesInStorage != null) {
            for (File fileInStorage : allFilesInStorage) {
                sizeAllFilesInStorage += fileInStorage.getSize();
            }
        }

        if (storage.getStorageMaxSize() < sizeAllFilesInStorage + file.getSize()) {
            throw new OverloadSizeException("File ID : " + file.getId() + " not added to Storage ID : " + storage.getId() + " by reason : overload size of Storage");
        }

    }

    private boolean checkIfFormatSupported(Storage storage, File file) throws UnsupportedFormatException {

        String[] formatsStorageSupported = storage.getFormatsSupported();
        for (String format : formatsStorageSupported) {
            if (format.equals(file.getFormat())){
                return true;
            }
        }
                throw new UnsupportedFormatException("File ID : " + file.getId() + " not added to Storage ID : " + storage.getId() + " by reason : format unsupported ");
        }



    public void putAll(Storage storage, List<File> files) throws ExistFileException, OverloadSizeException, UnsupportedFormatException {
        for (File f : files) {
            checkIfFileIsAlreadyInStorage(storage, f);
            checkIfFormatSupported(storage, f);
            checkIfSizeAviable(storage, f);
            if (!checkIfFileExistinSystem(f)) {
                throw new ExistFileException("File ID : " + f.getId() + " not found in DB.");
            }
            if (checkIfFileExistInStorage(storage, f)) {
                throw new ExistFileException("File ID : " + f.getId() + " not added to Storage ID : " + storage.getId() + " by reason : file is already present in Storage");
            }
        }
        fileDAO.putAll(storage, files);
    }

    public long delete(Storage storage, File file) throws ExistFileException {
        if (!checkIfFileExistInStorage(storage, file))
            throw new ExistFileException("File ID : " + file.getId() + " not deleted from Storage ID : " + storage.getId() + " by reason : file in Storage does not exist");

        fileDAO.delete(file.getId());
        return file.getId();
    }


    public long transferFile(Storage storageFrom, Storage storageTo, long id) throws ExistFileException, UnsupportedFormatException, OverloadSizeException {
        File file = fileDAO.findByID(id);
        // checkers
        if (!checkIfFileExistInStorage(storageServise.findStorageByID(storageFrom.getId()), file)) {
            throw new ExistFileException(" File id : " + id + "not found in Storage id : " + storageFrom.getId());
        }
        if (checkIfFileExistInStorage(storageServise.findStorageByID(storageTo.getId()), file)) {
            throw new ExistFileException(" File id : " + id + "is currently present in Storage id : " + storageTo.getId());
        }
        checkIfFormatSupported(storageServise.findStorageByID(storageTo.getId()), file);
        checkIfSizeAviable(storageServise.findStorageByID(storageTo.getId()), file);

        // в БД заменяем ИД стореджа
        return fileDAO.transferFile(storageTo, file);
    }

    /*
    сохраняет файл в системе (без указания хранилища.)
     */
    public long saveFileInSystem(File file) {
        fileDAO.save(file, null);
        return file.getId();
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws UnsupportedFormatException, ExistFileException, OverloadSizeException {
        List<File> filesStorageFrom = fileDAO.getAllFilesInStorageById(storageFrom.getId());
        List<File> filesStorageTo = fileDAO.getAllFilesInStorageById(storageTo.getId());

        checkIfMaxSizeIsEnoughToTransfer(storageServise.findStorageByID(storageTo.getId()), filesStorageFrom, filesStorageTo);
        checkIfFileIsPresentInSourceStorage(storageServise.findStorageByID(storageTo.getId()), filesStorageFrom);
        checkFilesFormatSupported(storageServise.findStorageByID(storageTo.getId()), filesStorageFrom);

        fileDAO.transferAllFiles(filesStorageFrom, storageTo.getId());

    }

    private void checkFilesFormatSupported(Storage storageTo, List<File> filesStorageFrom) throws UnsupportedFormatException {
        for (File file : filesStorageFrom) {
            checkIfFormatSupported(storageTo, file);
        }
    }

    private void checkIfFileIsPresentInSourceStorage(Storage storageTo, List<File> filesStorageFrom) throws ExistFileException {

        for (File file : filesStorageFrom) {
            if (checkIfFileExistInStorage(storageTo, file)) {
                throw new ExistFileException(" File id : " + file.getId() + "is currently present in Storage id : " + storageTo.getId());
            }
        }
    }

    private void checkIfMaxSizeIsEnoughToTransfer(Storage storageTo, List<File> filesStorageFrom, List<File> filesStorageTo) throws OverloadSizeException {
        long sizeAllOfFileInStorageFrom = 0;
        long sizeAllOfFileInStorageTo = 0;
        for (File file : filesStorageFrom) {
            sizeAllOfFileInStorageFrom += file.getSize();
        }
        for (File file : filesStorageTo) {
            sizeAllOfFileInStorageTo += file.getSize();
        }

        if (sizeAllOfFileInStorageFrom + sizeAllOfFileInStorageTo >= storageTo.getStorageMaxSize()) {
            throw new OverloadSizeException("SUMM size files in StorageTo and StorageFrom is over MAX Size StorageTo");
        }
    }
}
