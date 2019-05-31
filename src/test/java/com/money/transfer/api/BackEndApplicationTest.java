package com.money.transfer.api;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class BackEndApplicationTest {
    @Test
    public void shouldCreateApplicationObject() {
        // when
        BackEndApplication application = new BackEndApplication();

        // then
        assertThat(application).isNotNull();
    }

    @Test
    public void shouldStartApplication() {
        // given
        String[] args = {};

        // when
        BackEndApplication.main(args);

        // then no exception is thrown
    }
}
