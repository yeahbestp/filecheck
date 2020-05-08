package com.best.filechecker.util.wrappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsumerWrapper {

    public static <T> Consumer<T> consumerWrapper(ConsumerThrowable<T> consumerThrowable) {
        return t -> {
            try {
                consumerThrowable.accept(t);
            } catch (Exception e) {
                throw new IllegalArgumentException("Exception while handling supplier throwable", e);
            }
        };
    }
}
