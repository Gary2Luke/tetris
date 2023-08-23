package com.example;

import com.example.entity.Cell;
import com.example.entity.Tetromino;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @Author luguorui
 * @Date 2023/8/15
 */
public class Tetris extends JPanel {

    private Cell[][] wall;
    /**
     * 正在下落的四方格
     */
    @Getter
    private Tetromino current;
    /**
     * 下一个四方格
     */
    private Tetromino next;
    /**
     * 分数
     */
    private int score;

    /**
     * 消除的行数
     */
    private int lines;


    /**
     * 游戏状态：
     * 0：游戏中
     * 1：暂停状态
     * 3：结束
     */
    private int gameState;

    private int ringStep;

    public Tetris() {
        init();
    }

    public static void main(String[] args) {
        Tetris panel = new Tetris();
        JFrame jFrame = new JFrame();
        jFrame.add(panel);
        jFrame.setSize(810, 940);
        jFrame.setVisible(true);
        jFrame.setTitle("Luke Love Hoya");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        panel.start();
    }

    @Override
    public void paint(Graphics g) {
        // 绘制游戏背景
        paintBackground(g);

        g.translate(22, 15);

        // 绘制游戏主区域
        paintWall(g);

        // 绘制戒指
        if (isReachRing()) {
            paintRing(g);
        } else {
            // 绘制正在下落的四方格
            paintCurrentTetromino(g);
        }
        // 绘制下一个四方格
        paintNextTetromino(g);

        // 绘制得分和行数
        paintScoreLines(g);

        // 绘制游戏状态
        paintGameState(g);
    }


    private void init() {
        wall = new Cell[18][9];
        current = Tetromino.randomOne();
        next = Tetromino.randomOne();
        score = 0;
        lines = 0;
        gameState = Constant.GAME_PLAYING;
    }

