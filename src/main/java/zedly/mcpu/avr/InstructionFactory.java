/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.mcpu.avr;

import zedly.mcpu.Instruction;

/**
 *
 * @author Dennis
 */
public interface InstructionFactory {

    Instruction get(int opcode, ATMega320 cpu);

}
