/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.redavr;

import zedly.redavr.instruction.*;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author Dennis
 */
public class CPU {

    private static final int PROGRAM_MEMORY_SIZE = 16384;
    private static final InstructionFactory factory;

    public static final int IO_BASE = 32;
    public static final int XIO_BASE = 96;
    public static final int SRAM_BASE = 256;
    public static final int SREG = 0x3F;

    public int[] memory = new int[2304];

    private Instruction[] program = new Instruction[PROGRAM_MEMORY_SIZE];
    private int PC = 0;
    
    public void tick() {
        
        // Copy block data into I/O
        
        program[PC++].run();
        
        // Copy I/O into block data
        
    }
    

    public void loadProgram(short[] programMemory) throws IOException {
        for (int instructionIndex = 0; instructionIndex < programMemory.length; instructionIndex++) {
            int op = programMemory[instructionIndex];
            Instruction ins = factory.get(op, this);

            program[instructionIndex] = ins;

            if (ins.isLong()) {
                ins.feed(programMemory[++instructionIndex]);
                program[instructionIndex] = new NOP(0, this);
            }
        }
    }

    static {
        InstructionFactory f0x = new OpcodeMask(0,
                (op, cpu) -> new NOP(op, cpu),
                (op, cpu) -> new NOP(op, cpu));
        InstructionFactory f1x = new OpcodeMask(0,
                (op, cpu) -> new NOP(op, cpu),
                (op, cpu) -> new NOP(op, cpu));

        factory = new OpcodeMask(1, f0x, f1x);
    }
}
