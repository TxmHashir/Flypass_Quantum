import java.io.*;
import java.util.Scanner;
import javax.swing.filechooser.FileSystemView;
import java.util.List;
import java.util.ArrayList;

public class UsbKeyFetcher {
    public static String fetchEncrypKeyFrmUSB(String targetFileName) {
        String key = fetchFromFile(targetFileName);
        if (key != null) {
            return key;
        }
        return searchAllTxtFiles();
    }
    public static String searchAllTxtFiles() {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("OS detected: " + os);
        
        List<File> usbDrives = getUsbDrives(os);
        
        for (File usbDrive : usbDrives) {
            System.out.println("Searching USB drive: " + usbDrive.getAbsolutePath());
            String key = searchTxtFilesRecursively(usbDrive);
            if (key != null) {
                return key;
            }
        }
        
        System.out.println("No matching .txt files found on any removable drive.");
        return null;
    }
    private static String searchTxtFilesRecursively(File directory) {
        if (!directory.exists() || !directory.isDirectory() || !directory.canRead()) {
            return null;
        }
        
        try {
            File[] files = directory.listFiles();
            if (files == null) {
                return null;
            }
            
            for (File file : files) {
                if (file.isDirectory()) {
                    String key = searchTxtFilesRecursively(file);
                    if (key != null) {
                        return key;
                    }
                } else if (file.getName().toLowerCase().endsWith(".txt")) {
                    String key = readKeyFromFile(file);
                    if (key != null) {
                        return key;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error searching directory " + directory.getAbsolutePath() + ": " + e.getMessage());
        }
        
        return null;
    }

    private static String readKeyFromFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                return scanner.nextLine().trim();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + file.getAbsolutePath());
        }
        return null;
    }

    public static List<File> getUsbDrives(String os) {
        List<File> usbDrives = new ArrayList<>();
        File[] roots = File.listRoots();
        
        for (File root : roots) {
            if (isRemovableDrive(root)) {
                usbDrives.add(root);
            }
        }
        
        return usbDrives;
    }

    private static boolean isRemovableDrive(File drive) {
        try {
            FileSystemView fsv = FileSystemView.getFileSystemView();
            String desc = fsv.getSystemTypeDescription(drive);
            if (desc != null) {
                String descLower = desc.toLowerCase();
                return descLower.contains("removable") || descLower.contains("usb") || 
                       descLower.contains("flash") || descLower.contains("disk") && !descLower.contains("local");
            }
            String path = drive.getAbsolutePath();
            return !path.startsWith("C:") && !path.startsWith("c:");
        } catch (Exception e) {
            String path = drive.getAbsolutePath();
            return !path.startsWith("C:") && !path.startsWith("c:");
        }
    }

    public static String getDriveType(File drive) {
        try {
            FileSystemView fsv = FileSystemView.getFileSystemView();
            String desc = fsv.getSystemTypeDescription(drive);
            return desc != null ? desc : "Unknown";
        } catch (Exception e) {
            return "Unknown";
        }
    }

    private static String fetchFromFile(String targetFileName) {
        List<File> usbDrives = getUsbDrives(System.getProperty("os.name").toLowerCase());
        
        for (File usbDrive : usbDrives) {
            File keyFile = new File(usbDrive, targetFileName);
            if (keyFile.exists() && keyFile.canRead()) {
                return readKeyFromFile(keyFile);
            }
        }
        
        return null;
    }
}