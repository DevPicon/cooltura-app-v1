package pe.apiconz.android.cooltura.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.apiconz.android.cooltura.app.R;

/**
 * Created by Armando on 11/14/2015.
 */
public abstract class BaseFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getFragmentLayoutResourceId(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    protected abstract int getFragmentLayoutResourceId();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
