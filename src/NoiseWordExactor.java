import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.nio.file.StandardOpenOption.CREATE;

public class NoiseWordExactor{
    public static void main(String[] args) throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File selectedFile;
        String rec = "";

        ArrayList<String> stopWords = new ArrayList<>();
        Map<String, Integer> wordFrequency = new TreeMap<>();
        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                BufferedReader reader = Files.newBufferedReader(file);

                while (reader.ready()) {
                    rec = reader.readLine();

                    String[] words = rec.split("[^a-zA-Z]+");
                    for (String word : words) {
                        String lowerCaseWord = word.toLowerCase();
                        if(!lowerCaseWord.isEmpty()){
                            wordFrequency.merge(word, 1, Integer::sum);
                        }
                    }
                }
            }

            NoiseWords noiseProcessor = new NoiseWords(wordFrequency);
            stopWords =  noiseProcessor.getStopWords();

            noiseProcessor.printStopWords();


        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
