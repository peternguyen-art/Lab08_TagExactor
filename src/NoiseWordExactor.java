import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.nio.file.StandardOpenOption.CREATE;

public class NoiseWordExactor extends JFileChooser {
    public NoiseWordExactor() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File selectedFile;
        String rec = "";

        ArrayList<String> stopWords = new ArrayList<>();

        Map<String, Integer> wordFrequency = new TreeMap<>();
        try{
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file,CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                while(reader.ready()){
                    rec = reader.readLine();

                    String[] words = rec.split("[,.!?\\s]");
                    for(String word : words){
                        wordFrequency.merge(word, 1, Integer::sum);
                    }
                }
            }

            System.out.println(wordFrequency);


        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }
}
