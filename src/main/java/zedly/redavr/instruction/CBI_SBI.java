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
public class CBI_SBI extends Instruction {

    private final int a, d;
    private final CPU cpu;
    private final boolean set;

    public CBI_SBI(int opcode, CPU cpu) {
        this.cpu = cpu;
        this.set = (opcode & 0x200) != 0;
        a = (opcode & 0b11111000) >> 3;
        d = opcode & 0b111;
    }

    public void run() {
        if (set) {
            cpu.writeByte(CPU.IO_BASE + a, cpu.readByte(CPU.IO_BASE + a) | (1 << d));
        } else {
            cpu.writeByte(CPU.IO_BASE + a, cpu.readByte(CPU.IO_BASE + a) & ~(1 << d));
        }
    }
}
