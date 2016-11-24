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
public class ASR extends Instruction {

    private final int d;
    private final ATMega320 cpu;

    public ASR(int opcode, ATMega320 cpu) {
        this.cpu = cpu;
        d = (opcode & 0b111110000) >> 4;
    }

    public void run() {
        int asr = cpu.readByte(d);
        int status = cpu.readByte(ATMega320.SREG);

        status &= 0b11100000;
        status |= asr & 0x1;
        asr = (asr & 0x80) | (asr >> 1);

        status |= ((asr == 0) ? 0x2 : 0);
        status |= (((asr & 0x80) != 0) ? 0x4 : 0x0);
        status |= (((status & 0x4) != 0) != ((status & 0x1) != 0)) ? 0x8 : 0;
        status |= (((status & 0x4) != 0) != ((status & 0x2) != 0)) ? 0x10 : 0;

        cpu.writeByte(d, asr);
        cpu.writeByte(ATMega320.SREG, status);
    }
}
