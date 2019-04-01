package lesson4.hw1;

import lesson4.hw1.Exception.ExistFileException;
import lesson4.hw1.Exception.OverloadSizeException;
import lesson4.hw1.Exception.UnsupportedFormatException;
import lesson4.hw1.entity.Storage;
import lesson4.hw1.entity.File;
import lesson4.hw1.service.Controller;

import java.util.ArrayList;
import java.util.List;


public class Demo {
    public static void main(String[] args) {

        Controller controller = new Controller();
        Storage system = new Storage(999, new String[]{"txt", "png", "doc", "jpg", "docx"}, "Ukraine", 10000);
        Storage storage1 = new Storage(1000, new String[]{"txt","png","doc","jpg","docx"}, "Ukraine", 1000);
        Storage storage2 = new Storage(1001, new String[]{"jpg","png"}, "Ukraine", 1000);
        Storage storage3 = new Storage(1002, new String[]{"docx","doc"}, "Ukraine", 1000);


        File file1 = new File(2000, "file1", "png", 100L, null);
        File file2 = new File(2001, "file2", "txt", 100L, null);
        File file3 = new File(2002, "file3", "jpg", 100L, null);
        File file4 = new File(2003, "file4", "docx", 100L, storage3);


        System.out.println("testPuttingStorage:");
              testPuttingStorage(system, storage1, storage2, storage3, controller);

        System.out.println("saveFileInSystem:");
              testSavingFileWithoutStorage(file1, file2, file3, controller);

        System.out.println("Put file from system to Storage :");
           testPuttFileToStorage(storage1,storage2,storage3,file1,file2,file3,file4, controller);

        System.out.println("test putAll");
        testPutAll(file1,file2,file3,file4,system,controller);

        System.out.println("test delete");
        testDelete (system,file1,controller);
        testDelete (system,file1,controller);

        System.out.println("test Transfer All");
        testTransferAll(system, storage1, controller);

        System.out.println("test Transfer File");
        testTransfer(storage1,storage2, file1.getId(),controller);

    }

    private static void testTransfer(Storage storageFrom, Storage storageTo, long id, Controller controller) {
        try {
            controller.transferFile(storageFrom,storageTo,id);
        } catch (ExistFileException e) {
            System.out.println(e.getMessage());
        } catch (OverloadSizeException e) {
            System.out.println(e.getMessage());
        } catch (UnsupportedFormatException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void testTransferAll(Storage storageFrom, Storage storageTo, Controller controller) {
        try {
            controller.transferAll(storageFrom, storageTo);
        } catch (ExistFileException e) {
            System.out.println(e.getMessage());
        } catch (OverloadSizeException e) {
            System.out.println(e.getMessage());
        } catch (UnsupportedFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testDelete(Storage storage, File file, Controller controller) {
        try {
            controller.delete(storage, file);
        } catch (ExistFileException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testPutAll(File file1, File file2, File file3, File file4, Storage system, Controller controller) {
        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        files.add(file3);
        files.add(file4);

        try {
            controller.putAll(system, files);
        } catch (ExistFileException e) {
            System.out.println(e.getMessage());
        } catch (OverloadSizeException e) {
            System.out.println(e.getMessage());
        } catch (UnsupportedFormatException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void testPuttingStorage(Storage system, Storage storage1, Storage storage2, Storage storage3, Controller controller) {
        controller.addStorage(system);
        controller.addStorage(storage1);
        controller.addStorage(storage2);
        controller.addStorage(storage3);
    }

    private static void testSavingFileWithoutStorage(File file1, File file2, File file3, Controller controller) {
         /*
        save file in system without storage
         */
        controller.saveFileInSystem(file1);
        controller.saveFileInSystem(file2);
        controller.saveFileInSystem(file3);
    }


    private static void testPuttFileToStorage(Storage storage1, Storage storage2, Storage storage3, File file1, File file2, File file3, File file4, Controller controller) {
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
        try {
            controller.put(storage2, file3);
        } catch (UnsupportedFormatException e) {
            System.out.println(e.getMessage());
        } catch (OverloadSizeException e) {
            System.out.println(e.getMessage());
        } catch (ExistFileException e) {
            System.out.println(e.getMessage());
        }
        try {
            controller.put(storage3, file4);
        } catch (UnsupportedFormatException e) {
            System.out.println(e.getMessage());
        } catch (OverloadSizeException e) {
            System.out.println(e.getMessage());
        } catch (ExistFileException e) {
            System.out.println(e.getMessage());
        }
    }
}