/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.mcpu.avr;

import zedly.mcpu.Instruction;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author Dennis
 */
public class ATMega320 {

    private static final InstructionFactory factory;
    private final Instruction[] program = new Instruction[PROGRAM_MEMORY_SIZE / 2];
    private final int[] memory = new int[2304];
    private int PC = 0;

    public static final int PROGRAM_MEMORY_SIZE = 32768;
    public static final int SRAM_SIZE = 2048;
    public static final int IO_BASE = 32;
    public static final int XIO_BASE = 96;
    public static final int SRAM_BASE = 256;

    public static final int PINB = 0x23;
    public static final int DDRB = 0x24;
    public static final int PORTB = 0x25;
    public static final int PINC = 0x26;
    public static final int DDRC = 0x27;
    public static final int PORTC = 0x28;
    public static final int PIND = 0x29;
    public static final int DDRD = 0x2A;
    public static final int PORTD = 0x2B;
    public static final int TIFR0 = 0x35;
    public static final int TIFR1 = 0x36;
    public static final int TIFR2 = 0x37;
    public static final int PCIFR = 0x3B;
    public static final int EIFR = 0x3C;
    public static final int EIMSK = 0x3D;
    public static final int GPIOR0 = 0x3E;
    public static final int EECR = 0x3F;
    public static final int EEDR = 0x40;
    public static final int EEAR = 0x41;
    public static final int GTCCR = 0x43;
    public static final int TCCR0A = 0x44;
    public static final int TCCR0B = 0x45;
    public static final int TCNT0 = 0x46;
    public static final int OCR0A = 0x47;
    public static final int OCR0B = 0x48;
    public static final int GPIOR1 = 0x4A;
    public static final int GPIOR2 = 0x4B;
    public static final int SPCR = 0x4C;
    public static final int SPSR = 0x4D;
    public static final int SPDR = 0x4E;
    public static final int ACSR = 0x50;
    public static final int SMCR = 0x53;
    public static final int MCUSR = 0x54;
    public static final int MCUCR = 0x55;
    public static final int SPMCSR = 0x57;
    public static final int SP = 0x5D;
    public static final int SREG = 0x5F;

    public static final int WDTCSR = 0x60;
    public static final int CLKPR = 0x61;
    public static final int PRR = 0x64;
    public static final int OSCCAL = 0x66;
    public static final int PCICR = 0x68;
    public static final int EICRA = 0x69;
    public static final int PCMSK0 = 0x6B;
    public static final int PCMSK1 = 0x6C;
    public static final int PCMSK2 = 0x6D;
    public static final int TIMSK0 = 0x6E;
    public static final int TIMSK1 = 0x6F;
    public static final int TIMSK2 = 0x70;
    public static final int ADC = 0x78;
    public static final int ADCSRA = 0x7A;
    public static final int ADCSRB = 0x7B;
    public static final int ADCMUX = 0x7C;
    public static final int DIDR0 = 0x7E;
    public static final int DIDR1 = 0x7F;
    public static final int TCCR1A = 0x80;
    public static final int TCCR1B = 0x81;
    public static final int TCCR1C = 0x82;
    public static final int TCNT1 = 0x84;
    public static final int ICR1 = 0x86;
    public static final int OCR1A = 0x88;
    public static final int OCR1B = 0x8A;
    public static final int TCCR2A = 0xB0;
    public static final int TCCR2B = 0xB1;
    public static final int TCNT2 = 0xB2;
    public static final int OCR2A = 0xB3;
    public static final int OCR2B = 0xB4;
    public static final int ASSR = 0xB6;
    public static final int TWBR = 0xB8;
    public static final int TWSR = 0xB9;
    public static final int TWAR = 0xBA;
    public static final int TWDR = 0xBB;
    public static final int TWCR = 0xBC;
    public static final int TWAMR = 0xBD;
    public static final int UCSR0A = 0xC0;
    public static final int UCSR0B = 0xC1;
    public static final int UCSR0C = 0xC2;
    public static final int UBRR0 = 0xC4;
    public static final int UDR0 = 0xC6;

    public ATMega320() {

    }

    public int readInt(int address) {
        return readShort(address + 2) << 16
                + readShort(address);
    }

    public int readShort(int address) {
        return (readByte(address + 1) << 8)
                + readByte(address);
    }

    public int readByte(int address) {
        if (address < 0 || address >= SRAM_BASE + SRAM_SIZE) {
            return 0;
        }
        return memory[address];
    }

    public void writeInt(int address, int value) {
        writeShort(address, value);
        writeShort(address + 2, value >> 16);
    }

    public void writeShort(int address, int value) {
        writeByte(address, value);
        writeByte(address + 1, value >> 8);
    }

    public void writeByte(int address, int value) {
        if (address >= 0 && address < SRAM_BASE + SRAM_SIZE) {
            memory[address] = value & 0xFF;
        }
    }

    public int getPC() {
        return PC;
    }

    public void setPC(int newPC) {
        PC = newPC;
    }

    public void skip() {
        if (program[PC].isLong()) {
            PC += 2;
        } else {
            PC++;
        }
    }

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
