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
public class ADIW extends Instruction {

    private final int k, d;
    private final ATMega320 cpu;

    public ADIW(int opcode, ATMega320 cpu) {
        this.cpu = cpu;
        d = 24 + 2 * (opcode & 0b110000) >> 4;
        k = (opcode & 0b1111) | ((opcode & 0x11000000) >> 4);
    }

    @Override
    public void run() {
        int rd = cpu.readShort(d);
        int status = cpu.readByte(ATMega320.SREG);

        int sum = rd + k;

        status &= 0b11100000;

        status |= (((sum & 0x80) != 0 && (rd & 0x80) == 0) ? 0x1 : 0);
        status |= ((sum == 0) ? 0x2 : 0);
        status |= (((sum & 0x80) != 0) ? 0x4 : 0);
        status |= (((sum & 0x80) == 0 && (rd & 0x80) != 0) ? 0x8 : 0);
        status |= (((status & 0x8) != 0) != ((status & 0x4) != 0)) ? 0x10 : 0;

        cpu.writeShort(d, sum);
        cpu.writeByte(ATMega320.SREG, status);
    }

}
