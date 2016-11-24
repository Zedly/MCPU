package zedly.mcpu.avr;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import zedly.mcpu.Instruction;
import zedly.mcpu.avr.ATMega320;

/**
 *
 * @author Dennis
 */
public class NOP extends Instruction {
    
    public NOP(int opcode, ATMega320 cpu) {
    }
    
    public void run() {
    }
    
}
