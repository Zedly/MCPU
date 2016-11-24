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
public class CALL extends Instruction {

    private final ATMega320 cpu;
    private int k;

    public CALL(int opcode, ATMega320 cpu) {
        this.cpu = cpu;
    }

    @Override
    public void run() {
        int sp = cpu.readShort(ATMega320.SP);
        int pc = cpu.getPC();
        cpu.writeShort(sp, pc + 1); //Double increment because call is long
        cpu.writeShort(ATMega320.SP, sp - 2);
        cpu.setPC(k);
    }

    public boolean isLong() {
        return true;
    }

    public void feed(int more) {
        k = more / 2;
    }
}
