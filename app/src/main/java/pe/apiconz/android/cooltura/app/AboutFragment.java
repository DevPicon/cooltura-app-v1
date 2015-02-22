package pe.apiconz.android.cooltura.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout inflate = (LinearLayout) inflater.inflate(R.layout.fragment_about, null);
        TextView txtView = (TextView) inflate.findViewById(R.id.txtAppVersion);
        txtView.setText(BuildConfig.VERSION_NAME);

        builder.setView(inflate).setPositiveButton(R.string.dialog_about_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
