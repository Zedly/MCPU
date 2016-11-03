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
public class ADIW extends Instruction {

    private final int k, d;
    private final CPU cpu;

    public ADIW(int opcode, CPU cpu) {
        this.cpu = cpu;
        d = (opcode & 0b110000) >> 4;
        k = (opcode & 0b1111) | ((opcode & 0x11000000) >> 4);
    }

    @Override
    public void run() {
        int rd = cpu.memory[24 + 2 * d] + cpu.memory[26 + 2 * d];
        int status = cpu.memory[CPU.SREG];

        int sum = rd + k;

        status &= 0b11100000;

        status |= (((sum & 0x80) != 0 && (rd & 0x80) == 0) ? 0x1 : 0);
        status |= ((sum == 0) ? 0x2 : 0);
        status |= (((sum & 0x80) != 0) ? 0x4 : 0);
        status |= (((sum & 0x80) == 0 && (rd & 0x80) != 0) ? 0x8 : 0);
        status |= (((status & 0x8) != 0) != ((status & 0x4) != 0)) ? 0x10 : 0;

        cpu.memory[24 + 2 * d] = (sum >> 8) & 0xFF;
        cpu.memory[26 + 2 * d] = sum & 0xFF;
        cpu.memory[CPU.SREG] = status;
    }

}
