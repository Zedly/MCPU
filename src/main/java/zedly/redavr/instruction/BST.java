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
public class BST extends Instruction {

    private final int b, d;
    private final CPU cpu;

    public BST(int opcode, CPU cpu) {
        this.cpu = cpu;
        d = (opcode & 0b111110000) >> 4;
        b = opcode & 0b111;
    }

    public void run() {
        if ((cpu.readByte(d) & ~(1 << b)) != 0) {
            cpu.writeByte(CPU.SREG, cpu.readByte(CPU.SREG) | 0b1000000);
        } else {
            cpu.writeByte(CPU.SREG, cpu.readByte(CPU.SREG) & 0b10111111);
        }
    }
}
