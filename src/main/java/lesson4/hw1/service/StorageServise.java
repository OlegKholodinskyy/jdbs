package lesson4.hw1.service;

import lesson4.hw1.dao.StorageDAO;
import lesson4.hw1.entity.Storage;

public class StorageServise {
    StorageDAO storageDAO = new StorageDAO();

    public Storage findStorageByID(long id) {
        return storageDAO.findByID(id);
    }
}