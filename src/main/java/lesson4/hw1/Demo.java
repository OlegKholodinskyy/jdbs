package lesson4.hw1;

import lesson4.hw1.Exception.ExistFileException;
import lesson4.hw1.Exception.OverloadSizeException;
import lesson4.hw1.Exception.UnsupportedFormatException;
import lesson4.hw1.entity.Storage;
import lesson4.hw1.entity.File;
import lesson4.hw1.service.Controller;
import lesson4.hw1.service.Initializator;


public class Demo {
    public static void main(String[] args) {
        Initializator.initializeMaxIDs();

        Controller controller = new Controller();
        Storage storage1 = new Storage(new String[]{"txt", "png"}, "Ukraine", 1000);
        Storage storage2 = new Storage(new String[]{"jpg", "png"}, "Ukraine", 1000);
        Storage storage3 = new Storage(new String[]{"docx", "doc"}, "Ukraine", 1000);


        File file1 = new File("file1", "txt", 100L, null);
        File file2 = new File("file2", "txt", 100L, null);
        File file3 = new File("file3", "txt", 100L, null);


        /*
        save file in system without storage
         */
        controller.saveFileInSystem(file1);
        /*


        put file test
         */
        try {
            controller.put(storage1, file1);
        } catch (UnsupportedFormatException e) {
            System.out.println(e.getMessage());
        } catch (OverloadSizeException e) {
            System.out.println(e.getMessage());
        } catch (ExistFileException e) {
            System.out.println(e.getMessage());
        }


        try {
            controller.put(storage2, file2);
        } catch (UnsupportedFormatException e) {
            System.out.println(e.getMessage());
        } catch (OverloadSizeException e) {
            System.out.println(e.getMessage());
        } catch (ExistFileException e) {
            System.out.println(e.getMessage());
        }
    }


}