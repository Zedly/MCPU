package zedly.mcpu;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Dennis
 */
public abstract class Instruction implements Runnable {
    
    public boolean isLong() {
        return false;
    }
    
    public void feed(int more) {        
    }
    
}
