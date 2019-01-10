package me.mohammedr.mg.ui.game;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.mohammedr.mg.ui.game.fragment.GameFragment;
import me.mohammedr.mg.ui.game.fragment.GameFragmentModule;

/**
 * GameFragmentProvider module binding the {@link GameFragment} to provide dependencies.
 */
@Module
public abstract class GameFragmentProvider {
    @ContributesAndroidInjector(modules = GameFragmentModule.class)
    abstract GameFragment providesGameFragment();
}
