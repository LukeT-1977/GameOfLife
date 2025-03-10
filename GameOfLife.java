import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;

public class GameOfLife extends JFrame implements Runnable, MouseListener {
    // member data
    private static boolean isInitialised = false;
    private static final Dimension WindowSize = new Dimension(800,800);
    private BufferStrategy strategy;
    private Graphics offscreenGraphics;
    private boolean[][][] gameState = new boolean[40][40][2];
    private int frontBuffer = 0;
    private int backBuffer = 1;
    private boolean playing = false;

    public GameOfLife() {
        //Display the window, centred on the screen
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = screensize.width/2 - WindowSize.width/2;
        int y = screensize.height/2 - WindowSize.height/2;
        setBounds(x, y, WindowSize.width, WindowSize.height);
        setVisible(true);
        this.setTitle("Conway's Game of Life");

        // create and start our animation thread
        Thread t = new Thread(this);
        t.start();

        // send mouse events arriving into this JFrame back to its own event handlers
        addMouseListener(this);

        // initialise double-buffering
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        offscreenGraphics = strategy.getDrawGraphics();

        // initialise gameState
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                for (int m = 0; m < 2; m++) {
                    gameState[i][j][m] = false;
                }
            }
        }

        isInitialised = true;
    }

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        GameOfLife gol = new GameOfLife();
        GameWindow gw = new GameWindow(gol);
    }

    // function from Runnable
    public void run() {
        while(1==1) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }

            if (playing) {
                updateGameState();
            }

            this.repaint();
        }
    }

    public void paint(Graphics g) {
        if (!isInitialised) {
            return;
        }

        g = offscreenGraphics;

        // clear the canvas with a big black rectangle
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WindowSize.width, WindowSize.height);

        // draw cells of value "true"
        g.setColor(Color.WHITE);
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                if (gameState[i][j][frontBuffer]) {
                    g.fillRect(i*20,j*20,20, 20);
                }
            }
        }

        // flip the buffers offscreen<-->onscreen
        strategy.show();
    }

    // MouseListener functions
    public void mouseClicked(MouseEvent e) {
        // get position of mouse X and Y based on its position in grid, i.e. mouse's X and Y divided by 20 and rounded down
        int mouseX = (int) (e.getX() / 20);
        int mouseY = (int) (e.getY() / 20);

        // reverse current value, ie true becomes false and false becomes true
        gameState[mouseX][mouseY][frontBuffer] = !gameState[mouseX][mouseY][frontBuffer];
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }


    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    private void updateGameState() {
        // loop thru each cell in grid
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                // count live neighbours of each cell
                int liveNeighbours = countLiveNeighbours(i, j);

                if (gameState[i][j][frontBuffer]) {
                    // cell alive - remains alive if it has 2 or 3 live neighbours
                    gameState[i][j][backBuffer] = (liveNeighbours == 2) || (liveNeighbours == 3);
                } else {
                    // cell dead - becomes alive if it has 3 live neighbours
                    gameState[i][j][backBuffer] = (liveNeighbours == 3);
                }
            }
        }
        // swap buffers
        int temp = frontBuffer;
        frontBuffer = backBuffer;
        backBuffer = temp;
    }

    private int countLiveNeighbours(int x, int y) {
        int count = 0;
        for (int xx = -1; xx <= 1; xx++) {
            for (int yy = -1; yy <= 1; yy++) {
                if (xx != 0 || yy != 0) {
                    int nx = (x + xx + 40) % 40;
                    int ny = (y + yy + 40) % 40;
                    if (gameState[nx][ny][frontBuffer]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public boolean togglePlaying() {
        playing = !playing;
        return playing;
    }

    public void randomizeGrid() {
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                gameState[i][j][frontBuffer] = Math.random() < 0.5;
            }
        }
    }
}

