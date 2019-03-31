package lesson4.hw1.entity;

import lesson4.hw1.service.Initializator;

import java.util.Arrays;

public class Storage {
    private static long id;
    private  String[] formatsSupported;
    private String storageCountry;
    private long storageMaxSize;


    static{
        id =Initializator.getMaxIDStorage();
    }

    private Storage(long id, String[] formatsSupported, String storageCountry, long storageMaxSize) {
        this.id = id;
        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageMaxSize = storageMaxSize;
        id++;
    }

    public Storage(String[] formatsSupported, String storageCountry, long storageMaxSize) {
       this(id, formatsSupported,storageCountry,storageMaxSize);
    }

    public long getId() {
        return id;
    }

    public String[] getFormatsSupported() {
        return formatsSupported;
    }

    public String getStorageCountry() {
        return storageCountry;
    }

    public long getStorageMaxSize() {
        return storageMaxSize;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", formatsSupported=" + Arrays.toString(formatsSupported) +
                ", storageCountry='" + storageCountry + '\'' +
                ", storageMaxSize=" + storageMaxSize +
                '}';
    }
}
