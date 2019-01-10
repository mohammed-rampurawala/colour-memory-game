package me.mohammedr.mg.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.mohammedr.mg.ui.game.GameActivity;
import me.mohammedr.mg.ui.game.GameActivityModule;
import me.mohammedr.mg.ui.game.GameFragmentProvider;
import me.mohammedr.mg.ui.score.ScoresActivity;
import me.mohammedr.mg.ui.score.ScoresActivityModule;
import me.mohammedr.mg.ui.start.StartGameActivity;
import me.mohammedr.mg.ui.start.StartGameModule;

@Module
public abstract class ActivityBuilder {

    /**
     * Bind the game acitvity as well as the FragmentProvider module responsible for binding
     * {@link me.mohammedr.mg.ui.game.fragment.GameFragment}
     *
     * @return GameActivity
     */
    @ContributesAndroidInjector(modules = {GameActivityModule.class, GameFragmentProvider.class})
    abstract GameActivity bindGameActivity();

    /**
     * Binds the {@link ScoresActivity} with {@link ScoresActivityModule}
     *
     * @return ScoresActivity
     */
    @ContributesAndroidInjector(modules = {ScoresActivityModule.class})
    abstract ScoresActivity bindScoresActivity();

    /**
     * Binds the {@link StartGameActivity} with {@link StartGameModule}
     *
     * @return StartGameActivity
     */
    @ContributesAndroidInjector(modules = {StartGameModule.class})
    abstract StartGameActivity bindStartGameActivity();
}
