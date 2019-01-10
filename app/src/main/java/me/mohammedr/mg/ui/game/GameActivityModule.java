package me.mohammedr.mg.ui.game;

import dagger.Binds;
import dagger.Module;

/**
 * GameActivity module binding the {@link GameActivity} to provide dependencies
 */
@Module
public abstract class GameActivityModule {
    @Binds
    abstract GameActivity providesGameActivity(GameActivity activity);
}
