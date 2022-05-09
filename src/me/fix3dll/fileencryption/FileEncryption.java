package me.fix3dll.fileencryption;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileEncryption {

    public static void encryptFile(File file, int encryptionLevel) {
        File fileEncrypted = new File(file.toString().replace(".txt", "") + "Sifrelenmis.txt");
        BufferedWriter writer;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file),
                            StandardCharsets.UTF_8));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            writer = new BufferedWriter(new FileWriter(fileEncrypted));

            int c;
            while ((c = reader.read()) != -1) {
                /*
                  [97,122] -15, [65,90] -10 çıkarıp mod alınmasının sebebi mod
                  27 alındığında A || a nın 1'e Z||z nin 26'ya denk gelmesidir.
                 */
                if (97 <= c && c <= 122) {
                    if (122 < c + encryptionLevel) {
                        writer.write((char) (((c + encryptionLevel - 15) % 27) + 97));
                    } else if (c + encryptionLevel < 97) {
                        writer.write((char) (c + encryptionLevel + 26));
                    } else {
                        writer.write((char) (c + encryptionLevel));
                    }
                } else if (65 <= c && c <= 90) {
                    if (90 < c + encryptionLevel) {
                        writer.write((char) (((c + encryptionLevel - 10) % 27) + 65));
                    } else if (c + encryptionLevel < 65) {
                        writer.write((char) (c + encryptionLevel + 26));
                    } else {
                        writer.write((char) (c + encryptionLevel));
                    }
                } else {
                    writer.write((char) (c));
                }
            }

            writer.close();
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
