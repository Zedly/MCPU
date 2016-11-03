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
public class ADD_ADC extends Instruction {

    private final boolean carry;
    private final int r, d;
    private final CPU cpu;

    public ADD_ADC(int opcode, CPU cpu) {
        this.cpu = cpu;
        carry = (opcode & 0x100) != 0;
        r = (opcode & 0xF) + (opcode & 0b1000000000) >> 5;
        d = (opcode & 0b111110000) >> 4;
    }

    @Override
    public void run() {
        int rr = cpu.memory[r];
        int rd = cpu.memory[d];
        int status = cpu.memory[CPU.SREG];

        int sum = rr + rd;
        if (carry) {
            sum += status & 0x1;
        }

        status &= 0b11000000;

        status |= ((sum >= 256) ? 0x1 : 0);
        status |= ((sum == 0) ? 0x2 : 0);
        status |= (((sum & 0x40) != 0) ? 0x4 : 0);
        status |= ((((~sum & rr & rd & 0x40)
                | (~rd & ~rr & sum & 0x40)) != 0) ? 0x8 : 0);
        status |= (((status & 0x8) != 0) != ((status & 0x4) != 0)) ? 0x10 : 0;
        status |= (((0x8 & rd & rr)
                | (0x8 & ~sum & rr)
                | (0x8 & ~sum & rd)) != 0) ? 0x20 : 0;

        cpu.memory[rd] = sum & 0xFF;
        cpu.memory[CPU.SREG] = status;
    }

}
