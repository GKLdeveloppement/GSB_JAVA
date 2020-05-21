package com.example.gsb;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class NotesAdapteur extends RecyclerView.Adapter<NotesAdapteur.NotesHoldeur> {

    private Context _context;
    private String _libelleArray[];
    private Double  _montantArray[];
    private Integer  _quantiteArray[];

    public NotesAdapteur(Context context, List<String> libelleArray, List<Double> montantArray, List<Integer>  quantiteArray) {
        _context = context;
        _libelleArray = libelleArray.toArray(new String[0]);
        _montantArray = montantArray.toArray(new Double[0]);
        _quantiteArray = quantiteArray.toArray(new Integer[0]);
    }

    public Integer[] nouvellesQuantite() {
        return _quantiteArray;
    }

    public String nouvellesQuantiteString() {
        return Arrays.toString(_quantiteArray);
    }

    public class NotesHoldeur extends RecyclerView.ViewHolder {

        private TextView holdeurLibelle;
        private TextView holdeurMontant;
        private TextView holdeurQuantite;

        public NotesHoldeur (@NonNull View itemView) {
            super(itemView);
            holdeurLibelle = itemView.findViewById(R.id.libelleNote);
            holdeurMontant = itemView.findViewById(R.id.montantNote);
            holdeurQuantite = itemView.findViewById(R.id.quantiteNote);
        }
    }

    @NonNull
    @Override
    public NotesAdapteur.NotesHoldeur onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(_context);
        View view = inflater.inflate(R.layout.liste_notes_row, parent, false);
        return new NotesHoldeur(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotesAdapteur.NotesHoldeur holder, final int position) {
        holder.holdeurLibelle.setText(_libelleArray[position]);
        holder.holdeurMontant.setText(_montantArray[position].toString() + "€");
        holder.holdeurQuantite.setText(_quantiteArray[position].toString());
        holder.holdeurQuantite.addTextChangedListener(new TextWatcher() {
            @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
                public void afterTextChanged(Editable e) {
                    Integer nouvelleQuantite = 0;
                    try {
                        nouvelleQuantite = Integer.parseInt(e.toString());
                        _quantiteArray[position] = nouvelleQuantite;
                    } catch (NumberFormatException except) {
                        System.out.println("Incorrect value !");
                    }
            }
        });
    }

    @Override
    public int getItemCount() {
        return _libelleArray.length;
    }
}
