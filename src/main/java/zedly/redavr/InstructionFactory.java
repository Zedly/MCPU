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
public interface InstructionFactory {

    Instruction get(int opcode, CPU cpu);

}
