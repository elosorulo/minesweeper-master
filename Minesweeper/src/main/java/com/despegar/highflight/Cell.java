package com.despegar.highflight;

public interface Cell<T> {
    boolean isFlagged();

    boolean isCovered();

    T getValue();

    void setValue(T value);

    void unFlag();

    void flag();

    void unCover();
}
