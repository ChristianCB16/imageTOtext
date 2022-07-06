package com.example.textdemo.historialAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textdemo.Model.translateData;
import com.example.textdemo.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;

public class historialAdapter extends FirestoreRecyclerAdapter<translateData, historialAdapter.ViewHolder> {

    private final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    FragmentManager fm;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public historialAdapter(@NonNull FirestoreRecyclerOptions<translateData> options) {
        super(options);
        this.activity = activity;
        this.fm = fm;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull translateData translateData) {

        //DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        //final String id = documentSnapshot.getId();

        viewHolder.sourceTranslate.setText(translateData.getTexto());
        viewHolder.targetTranslate.setText(translateData.getTraduccion());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_translate_single, parent, false);
        return new ViewHolder(v);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sourceTranslate, targetTranslate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sourceTranslate = itemView.findViewById(R.id.sourceTranslate);
            targetTranslate = itemView.findViewById(R.id.targetTranslate);
            //dateTranslate = itemView.findViewById(R.id.dateTranslate);
        }
    }
}
