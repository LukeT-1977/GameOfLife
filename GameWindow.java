import javax.swing.*;
import java.awt.event.*;

public class GameWindow extends JFrame {
    private GameOfLife gol;

    public GameWindow(GameOfLife gameOfLife) {
        this.gol = gameOfLife;

        // set up jframe
        setTitle("Game of Life");
        setSize(140, 200);

        // create play button
        JButton playButton = new JButton("Play");
        playButton.setBounds(20, 40, 100, 40);

        // create random button
        JButton randomButton = new JButton("Random");
        randomButton.setBounds(20, 85, 100, 40);

        // add the components to the JFrame
        add(playButton);
        add(randomButton);

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
        // make the window visible
        setVisible(true);
    }
}