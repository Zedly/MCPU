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
public class CALL extends Instruction {

    private final CPU cpu;
    private int k;

    public CALL(int opcode, CPU cpu) {
        this.cpu = cpu;
    }

    @Override
    public void run() {
        int sp = cpu.readShort(CPU.SP);
        int pc = cpu.getPC();
        cpu.writeShort(sp, pc + 1); //Double increment because call is long
        cpu.writeShort(CPU.SP, sp - 2);
        cpu.setPC(k);
    }

    public boolean isLong() {
        return true;
    }

    public void feed(int more) {
        k = more / 2;
    }
}
