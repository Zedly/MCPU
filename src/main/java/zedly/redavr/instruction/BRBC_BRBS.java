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
public class BRBC_BRBS extends Instruction {

    private final CPU cpu;
    private final int k, s;
    private final boolean set;

    public BRBC_BRBS(int opcode, CPU cpu) {
        this.cpu = cpu;
        this.s = opcode & 0b111;
        set = (opcode & 0b10000000000) != 0;
        int _k = (opcode & 0b1111111000) >> 3;
        if ((_k & 0b1000000) != 0) {
            k = -64 + (~_k & 0b111111);
        } else {
            k = _k;
        }
    }

    @Override
    public void run() {
        if (((cpu.memory[CPU.SREG] & (1 << s)) != 0) == set) {
            cpu.PC += k;
        }
    }

}
