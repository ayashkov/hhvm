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
        assertThat(vm.getHeap()).isNotNull();
        assertThat(vm.getHeap()).hasSize(8 * 1024);
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
    void getHeap_ReturnsTheSameArray_Always()
    {
        assertThat(vm.getHeap()).isSameAs(vm.getHeap());
    }

    @Test
    void getStack_ReturnsTheSameArray_Always()
    {
        assertThat(vm.getStack()).isSameAs(vm.getStack());
    }

    @Test
    void step_AdvancesPc_WhenNoOpInstruction()
    {
        byte[] load = new byte[] { 0x01 };

        System.arraycopy(load, 0, vm.getCode(), 0, load.length);

        vm.step();

        assertThat(vm.getPc()).isEqualTo(1);
    }
}
