package me.mohammedr.mg.ui.score;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ScoresActivityModule {

    @Binds
    abstract ScoresActivity provideScoresActivity(ScoresActivity activity);
}
