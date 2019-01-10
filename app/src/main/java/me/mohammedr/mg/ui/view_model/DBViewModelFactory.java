package me.mohammedr.mg.ui.view_model;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import me.mohammedr.mg.db.IPersistenceRepository;


public class DBViewModelFactory implements ViewModelProvider.Factory {
    private final IPersistenceRepository repository;

    @Inject
    public DBViewModelFactory(IPersistenceRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DBViewModel(repository);
    }
}
