package lesson4.hw1.service;

import lesson4.hw1.dao.StorageDAO;
import lesson4.hw1.entity.Storage;

public class StorageServise {

    StorageDAO storageDAO = new StorageDAO();

    public Storage findStorageByID(long id) {
        return storageDAO.findByID(id);
    }

    public Storage addStorage(Storage storage){
       return storageDAO.save(storage);
    }

    public Long dellStorage (long id){
        return storageDAO.delete(id);
    }

    public void updateStorage (Storage storage){
        storageDAO.update(storage);
    }
}