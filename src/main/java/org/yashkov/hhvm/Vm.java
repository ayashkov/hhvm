package org.yashkov.hhvm;

public class Vm {
    public byte[] getCode()
    {
        return new byte[4096];
    }

    public byte[] getHeap()
    {
        return new byte[8192];
    }

    public byte[] getStack()
    {
        return new byte[4096];
    }
}
