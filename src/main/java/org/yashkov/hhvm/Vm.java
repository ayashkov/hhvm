package org.yashkov.hhvm;

import java.util.Arrays;

public class Vm {
    private static final int CODE_SIZE = 4096;

    private static final int HEAP_SIZE = 8192;

    private static final int STACK_SIZE = 4096;

    private boolean halted = false;

    private final byte[] code = new byte[CODE_SIZE];

    private final byte[] data = new byte[HEAP_SIZE];

    private final byte[] stack = new byte[STACK_SIZE];

    private int pc;

    private int sp;

    private int fp;

    private final Op[] operations = new Op[] {
        () -> halted = true,    /* 0x00 HLT */
        () -> {},               /* 0x01 NOP */
        () -> {                 /* 0x02 SFP */
            stack[--sp] = (byte)((fp >> 24) & 0xff);
            stack[--sp] = (byte)((fp >> 16) & 0xff);
            stack[--sp] = (byte)((fp >> 8) & 0xff);
            stack[--sp] = (byte)(fp & 0xff);
            fp = sp;
        },
        () -> {},               /* 0x03 RFP */
        () -> {},               /* 0x04 */
        () -> {},               /* 0x05 */
        () -> {},               /* 0x06 */
        () -> {},               /* 0x07 */
        () -> {},               /* 0x08 */
        () -> {},               /* 0x09 */
        () -> {},               /* 0x0A */
        () -> {},               /* 0x0B */
        () -> {},               /* 0x0C */
        () -> {},               /* 0x0D */
        () -> {},               /* 0x0E */
        () -> {},               /* 0x0F */
        () -> {                 /* 0x10 LD0 */
            stack[--sp] = 0x00;
            stack[--sp] = 0x00;
            stack[--sp] = 0x00;
            stack[--sp] = 0x00;
        },
        () -> {                 /* 0x11 LD1 */
            stack[--sp] = 0x00;
            stack[--sp] = 0x00;
            stack[--sp] = 0x00;
            stack[--sp] = 0x01;
        },
        () -> {                 /* 0x12 LDC */
            stack[--sp] = code[pc + 3];
            stack[--sp] = code[pc + 2];
            stack[--sp] = code[pc + 1];
            stack[--sp] = code[pc];
            pc += 4;
        },
        () -> {                 /* 0x13 LDF */
        },
        () -> {                 /* 0x14 LDI */
            int addr = stack[sp++];

            addr |= stack[sp++] << 8;
            addr |= stack[sp++] << 16;
            addr |= stack[sp++] << 24;

            stack[--sp] = data[addr + 3];
            stack[--sp] = data[addr + 2];
            stack[--sp] = data[addr + 1];
            stack[--sp] = data[addr];
        }
    };

    public Vm()
    {
        Arrays.fill(code, (byte)0x00);
        Arrays.fill(data, (byte)0xfa);
        Arrays.fill(stack, (byte)0xfb);
        reset();
    }

    public byte[] getCode()
    {
        return code;
    }

    public byte[] getData()
    {
        return data;
    }

    public byte[] getStack()
    {
        return stack;
    }

    public boolean isHalted()
    {
        return halted;
    }

    public int getPc()
    {
        return pc;
    }

    public int getSp()
    {
        return sp;
    }

    public int getFp()
    {
        return fp;
    }

    public void step()
    {
        if (halted)
            return;

        operations[code[pc++]].execute();
    }

    public void reset()
    {
        pc = 0;
        fp = sp = stack.length;
        halted = false;
    }

    @FunctionalInterface
    private interface Op {
        void execute();
    }
}
