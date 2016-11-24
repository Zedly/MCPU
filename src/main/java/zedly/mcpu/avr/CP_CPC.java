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
public class CP_CPC extends Instruction {

    private final boolean carry;
    private final int r, d;
    private final ATMega320 cpu;

    public CP_CPC(int opcode, ATMega320 cpu) {
        this.cpu = cpu;
        carry = (opcode & 0x100) == 0;
        r = (opcode & 0xF) + (opcode & 0b1000000000) >> 5;
        d = (opcode & 0b111110000) >> 4;
    }

    @Override
    public void run() {
        int rr = cpu.readByte(r);
        int rd = cpu.readByte(d);
        int status = cpu.readByte(ATMega320.SREG);

        int cp = rd - rr;
        if (carry) {
            cp -= status & 0x1;
        }

        status &= 0b11000000;
        status |= (((0x80 & ((~rd & rr) | (rr & cp) | (cp & ~rd))) != 0)) ? 0x1 : 0;
        status |= (cp == 0) ? (status & 0x2) : 0;
        status |= (((cp & 0x80) != 0) ? 0x4 : 0);
        status |= ((0x80 & ((rd & ~rr & ~cp) | (~rd & rr & cp))) != 0) ? 0x8 : 0;
        status |= (((status & 0x8) != 0) != ((status & 0x4) != 0)) ? 0x10 : 0;
        status |= ((0x8 & ((~rd & rr) | (cp & rr) | (cp & ~rd)) ) != 0) ? 0x20 : 0;

        cpu.writeByte(ATMega320.SREG, status);
    }
}
