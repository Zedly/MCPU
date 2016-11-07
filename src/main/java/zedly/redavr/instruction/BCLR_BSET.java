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
public class BCLR_BSET extends Instruction {

    private final int s;
    private final CPU cpu;
    private final boolean set;

    public BCLR_BSET(int opcode, CPU cpu) {
        this.cpu = cpu;
        this.set = (opcode & 0x100) != 0;
        s = (opcode & 0b1110000) >> 4;
    }

    public void run() {
        if (set) {
            cpu.memory[CPU.SREG] |= (1 << s);
        } else {
            cpu.memory[CPU.SREG] &= ~(1 << s);
        }
    }
}
