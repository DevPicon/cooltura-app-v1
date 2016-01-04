package pe.apiconz.android.cooltura.app.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Armando on 11/14/2015.
 */
public abstract class BaseFragment extends Fragment {

    private String TAG = BaseFragment.class.getCanonicalName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getFragmentLayoutResourceId(), container, false);
        rootView.setTag(TAG);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    protected abstract int getFragmentLayoutResourceId();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onCreateView();
        onCreateView(view);
    }

    protected abstract void onCreateView();

    protected void onCreateView(View view) {
    }

    ;
}
