import java.io.*;
import java.util.Scanner;
import javax.swing.filechooser.FileSystemView;
import java.util.List;
import java.util.ArrayList;

public class UsbKeyFetcher {
    public static String fetchEncryptionKeyFromUsb(String targetFileName) {
        String key = fetchFromSpecificFile(targetFileName);
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
                    String name = file.getName().toLowerCase();
                    if (name.equals("$recycle.bin") || name.equals("system volume information") || 
                        name.equals(".trashes") || name.startsWith(".")) {
                        continue;
                    }
                    String key = searchTxtFilesRecursively(file);
                    if (key != null) {
                        return key;
                    }
                } else if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    System.out.println("Reading .txt file: " + file.getAbsolutePath());
                    String key = readKeyFromFile(file);
                    if (key != null && !key.isEmpty()) {
                        System.out.println("Found key in file: " + file.getName() + " -> " + key);
                        return key;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error searching directory " + directory + ": " + e.getMessage());
        }
        
        return null;
    }
    private static String readKeyFromFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            StringBuilder key = new StringBuilder();
            while (scanner.hasNextLine()) {
                key.append(scanner.nextLine()).append("\n");
            }
            String content = key.toString().trim();
            return content.isEmpty() ? null : content;
        } catch (IOException e) {
            System.err.println("Error reading file " + file + ": " + e.getMessage());
            return null;
        }
    }
    private static List<File> getUsbDrives(String os) {
        List<File> drives = new ArrayList<>();
        
        if (os.contains("win")) {
            String systemDrive = System.getenv("SystemDrive");
            if (systemDrive == null) {
                systemDrive = "C:";
            }
            String systemPrefix = systemDrive.substring(0, 2) + "\\";
            
            for (char c = 'A'; c <= 'Z'; c++) {
                String path = c + ":\\";
                File root = new File(path);
                if (root.exists() && root.canRead() && !path.startsWith(systemPrefix)) {
                    if (isRemovableDrive(root)) {
                        System.out.println("Found removable drive: " + path + " (Type: " + getDriveType(root) + ")");
                        drives.add(root);
                    }
                }
            }
        } else if (os.contains("mac")) {
            File volumesDir = new File("/Volumes/");
            if (volumesDir.exists() && volumesDir.isDirectory()) {
                File[] volumes = volumesDir.listFiles();
                if (volumes != null) {
                    for (File vol : volumes) {
                        if (vol.isDirectory() && !new File(vol, "System").exists()) {
                            System.out.println("Found potential removable volume: " + vol.getAbsolutePath());
                            drives.add(vol);
                        }
                    }
                }
            }
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            String[] mountPoints = {"/media/", "/mnt/"};
            for (String mp : mountPoints) {
                File dir = new File(mp);
                if (dir.exists() && dir.isDirectory()) {
                    File[] volumes = dir.listFiles();
                    if (volumes != null) {
                        for (File vol : volumes) {
                            if (vol.isDirectory()) {
                                System.out.println("Found volume: " + vol.getAbsolutePath());
                                drives.add(vol);
                            }
                        }
                    }
                }
            }
        }
        
        return drives;
    }
    private static String fetchFromSpecificFile(String targetFileName) {
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.contains("win")) {
            String systemDrive = System.getenv("SystemDrive");
            if (systemDrive == null) {
                systemDrive = "C:";
            }
            String systemPrefix = systemDrive.substring(0, 2) + "\\";
            
            for (char c = 'A'; c <= 'Z'; c++) {
                String path = c + ":\\";
                File root = new File(path);
                if (root.exists() && root.canRead() && !path.startsWith(systemPrefix)) {
                    if (isRemovableDrive(root)) {
                        File keyFile = new File(root, targetFileName);
                        if (keyFile.exists() && keyFile.isFile()) {
                            System.out.println("Found key file: " + keyFile.getAbsolutePath());
                            return readKeyFromFile(keyFile);
                        }
                    }
                }
            }
        } else if (os.contains("mac")) {
            File volumesDir = new File("/Volumes/");
            if (volumesDir.exists() && volumesDir.isDirectory()) {
                for (File vol : volumesDir.listFiles()) {
                    if (vol.isDirectory() && !new File(vol, "System").exists()) {
                        File keyFile = new File(vol, targetFileName);
                        if (keyFile.exists() && keyFile.isFile()) {
                            System.out.println("Found key file: " + keyFile.getAbsolutePath());
                            return readKeyFromFile(keyFile);
                        }
                    }
                }
            }
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            String[] mountPoints = {"/media/", "/mnt/"};
            for (String mp : mountPoints) {
                File dir = new File(mp);
                if (dir.exists() && dir.isDirectory()) {
                    for (File vol : dir.listFiles()) {
                        if (vol.isDirectory()) {
                            File keyFile = new File(vol, targetFileName);
                            if (keyFile.exists() && keyFile.isFile()) {
                                System.out.println("Found key file: " + keyFile.getAbsolutePath());
                                return readKeyFromFile(keyFile);
                            }
                        }
                    }
                }
            }
        }
        
        return null;
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

    private static String getDriveType(File drive) {
        try {
            FileSystemView fsv = FileSystemView.getFileSystemView();
            String desc = fsv.getSystemTypeDescription(drive);
            return desc != null ? desc : "Unknown";
        } catch (Exception e) {
            return "Unknown";
        }
    }
}