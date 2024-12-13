package com.healthmate.view.visite;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.healthmate.database.AppDatabase;
import com.healthmate.database.bean.Referto;

import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RefertoViewModel extends ViewModel {
    private final AppDatabase appDatabase;
    private final ExecutorService executorService;

    public RefertoViewModel(@NonNull Application application) {
        appDatabase = AppDatabase.getDatabase(application);
        executorService = Executors.newSingleThreadExecutor(); // Pool di thread singolo
    }

    public static class Factory implements ViewModelProvider.Factory {
        private Application application;

        public Factory(Application application) {
            this.application = application;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(RefertoViewModel.class)) {
                //noinspection unchecked
                return (T) new RefertoViewModel(application);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

    public LiveData<List<Referto>> getReferti() {
        return (LiveData<List<Referto>>) appDatabase.cartellaClinicaDAO().showReferti();
    }

    public void addReferto(Referto referto) {
        executorService.execute(() -> appDatabase.cartellaClinicaDAO().addReferto(referto));
    }

    public void deleteReferto(Referto referto) {
        executorService.execute(() -> appDatabase.cartellaClinicaDAO().deleteReferto(referto));
    }

    public void updateReferto(Referto referto) {
        executorService.execute(() -> appDatabase.cartellaClinicaDAO().updateReferto(referto));
    }
}
