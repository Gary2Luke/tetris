package com.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author luguorui
 * @Date 2023/8/15
 */
public class Constant {
    public static final int GAME_PLAYING = 0;
    public static final int GAME_STOP = 1;
    public static final int GAME_OVER = 2;

    public static final String[] SHOW_GAME_STATES = new String[]{"P[pause]", "C[continue]", "S[replay]"};
    /**
     * 游戏分数池
     */
    public static final int[] SCORES_POOL = {0, 1, 2, 5, 10};
    public static BufferedImage IMAGE_I;
    public static BufferedImage IMAGE_J;
    public static BufferedImage IMAGE_L;
    public static BufferedImage IMAGE_O;
    public static BufferedImage IMAGE_S;
    public static BufferedImage IMAGE_T;
    public static BufferedImage IMAGE_Z;
    public static BufferedImage IMAGE_BACKGROUND;

    public static BufferedImage IMAGE_RING;
    public static int CELLS_SIZE = 48;

    public static final int RING_SCORE = 10;

    static {
        try {
            IMAGE_I = ImageIO.read(Constant.class.getClassLoader().getResourceAsStream("images/I.png"));
            IMAGE_J = ImageIO.read(Constant.class.getClassLoader().getResourceAsStream("images/J.png"));
            IMAGE_L = ImageIO.read(Constant.class.getClassLoader().getResourceAsStream("images/L.png"));
            IMAGE_O = ImageIO.read(Constant.class.getClassLoader().getResourceAsStream("images/O.png"));
            IMAGE_S = ImageIO.read(Constant.class.getClassLoader().getResourceAsStream("images/S.png"));
            IMAGE_T = ImageIO.read(Constant.class.getClassLoader().getResourceAsStream("images/T.png"));
            IMAGE_Z = ImageIO.read(Constant.class.getClassLoader().getResourceAsStream("images/Z.png"));
            IMAGE_BACKGROUND = ImageIO.read(Constant.class.getClassLoader().getResourceAsStream("images/background.png"));
            IMAGE_RING = ImageIO.read(Constant.class.getClassLoader().getResourceAsStream("images/ring-removebg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
