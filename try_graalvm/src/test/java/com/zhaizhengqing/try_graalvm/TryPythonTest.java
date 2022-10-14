package com.zhaizhengqing.try_graalvm;

import org.junit.Test;

import java.io.IOException;

public class TryPythonTest {
    @Test
    public void test() throws IOException {
        TryPython tryPython = new TryPython();
        tryPython.tryPython();
    }
}
