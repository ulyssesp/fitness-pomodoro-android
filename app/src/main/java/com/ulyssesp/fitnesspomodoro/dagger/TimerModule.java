package com.ulyssesp.fitnesspomodoro.dagger;

import com.ulyssesp.fitnesspomodoro.Constants;
import com.ulyssesp.fitnesspomodoro.data.timer.TimerStore;
import com.ulyssesp.fitnesspomodoro.flrx.Dispatcher;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TimerModule {
    @Provides
    @Singleton
    TimerStore provideTimerStore(Dispatcher<Constants.Actions> dispatcher) {
        return new TimerStore(dispatcher);
    }
}
