package com.example.entity.shape;

import com.example.Constant;
import com.example.entity.Cell;
import com.example.entity.Tetromino;

/**
 * @Author luguorui
 * @Date 2023/8/15
 */
public class T extends Tetromino {
    public T(){
        cells[0] = new Cell(0,4, Constant.IMAGE_T);
        cells[1] = new Cell(0,3, Constant.IMAGE_T);
        cells[2] = new Cell(0,5, Constant.IMAGE_T);
        cells[3] = new Cell(1,4, Constant.IMAGE_T);

        states=new State[4];
        states[0]=new State(0,0,0,-1,0,1,1,0);
        states[1]=new State(0,0,-1,0,1,0,0,-1);
        states[2]=new State(0,0,0,1,0,-1,-1,0);
        states[3]=new State(0,0,1,0,-1,0,0,1);
    }
}
