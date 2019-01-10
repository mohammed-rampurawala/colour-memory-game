package me.mohammedr.mg.ui.start;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class StartGameModule {
    @Binds
    abstract StartGameActivity provideStartGameActivity(StartGameActivity activity);
}
