package zedly.mcpu.avr;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import zedly.mcpu.Instruction;
import zedly.mcpu.avr.ATMega320;

/**
 *
 * @author Dennis
 */
public class MOVW extends Instruction {

    private final int rd, rr;
    private final ATMega320 cpu;

    public MOVW(int opcode, ATMega320 cpu) {
        this.cpu = cpu;
        rd = 2 * (opcode & 0xF0) >> 4;
        rr = 2 * (opcode & 0x0F);
    }

    public void run() {
        cpu.writeShort(rd, cpu.readShort(rr));
    }

}
