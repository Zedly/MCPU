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
public class CBI_SBI extends Instruction {

    private final int a, d;
    private final ATMega320 cpu;
    private final boolean set;

    public CBI_SBI(int opcode, ATMega320 cpu) {
        this.cpu = cpu;
        this.set = (opcode & 0x200) != 0;
        a = (opcode & 0b11111000) >> 3;
        d = opcode & 0b111;
    }

    public void run() {
        if (set) {
            cpu.writeByte(ATMega320.IO_BASE + a, cpu.readByte(ATMega320.IO_BASE + a) | (1 << d));
        } else {
            cpu.writeByte(ATMega320.IO_BASE + a, cpu.readByte(ATMega320.IO_BASE + a) & ~(1 << d));
        }
    }
}
