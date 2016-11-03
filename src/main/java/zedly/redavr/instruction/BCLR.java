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
public class BCLR extends Instruction {

    private final int s;
    private final CPU cpu;

    public BCLR(int opcode, CPU cpu) {
        this.cpu = cpu;
        s = (opcode & 0b1110000) >> 4;
    }

    public void run() {
        cpu.memory[CPU.SREG] &= ~(1 << s);
    }
}
