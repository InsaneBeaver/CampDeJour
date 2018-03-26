package palisson.bdeb.qc.ca.bdebgarde;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class EnfantsAdapter extends ArrayAdapter<Enfant> {
    public EnfantsAdapter(Context context, ArrayList<Enfant> enfants){
        super(context,0, enfants);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Enfant enfant = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.enfant_list, parent, false);

        TextView tvNom = (TextView) convertView.findViewById(R.id.tvNom);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        tvNom.setText(enfant.getPrenom());
        tvDate.setText(enfant.stringDateNaissance());

        return convertView;
    }
}
