import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.System.exit;

public class TagExtractorGUI extends JFrame {
    JPanel mainPnl;

    JPanel titlePnl;
    JLabel titleLbl;

    JPanel displayPnl;
    JTextArea textArea;
    JScrollPane scrollPane;

    JPanel btnPnl;
    JButton searchBtn;
    JButton saveBtn;
    JButton quitBtn;

    ArrayList<String> stopWords = new ArrayList<>();
    Map<String, Integer> wordFrequency = new TreeMap<>();

    NoiseWordExtractor noiseWordExtractor = new NoiseWordExtractor(wordFrequency);


    public TagExtractorGUI() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout(5, 5));

        createTitlePnl();
        mainPnl.add(titlePnl, BorderLayout.NORTH);

        createDisplayPnl();
        mainPnl.add(displayPnl, BorderLayout.CENTER);

        createBtnPnl();
        mainPnl.add(btnPnl, BorderLayout.SOUTH);

        add(mainPnl);
        setTitle("TagExactor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setVisible(true);
    }

    public void createTitlePnl() {
        titlePnl = new JPanel();

        titleLbl = new JLabel("Tag Extractor", JLabel.CENTER);
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 20));

        titlePnl.add(titleLbl);
    }

    public void createDisplayPnl() {
        displayPnl = new JPanel();
        textArea = new JTextArea(20, 25);
        scrollPane = new JScrollPane(textArea);

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));

        displayPnl.add(scrollPane, BorderLayout.CENTER);
    }

    public void createBtnPnl() {
        btnPnl = new JPanel();
        searchBtn = new JButton("Search File");
        saveBtn = new JButton("Save As Document");
        quitBtn = new JButton("Quit");

        searchBtn.addActionListener((ActionEvent e) -> {
            generateWordExtractor();
        });

        saveBtn.addActionListener((ActionEvent e) -> {
            saveFile();
        });

        quitBtn.addActionListener((ActionEvent e) -> exit(0));
        btnPnl.add(searchBtn);
        btnPnl.add(saveBtn);
        btnPnl.add(quitBtn);
    }

    private void generateWordExtractor() {
        textArea.setText("");
        wordFrequency.clear();

        noiseWordExtractor.getFile();

        NoiseWords noiseProcessor = new NoiseWords(wordFrequency);

        stopWords = noiseProcessor.getStopWords();
        noiseProcessor.filterWords(stopWords);

        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            String text = entry.getKey() + ":" + entry.getValue().toString();
            textArea.append(text + "\n");
        }
    }

    private void saveFile() {
        String textToSave = textArea.getText();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Filtered Tags");

        fileChooser.setSelectedFile(new File("filtered_tags.txt"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            Path filePath = fileChooser.getSelectedFile().toPath();

            if (!filePath.toString().toLowerCase().endsWith(".txt")) {
                filePath = Paths.get(filePath.toString() + ".txt");
            }

            try {
                Files.writeString(filePath, textToSave, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                JOptionPane.showMessageDialog(this,
                        "Successfully saved data to:\n" + filePath.getFileName(),
                        "Save Successful",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Error saving file: " + e.getMessage(),
                        "Save Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}
