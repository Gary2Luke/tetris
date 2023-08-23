package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;

/**
 * 单元格
 *
 * @Author luguorui
 * @Date 2023/8/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cell {
    private int row;

    private int col;

    private BufferedImage image;

    public void moveLeft() {
        col--;
    }

    public void moveRight() {
        col++;
    }

    public void moveDown() {
        row++;
    }
}
