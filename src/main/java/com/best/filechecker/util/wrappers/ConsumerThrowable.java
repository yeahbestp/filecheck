package com.best.filechecker.util.wrappers;

@FunctionalInterface
public interface ConsumerThrowable<T> {
    void accept(T t) throws Exception;
}