    /**
     * 游戏主逻辑，监听键盘事件
     */
    public void start() {
        gameState = Constant.GAME_PLAYING;
        KeyAdapter keyAdapter = new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                switch (code) {
                    case KeyEvent.VK_DOWN:
                        singleDropActive();
                        break;
                    case KeyEvent.VK_LEFT:
                        moveLeftActive();
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveRightActive();
                        break;
                    case KeyEvent.VK_UP:
                        rotateRightActive();
                        break;
                    case KeyEvent.VK_SPACE:
                        instantDropActive();
                        break;
                    case KeyEvent.VK_P:
                        //判断当前游戏状态
                        if (gameState == Constant.GAME_PLAYING) {
                            gameState = Constant.GAME_STOP;
                        }
                        break;
                    case KeyEvent.VK_C:
                        if (gameState == Constant.GAME_STOP) {
                            gameState = Constant.GAME_PLAYING;
                        }
                        break;
                    case KeyEvent.VK_S:
                        //重新开始
                        init();
                        break;

                }
            }
        };

        //将窗口设置为焦点
        this.addKeyListener(keyAdapter);
        this.requestFocus();

        while (true) {
            if (gameState == Constant.GAME_PLAYING) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                singleDropActive();
            }
            repaint();

            // 打印戒指
            if (isReachRing()) {
                for (int i = 0; i < 8; i++) {
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ++ringStep;
                    repaint();
                }
                break;
            }
        }
    }


    /**
     * 需要打印戒指
     *
     * @return
     */
    private boolean isReachRing() {
        return score >= Constant.RING_SCORE;
    }

    /**
     * 四方格瞬间下落
     */
    public void instantDropActive() {
        while (true) {
            //判断能否下落
            if (canDrop()) {
                current.moveDown();
            } else {
                break;
            }
        }
        trtrominoStop();
    }


    /**
     * 四方格单次下落
     */
    private void singleDropActive() {
        if (canDrop()) {
            current.moveDown();
        } else {
            trtrominoStop();
        }
    }

    /**
     * 四方格停止下落，需要消除行、积分、判断游戏状态
     */
    private void trtrominoStop() {
        //嵌入到墙中
        landToWall();
        destroyLinesAndScore();
        if (isGameOver()) {
            gameState = Constant.GAME_OVER;
        } else {
            //游戏没有结束
            current = next;
            next = Tetromino.randomOne();
        }
    }


    /**
     * 按键一次，左移一次
     */
    public void moveLeftActive() {
        current.moveLeft();
        //判断是否越界或重合
        if (isOutOfBounds() || isCoincide()) {
            current.moveRight();
        }
    }

    /**
     * 按键一次，右移一次
     */
    public void moveRightActive() {
        current.moveRight();
        //判断是否越界或重合
        if (isOutOfBounds() || isCoincide()) {
            current.moveLeft();
        }
    }

    /**
     * 右旋转
     */
    private void rotateRightActive() {
        current.rotateRight();
        if (isOutOfBounds() || isCoincide()) {
            current.rotateLeft();
        }
    }

    /**
     * 单元格嵌入墙中
     */
    private void landToWall() {
        Cell[] cells = current.getCells();
        for (Cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            wall[row][col] = cell;
        }
    }

    /**
     * 判断四方格是否能下落
     *
     * @return boolean
     */
    private boolean canDrop() {
        for (Cell cell : current.getCells()) {
            if (cell.getRow() == wall.length - 1) {
                return false;
            }
            if (wall[cell.getRow() + 1][cell.getCol()] != null) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断方块是否重合
     *
     * @return boolean
     */
    private boolean isCoincide() {
        for (Cell cell : current.getCells()) {
            if (wall[cell.getRow()][cell.getCol()] != null) {
                return true;
            }
        }
        return false;
    }

    private boolean isGameOver() {
        for (Cell cell : next.getCells()) {
            if (wall[cell.getRow()][cell.getCol()] != null) {
                return true;
            }
        }
        return false;
    }

    private boolean isOutOfBounds() {
        for (Cell cell : current.getCells()) {
            if (cell.getRow() < 0 || cell.getRow() >= wall.length) {
                return true;
            }
            if (cell.getCol() < 0 || cell.getCol() >= wall[0].length) {
                return true;
            }
        }
        return false;
    }

    /**
     * 消除行，并且积分
     */
    private void destroyLinesAndScore() {
        int line = 0;
        for (Cell cell : current.getCells()) {
            int row = cell.getRow();
            while (isFullLine(row)) {
                ++line;
                for (int i = row - 1; i >= 0; i--) {
                    System.arraycopy(wall[i], 0, wall[i + 1], 0, wall[0].length);
                }
                wall[0] = new Cell[9];
            }
        }
        lines += line;
        score += Constant.SCORES_POOL[line];
    }

    private boolean isFullLine(int row) {
        for (Cell cell : wall[row]) {
            if (cell == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 出戒指
     */
    private void paintRing(Graphics g) {
        int x = -155;
        int y = -300;
        g.drawImage(Constant.IMAGE_RING, x, y + ringStep * 70, null);
    }

    private void paintGameState(Graphics g) {
        if (gameState == Constant.GAME_PLAYING) {
            g.drawString(Constant.SHOW_GAME_STATES[0], 500, 660);
        } else if (gameState == Constant.GAME_STOP) {
            g.drawString(Constant.SHOW_GAME_STATES[1], 500, 660);
        } else {
            g.drawString(Constant.SHOW_GAME_STATES[2], 500, 660);
            g.setColor(Color.RED);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
//            g.drawString("GAME OVER!", 30, 400);
        }
    }

    private void paintScoreLines(Graphics g) {
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        g.drawString("分数: " + score, 500, 250);
        g.drawString("行数: " + lines, 500, 430);
    }

    private void paintNextTetromino(Graphics g) {
        Cell[] cells = next.getCells();
        for (Cell cell : cells) {
            g.drawImage(cell.getImage(), cell.getCol() * Constant.CELLS_SIZE + 370, cell.getRow() * Constant.CELLS_SIZE + 25, null);
        }
    }

    private void paintCurrentTetromino(Graphics g) {
        Cell[] cells = current.getCells();
        for (Cell cell : cells) {
            g.drawImage(cell.getImage(), cell.getCol() * Constant.CELLS_SIZE, cell.getRow() * Constant.CELLS_SIZE, null);
        }
    }


    private void paintWall(Graphics g) {
        for (int i = 0; i < wall.length; i++) {
            for (int j = 0; j < wall[0].length; j++) {
                Cell cell = wall[i][j];
                int x = j * Constant.CELLS_SIZE;
                int y = i * Constant.CELLS_SIZE;
                //判断是否有小方块
                if (cell == null) {
                    g.drawRect(x, y, Constant.CELLS_SIZE, Constant.CELLS_SIZE);
                } else {
                    g.drawImage(cell.getImage(), x, y, null);
                }
            }
        }
    }

    private void paintBackground(Graphics g) {
        g.drawImage(Constant.IMAGE_BACKGROUND, 0, 0, null);
    }
}
