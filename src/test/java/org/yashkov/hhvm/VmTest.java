package org.yashkov.hhvm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class VmTest {
    @Test
    void constructor_CreatesInstance_Normally()
    {
        Vm vm = new Vm();

        assertThat(vm.getCode()).isNotNull();
        assertThat(vm.getCode()).hasSize(4 * 1024);
        assertThat(vm.getHeap()).isNotNull();
        assertThat(vm.getHeap()).hasSize(8 * 1024);
        assertThat(vm.getStack()).isNotNull();
        assertThat(vm.getStack()).hasSize(4 * 1024);
    }
}
