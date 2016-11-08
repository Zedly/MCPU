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
public class CPI extends Instruction {

    private final boolean carry;
    private final int k, d;
    private final CPU cpu;

    public CPI(int opcode, CPU cpu) {
        this.cpu = cpu;
        carry = (opcode & 0x100) == 0;
        k = (opcode & 0xF) + (opcode & 0xF00) >> 4;
        d = (opcode & 0b111110000) >> 4;
    }

    @Override
    public void run() {
        int rd = cpu.readByte(d);
        int status = cpu.readByte(CPU.SREG);

        int cp = rd - k;
        if (carry) {
            cp -= status & 0x1;
        }

        status &= 0b11000000;
        status |= (((0x80 & ((~rd & k) | (k & cp) | (cp & ~rd))) != 0)) ? 0x1 : 0;
        status |= (cp == 0) ? (status & 0x2) : 0;
        status |= (((cp & 0x80) != 0) ? 0x4 : 0);
        status |= ((0x80 & ((rd & ~k & ~cp) | (~rd & k & cp))) != 0) ? 0x8 : 0;
        status |= (((status & 0x8) != 0) != ((status & 0x4) != 0)) ? 0x10 : 0;
        status |= ((0x8 & ((~rd & k) | (cp & k) | (cp & ~rd))) != 0) ? 0x20 : 0;

        cpu.writeByte(CPU.SREG, status);
    }
}
