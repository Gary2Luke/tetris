package com.example.entity.shape;

import com.example.Constant;
import com.example.entity.Cell;
import com.example.entity.Tetromino;

/**
 * @Author luguorui
 * @Date 2023/8/15
 */
public class O extends Tetromino {
    public O(){
        cells[0] = new Cell(0, 4, Constant.IMAGE_O);
        cells[1] = new Cell(0, 5, Constant.IMAGE_O);
        cells[2] = new Cell(1, 4, Constant.IMAGE_O);
        cells[3] = new Cell(1, 5,Constant.IMAGE_O);

        //无旋转状态
        states = new State[0];
    }
}
