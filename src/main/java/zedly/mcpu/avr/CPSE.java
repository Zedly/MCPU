package zedly.mcpu.avr;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import zedly.mcpu.avr.ATMega320;

/**
 *
 * @author Dennis
 */
public class CPSE extends Instruction {

    private final int r, d;
    private final ATMega320 cpu;

    public CPSE(int opcode, ATMega320 cpu) {
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
