import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.System.exit;

public class TagExactorGUI extends JFrame {
    JPanel mainPnl;

    JPanel titlePnl;
    JLabel titleLbl;

    JPanel displayPnl;
    JScrollPane scrollPane;

    JPanel btnPnl;
    JButton saveBtn;
    JButton quitBtn;

    public TagExactorGUI() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout(5,5));

        createTitlePnl();
        mainPnl.add(titlePnl, BorderLayout.NORTH);

        createDisplayPnl();
        mainPnl.add(displayPnl, BorderLayout.CENTER);

        createBtnPnl();
        mainPnl.add(btnPnl, BorderLayout.SOUTH);

        add(mainPnl);
        setTitle("TagExactor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
        setVisible(true);
    }

    public void createTitlePnl(){
        titlePnl = new JPanel();

        titleLbl = new JLabel("Tag Extractor",JLabel.CENTER);
        titleLbl.setFont(new Font("SansSerif",Font.BOLD,20));

        titlePnl.add(titleLbl);
    }

    public void createDisplayPnl(){
        displayPnl = new JPanel();
        JTextArea textArea = new JTextArea(20, 25);
        scrollPane = new JScrollPane(textArea);

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("SansSerif",Font.PLAIN,14));

        displayPnl.add(scrollPane,BorderLayout.CENTER);
    }

    public void createBtnPnl(){
        btnPnl = new JPanel();
        saveBtn = new JButton("Save As Document");
        quitBtn = new JButton("Quit");

        quitBtn.addActionListener((ActionEvent e)->exit(0));
        btnPnl.add(saveBtn);
        btnPnl.add(quitBtn);
    }
}
