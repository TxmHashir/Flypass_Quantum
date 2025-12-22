import java.io.*;
import java.util.Scanner;

public class UsbKeyFetcher {
    public static String fetchEncryptionKeyFromUsb(String targetFileName) {
        // Get all available drives (roots)
        File[] roots = File.listRoots();
        System.out.println("Detected drives:");
        for (File root : roots) {
            System.out.println(root.getAbsolutePath());
            if (isRemovableDrive(root)) {
                System.out.println("Checking removable drive: " + root.getAbsolutePath());
                File keyFile = new File(root, targetFileName);
                if (keyFile.exists() && keyFile.isFile()) {
                    System.out.println("Found key file: " + keyFile.getAbsolutePath());
                    try (Scanner scanner = new Scanner(keyFile)) {
                        StringBuilder key = new StringBuilder();
                        while (scanner.hasNextLine()) {
                            key.append(scanner.nextLine());
                        }
                        System.out.println("Successfully read key: " + key.toString().trim());
                        return key.toString().trim();  // Return the huge key as string
                    } catch (IOException e) {
                        System.err.println("Error reading key file on " + root + ": " + e.getMessage());
                    }
                } else {
                    System.out.println("Key file not found on " + root);
                }
            } else {
                System.out.println("Skipping non-removable drive: " + root);
            }
        }
        System.out.println("No key file found on any removable drive.");
        return null;  // Not found
    }

    private static boolean isRemovableDrive(File drive) {
        // Basic heuristic: Check if drive is not the system root (customize per OS)
        String path = drive.getAbsolutePath();
        if (System.getProperty("os.name").startsWith("Windows")) {
            // For Windows, consider all drives except C:\ as potentially removable (A: and B: are old floppies, but include them if needed)
            return !path.startsWith("C:\\");
        } else {
            // For Linux/macOS, check if path contains "/media/" or "/Volumes/"
            return path.contains("/media/") || path.contains("/Volumes/");
        }
    }
}