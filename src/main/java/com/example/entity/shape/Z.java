package com.example.entity.shape;

import com.example.Constant;
import com.example.entity.Cell;
import com.example.entity.Tetromino;

/**
 * @Author luguorui
 * @Date 2023/8/15
 */
public class Z extends Tetromino {
    public Z(){
        cells[0] = new Cell(1,4, Constant.IMAGE_Z);
        cells[1] = new Cell(0,3, Constant.IMAGE_Z);
        cells[2] = new Cell(0,4, Constant.IMAGE_Z);
        cells[3] = new Cell(1,5, Constant.IMAGE_Z);

        //共有两种旋转状态
        states =new State[2];
        //初始化两种状态的相对坐标
        states[0]=new State(0,0,-1,-1,-1,0,0,1);
        states[1]=new State(0,0,-1,1,0,1,1,0);
    }
}
