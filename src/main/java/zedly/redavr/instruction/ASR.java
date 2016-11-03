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
public class ASR extends Instruction {

    private final int d;
    private final CPU cpu;

    public ASR(int opcode, CPU cpu) {
        this.cpu = cpu;
        d = (opcode & 0b111110000) >> 4;
    }

    public void run() {
        int asr = cpu.memory[d];

        int status = cpu.memory[CPU.SREG];
        status &= 0b11100000;

        status |= asr & 0x1;

        asr = (asr & 0x80) | (asr >> 1);

        status |= ((asr == 0) ? 0x2 : 0);
        status |= (((asr & 0x80) != 0) ? 0x4 : 0x0);
        status |= (((status & 0x4) != 0) != ((status & 0x1) != 0)) ? 0x8 : 0;
        status |= (((status & 0x4) != 0) != ((status & 0x2) != 0)) ? 0x10 : 0;

        cpu.memory[d] = asr & 0xFF;
        cpu.memory[CPU.SREG] = status;
    }
}
