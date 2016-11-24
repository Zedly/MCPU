package zedly.mcpu.avr;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import zedly.mcpu.avr.ATMega320;

/**
 *
 * @author Dennis
 */
public class BST extends Instruction {

    private final int b, d;
    private final ATMega320 cpu;

    public BST(int opcode, ATMega320 cpu) {
        this.cpu = cpu;
        d = (opcode & 0b111110000) >> 4;
        b = opcode & 0b111;
    }

    public void run() {
        if ((cpu.readByte(d) & ~(1 << b)) != 0) {
            cpu.writeByte(ATMega320.SREG, cpu.readByte(ATMega320.SREG) | 0b1000000);
        } else {
            cpu.writeByte(ATMega320.SREG, cpu.readByte(ATMega320.SREG) & 0b10111111);
        }
    }
}
