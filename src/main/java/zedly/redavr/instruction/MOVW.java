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
public class MOVW extends Instruction {

    private final int rd, rr;
    private final CPU cpu;

    public MOVW(int opcode, CPU cpu) {
        this.cpu = cpu;
        rd = 2 * (opcode & 0xF0) >> 4;
        rr = 2 * (opcode & 0x0F);
    }

    public void run() {
        cpu.writeShort(rd, cpu.readShort(rr));
    }

}
