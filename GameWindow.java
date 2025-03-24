import javax.swing.*;
import java.awt.event.*;

public class GameWindow extends JFrame {
    private GameOfLife gol;

    public GameWindow(GameOfLife gameOfLife) {
        this.gol = gameOfLife;

        // set up jframe
        setSize(140, 235);
        setLayout(null); // explicitly set null layout

        // create play button
        JButton playButton = new JButton("Play");
        playButton.setBounds(20, 20, 100, 40);

        // create random button
        JButton randomButton = new JButton("Random");
        randomButton.setBounds(20, 65, 100, 40);

        // create save button
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(20, 110, 100, 40);

        // create load button
        JButton loadButton = new JButton("Load");
        loadButton.setBounds(20, 155, 100, 40);

        // add the components to the JFrame
        add(playButton);
        add(randomButton);
        add(saveButton);
        add(loadButton);

        // when playbutton clicked
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // toggle play/pause and change text accordingly
                if (gol.togglePlaying()) { // this both calls the toggle function and checks the return
                    playButton.setText("Pause");
                } else {
                    playButton.setText("Play");
                }
            }
        });

        // when random button clicked
        randomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gol.randomizeGrid();
            }
        });

        // when save button clicked
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gol.saveGame();
            }
        });

        // when load button clicked
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gol.loadGame();
            }
        });

        // make the window visible
        setVisible(true);
    }
}