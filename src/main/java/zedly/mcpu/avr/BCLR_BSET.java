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
public class BCLR_BSET extends Instruction {

    private final int s;
    private final ATMega320 cpu;
    private final boolean set;

    public BCLR_BSET(int opcode, ATMega320 cpu) {
        this.cpu = cpu;
        this.set = (opcode & 0x100) != 0;
        s = (opcode & 0b1110000) >> 4;
    }

    public void run() {
        if (set) {
            cpu.writeByte(ATMega320.SREG, cpu.readByte(ATMega320.SREG) | (1 << s));
        } else {
            cpu.writeByte(ATMega320.SREG, cpu.readByte(ATMega320.SREG) & ~(1 << s));
        }
    }
}
