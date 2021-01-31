package main.java.edu.piechart.io;

import java.io.*;

public class ReadFile {
    private final String path;

    public ReadFile(String filePath) {
        this.path = filePath;
    }

    public String[] openFile() throws IOException {
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferReader = new BufferedReader(fileReader);
        StringBuilder fileToString = new StringBuilder();

        String characterData;

        while ((characterData = bufferReader.readLine()) != null) {
            fileToString.append(characterData);
            fileToString.append("\n");
        }
        String[] evidence = fileToString.toString().split(",");
        bufferReader.close();
        return evidence;
    }

}
