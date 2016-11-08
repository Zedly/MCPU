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
public class CPSE extends Instruction {

    private final int r, d;
    private final CPU cpu;

    public CPSE(int opcode, CPU cpu) {
        this.cpu = cpu;
        r = (opcode & 0xF) + (opcode & 0b1000000000) >> 5;
        d = (opcode & 0b111110000) >> 4;
    }

    @Override
    public void run() {
        if (cpu.readByte(r) == cpu.readByte(d)) {
            cpu.skip();
        }
    }
}
