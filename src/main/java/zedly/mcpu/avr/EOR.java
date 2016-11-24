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
public class EOR extends Instruction {

    private final int d, r;
    private final ATMega320 cpu;

    public EOR(int opcode, ATMega320 cpu) {
        this.cpu = cpu;
        r = (opcode & 0xF) + (opcode & 0b1000000000) >> 5;
        d = (opcode & 0b111110000) >> 4;
    }

    public void run() {

        int eor = cpu.readByte(r) ^ cpu.readByte(d);
        int status = cpu.readByte(ATMega320.SREG);

        status &= 0b11100001;

        status |= ((eor == 0) ? 0x2 : 0x0);
        status |= (((eor & 0x80) != 0) ? 0x4 : 0x0);
        status |= (((status & 0x2) != 0) != ((status & 0x4) != 0)) ? 0x10 : 0;

        cpu.writeByte(d, eor);
        cpu.writeByte(ATMega320.SREG, status);
    }
}
