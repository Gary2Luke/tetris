package com.example.entity;

import com.example.entity.shape.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;

/**
 * 四方格
 *
 * @Author luguorui
 * @Date 2023/8/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tetromino {
    protected Cell[] cells = new Cell[4];

    protected State[] states;

    protected int count = 10000;

    public static Tetromino randomOne() {
        int randomNum = RandomUtils.nextInt(0, 20);
        if (randomNum <= 7) {
            return new I();
        } else if (randomNum <= 14) {
            return new O();
        } else if (randomNum <= 15) {
            return new J();
        } else if (randomNum <= 16) {
            return new L();
        } else if (randomNum <= 17) {
            return new S();
        } else if (randomNum <= 18) {
            return new T();
        } else if (randomNum <= 19) {
            return new Z();
        }
        return new I();
    }

    public void rotateRight() {
        if (states.length == 0) {
            return;
        }
        count++;
        State state = states[count % states.length];
        int row = cells[0].getRow();
        int col = cells[0].getCol();
        cells[1].setRow(row + state.row1);
        cells[1].setCol(col + state.col1);
        cells[2].setRow(row + state.row2);
        cells[2].setCol(col + state.col2);
        cells[3].setRow(row + state.row3);
        cells[3].setCol(col + state.col3);
    }

    public void rotateLeft() {
        if (states.length == 0) {
            return;
        }
        count--;
        State state = states[count % states.length];
        int row = cells[0].getRow();
        int col = cells[0].getCol();
        cells[1].setRow(row + state.row1);
        cells[1].setCol(col + state.col1);
        cells[2].setRow(row + state.row2);
        cells[2].setCol(col + state.col2);
        cells[3].setRow(row + state.row3);
        cells[3].setCol(col + state.col3);
    }

    public void moveLeft() {
        for (Cell cell : cells) {
            cell.moveLeft();
        }
    }

    public void moveRight() {
        for (Cell cell : cells) {
            cell.moveRight();
        }
    }

    public void moveDown() {
        for (Cell cell : cells) {
            cell.moveDown();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    protected class State {
        int row0;
        int col0;
        int row1;
        int col1;
        int row2;
        int col2;
        int row3;
        int col3;
    }
}
