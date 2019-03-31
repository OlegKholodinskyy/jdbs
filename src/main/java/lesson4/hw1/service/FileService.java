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

    public File put(Storage storage, File file) throws UnsupportedFormatException, OverloadSizeException, ExistFileException {
        checkIfFormatSupported(storage, file);
        checkIfSizeAviable(storage, file);
        if (checkIfFileExist(storage, file)) {
            throw new ExistFileException("File ID : " + file.getId() + " not added to Storage ID : " + storage.getId() + " by reason : file is already present in Storage");
        }
        fileDAO.save(file, storage);
        return file;
    }

    private boolean checkIfFileExist(Storage storage, File file) {
        List<File> allFilesInStorage = fileDAO.getAllFilesInStorageById(storage.getId());
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

    private void checkIfFormatSupported(Storage storage, File file) throws UnsupportedFormatException {
        String[] formatsStorageSupported = storage.getFormatsSupported();
        for (String formatStorage : formatsStorageSupported) {
            if (formatStorage.equals(file.getFormat())) {
                return;
            }
        }
        throw new UnsupportedFormatException("File ID : " + file.getId() + " not added to Storage ID : " + storage.getId() + " by reason : format unsupported ");
    }

    public List<File> putAll(Storage storage, List<File> files) throws ExistFileException, OverloadSizeException, UnsupportedFormatException {

        for (File file : files) {
            put(storage, file);
        }
        return files;
    }

    public long delete(Storage storage, File file) throws ExistFileException {
        if (!checkIfFileExist(storage, file))
            throw new ExistFileException("File ID : " + file.getId() + " not deleted from Storage ID : " + storage.getId() + " by reason : file in Storage does not exist");

        fileDAO.delete(file.getId());
        return file.getId();
    }


    public long transferFile(Storage storageFrom, Storage storageTo, long id) {
        return 0;
    }

    public long saveFileInSystem(File file) {

        fileDAO.save(file,null);
        return file.getId();
    }
}
