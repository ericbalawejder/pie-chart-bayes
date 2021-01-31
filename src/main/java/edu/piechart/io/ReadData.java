package main.java.edu.piechart.io;

import java.io.*;

public class ReadData {
    private final String path;

    public ReadData(String filePath) {
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
        String[] data = fileToString.toString().split("\n");
        bufferReader.close();
        return data;
    }

}
