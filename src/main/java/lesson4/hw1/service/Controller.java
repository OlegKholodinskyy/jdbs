package lesson4.hw1.service;

import lesson4.hw1.Exception.ExistFileException;
import lesson4.hw1.Exception.OverloadSizeException;
import lesson4.hw1.Exception.UnsupportedFormatException;
import lesson4.hw1.entity.File;
import lesson4.hw1.entity.Storage;

import java.util.List;

public class Controller {
    FileService fileService = new FileService();
    StorageServise storageServise = new StorageServise();

    public File put(Storage storage, File file) throws UnsupportedFormatException, OverloadSizeException, ExistFileException {
        return fileService.put(storage, file);
    }

    public void putAll(Storage storage, List<File> files) throws ExistFileException, OverloadSizeException, UnsupportedFormatException {
        fileService.putAll(storage, files);
    }

    public long delete(Storage storage, File file) throws ExistFileException {
        return fileService.delete(storage, file);
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws ExistFileException, OverloadSizeException, UnsupportedFormatException {
        fileService.transferAll(storageFrom, storageTo);
    }

    public long transferFile(Storage storageFrom, Storage storageTo, long id) throws ExistFileException, OverloadSizeException, UnsupportedFormatException {
        return fileService.transferFile(storageFrom, storageTo, id);
    }

    /*
    save file in system without storage
     */
    public long saveFileInSystem(File file) {
        return fileService.saveFileInSystem(file);
    }

    public Storage addStorage (Storage storage){
      return   storageServise.addStorage(storage);
    }
}
