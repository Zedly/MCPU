/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.redavr;

import zedly.redavr.instruction.Instruction;

/**
 *
 * @author Dennis
 */
public class OpcodeMask implements InstructionFactory {

    private final int maskBit;
    private final InstructionFactory zero, one;

    public OpcodeMask(int maskBit, InstructionFactory zero, InstructionFactory one) {
        this.maskBit = maskBit;
        this.zero = zero;
        this.one = one;
    }

    public Instruction get(int opcode, CPU cpu) {
        if (((opcode >> maskBit) & 1) == 0) {
            return zero.get(opcode, cpu);
        } else {
            return one.get(opcode, cpu);
        }
    }

}
