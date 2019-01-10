package me.mohammedr.mg.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import me.mohammedr.mg.db.DatabaseRepository;
import me.mohammedr.mg.db.IPersistenceRepository;
import me.mohammedr.mg.db.MemoryGameDatabase;
import me.mohammedr.mg.ui.view_model.DBViewModelFactory;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    MemoryGameDatabase provideMemoryDatabase(Context context) {
        return Room.databaseBuilder(context, MemoryGameDatabase.class, "memory_db")
                .fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    IPersistenceRepository provideRepository(MemoryGameDatabase database) {
        return new DatabaseRepository(database);
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    DBViewModelFactory provideGameModelFactory(IPersistenceRepository repository) {
        return new DBViewModelFactory(repository);
    }

}
