package me.mohammedr.mg;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import me.mohammedr.mg.di.AppComponent;
import me.mohammedr.mg.di.DaggerAppComponent;

public class MGApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
