package org.dorkmaster.mandlebrot.common.util;

public class Timer {
    long start = System.currentTimeMillis();

    public long time() {
        return System.currentTimeMillis() - start;
    }
}
