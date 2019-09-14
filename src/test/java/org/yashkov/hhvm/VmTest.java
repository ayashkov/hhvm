package org.yashkov.hhvm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class VmTest {
    private final Vm vm = new Vm();

    @Test
    void constructor_CreatesInstance_Normally()
    {
        assertThat(vm.getCode()).isNotNull();
        assertThat(vm.getCode()).hasSize(4 * 1024);
        assertThat(vm.getData()).isNotNull();
        assertThat(vm.getData()).hasSize(8 * 1024);
        assertThat(vm.getStack()).isNotNull();
        assertThat(vm.getStack()).hasSize(4 * 1024);
        assertThat(vm.getPc()).isEqualTo(0);
        assertThat(vm.getSp()).isEqualTo(4 * 1024);
        assertThat(vm.getFp()).isEqualTo(4 * 1024);
    }

    @Test
    void getCode_ReturnsTheSameArray_Always()
    {
        assertThat(vm.getCode()).isSameAs(vm.getCode());
    }

    @Test
    void getData_ReturnsTheSameArray_Always()
    {
        assertThat(vm.getData()).isSameAs(vm.getData());
    }

    @Test
    void getStack_ReturnsTheSameArray_Always()
    {
        assertThat(vm.getStack()).isSameAs(vm.getStack());
    }

    @Test
    void step_OnlyAdvancesPc_WhenNoOpInstruction()
    {
        loadCode((byte)0x01, (byte)0x01);

        vm.step();

        assertThat(vm.isHalted()).isFalse();
        assertThat(vm.getPc()).isEqualTo(1);
        assertThat(vm.getSp()).isEqualTo(4 * 1024);
        assertThat(vm.getFp()).isEqualTo(4 * 1024);

        vm.step();

        assertThat(vm.getPc()).isEqualTo(2);
        assertThat(vm.isHalted()).isFalse();
    }

    @Test
    void step_HaltsExceution_WhenHaltInstruction()
    {
        loadCode((byte)0x00, (byte)0x01);

        vm.step();

        assertThat(vm.isHalted()).isTrue();
        assertThat(vm.getPc()).isEqualTo(1);
        assertThat(vm.getSp()).isEqualTo(4 * 1024);
        assertThat(vm.getFp()).isEqualTo(4 * 1024);

        vm.step();

        assertThat(vm.isHalted()).isTrue();
        assertThat(vm.getPc()).isEqualTo(1);
    }


    @Test
    void step_PushesZero_WhenLoadZeroInstruction()
    {
        loadCode((byte)0x10);

        vm.step();

        assertThat(vm.isHalted()).isFalse();
        assertThat(vm.getPc()).isEqualTo(1);
        assertThat(vm.getFp()).isEqualTo(4 * 1024);

        byte[] stack = vm.getStack();
        int sp = vm.getSp();

        assertThat(sp).isEqualTo(4 * 1024 - 4);
        assertThat(stack[sp]).isEqualTo((byte)0x00);
        assertThat(stack[sp + 1]).isEqualTo((byte)0x00);
        assertThat(stack[sp + 2]).isEqualTo((byte)0x00);
        assertThat(stack[sp + 3]).isEqualTo((byte)0x00);
    }

    @Test
    void step_PushesOne_WhenLoadOneInstruction()
    {
        loadCode((byte)0x11);

        vm.step();

        assertThat(vm.isHalted()).isFalse();
        assertThat(vm.getPc()).isEqualTo(1);
        assertThat(vm.getFp()).isEqualTo(4 * 1024);

        byte[] stack = vm.getStack();
        int sp = vm.getSp();

        assertThat(sp).isEqualTo(4 * 1024 - 4);
        assertThat(stack[sp]).isEqualTo((byte)0x01);
        assertThat(stack[sp + 1]).isEqualTo((byte)0x00);
        assertThat(stack[sp + 2]).isEqualTo((byte)0x00);
        assertThat(stack[sp + 3]).isEqualTo((byte)0x00);
    }

    @Test
    void reset_ResetsVM_Always()
    {
        loadCode((byte)0x10, (byte)0x00);
        vm.step();
        vm.step();

        vm.reset();

        assertThat(vm.isHalted()).isFalse();
        assertThat(vm.getPc()).isEqualTo(0);
        assertThat(vm.getSp()).isEqualTo(4 * 1024);
        assertThat(vm.getFp()).isEqualTo(4 * 1024);
    }

    private void loadCode(byte... code)
    {
        System.arraycopy(code, 0, vm.getCode(), 0, code.length);
    }

    private void loadData(int address, byte... data)
    {
        System.arraycopy(data, 0, vm.getData(), address, data.length);
    }
}
