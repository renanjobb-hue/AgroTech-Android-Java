package com.agrotech.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ColheitasFragment extends Fragment {

    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_colheitas, container, false);

        final Spinner spVol = root.findViewById(R.id.spVol);
        List<String> nomes = new ArrayList<>();
        for (Repo.Voluntario v : Repo.voluntarios) nomes.add(v.nome);
        if (nomes.isEmpty()) nomes.add("Não informado");
        spVol.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, nomes));

        final EditText etProduto = root.findViewById(R.id.etProduto);
        final EditText etPeso = root.findViewById(R.id.etPeso);

        root.findViewById(R.id.btnRegistrar).setOnClickListener(v -> {
            String produto = etProduto.getText().toString().trim();
            double peso = Util.parsePeso(etPeso.getText().toString());
            if (produto.isEmpty()) {
                etProduto.setError("Informe o produto");
                return;
            }
            if (peso <= 0) {
                etPeso.setError("Informe um peso válido");
                return;
            }
            Repo.colheitas.add(0, new Repo.Colheita(Repo.hoje(), produto, peso, (String) spVol.getSelectedItem()));
            etProduto.setText("");
            etPeso.setText("");
            Toast.makeText(requireContext(), "Colheita registrada!", Toast.LENGTH_SHORT).show();
            refresh();
        });

        refresh();
        return root;
    }

    private void refresh() {
        LinearLayout ll = root.findViewById(R.id.llHistorico);
        ll.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        int n = Math.min(10, Repo.colheitas.size());
        for (int i = 0; i < n; i++) {
            Repo.Colheita c = Repo.colheitas.get(i);
            View row = inflater.inflate(R.layout.item_colheita, ll, false);
            ((TextView) row.findViewById(R.id.tvProduto)).setText(c.produto);
            ((TextView) row.findViewById(R.id.tvInfo)).setText(c.data + " · " + c.voluntario);
            ((TextView) row.findViewById(R.id.tvPeso)).setText(Repo.kg(c.peso) + " Kg");
            ll.addView(row);
        }
    }
}
