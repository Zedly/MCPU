/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.redavr.instruction;

import zedly.redavr.CPU;

/**
 *
 * @author Dennis
 */
public class DEC extends Instruction {

    private final int d;
    private final CPU cpu;

    public DEC(int opcode, CPU cpu) {
        this.cpu = cpu;
        d = (opcode & 0b111110000) >> 4;
    }

    @Override
    public void run() {
        int rd = cpu.readByte(d) - 1;
        int status = cpu.readByte(CPU.SREG);

        status &= 0b11100001;

        status |= (rd == 0) ? 0x2 : 0;
        status |= (((rd & 0x80) != 0) ? 0x4 : 0);
        status |= (rd == 0x7F) ? 0x8 : 0;
        status |= (((status & 0x8) != 0) != ((status & 0x4) != 0)) ? 0x10 : 0;

        cpu.writeByte(d, rd);
        cpu.writeByte(CPU.SREG, status);
    }

}
