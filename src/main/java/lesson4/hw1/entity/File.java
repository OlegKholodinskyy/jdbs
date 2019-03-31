package lesson4.hw1.entity;

import javafx.fxml.Initializable;
import lesson4.hw1.service.Initializator;

import java.util.Objects;

public class File {

    private static long id;
    private String name;
    private String format;
    private long size;
    private Storage storage;

    static  {
        id = Initializator.getMaxIDFile();
    }

    private File(long id, String name, String format, long size, Storage storage) {
        this.id = id;
        this.name = name;
        this.format = format;
        this.size = size;
        this.storage = storage;
        id++;
    }

    public File(String name, String format, long size, Storage storage) {
        this(id,name,format,size,storage);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    public long getSize() {
        return size;
    }

    public Storage getStorage() {
        return storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return (id == file.id && name.equals(file.name));
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", size=" + size +
                ", storage=" + storage +
                '}';
    }
}