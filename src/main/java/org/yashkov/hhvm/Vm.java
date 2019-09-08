package org.yashkov.hhvm;

public class Vm {
    private static final int CODE_SIZE = 4096;

    private static final int HEAP_SIZE = 8192;

    private static final int STACK_SIZE = 4096;

    private final byte[] code = new byte[CODE_SIZE];

    private final byte[] heap = new byte[HEAP_SIZE];

    private final byte[] stack = new byte[STACK_SIZE];

    private int pc = 0;

    public byte[] getCode()
    {
        return code;
    }

    public byte[] getHeap()
    {
        return heap;
    }

    public byte[] getStack()
    {
        return stack;
    }

    public int getPc()
    {
        return pc;
    }

    public int getSp()
    {
        return stack.length;
    }

    public int getFp()
    {
        return stack.length;
    }

    public void step()
    {
        pc++;
    }
}
