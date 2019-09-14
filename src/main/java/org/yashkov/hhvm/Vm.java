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
        return stack.length;
    }

    public void step()
    {
        if (halted)
            return;

        switch (code[pc++]) {
        case 0x00:
            halted = true;

            break;
        case 0x10:
            stack[--sp] = 0x00;
            stack[--sp] = 0x00;
            stack[--sp] = 0x00;
            stack[--sp] = 0x00;

            break;
        case 0x11:
            stack[--sp] = 0x00;
            stack[--sp] = 0x00;
            stack[--sp] = 0x00;
            stack[--sp] = 0x01;

            break;
        }
    }

    public void reset()
    {
        pc = 0;
        sp = stack.length;
        halted = false;
    }
}
