package com.agrotech.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/** Tela do Colhedor: vê suas atividades e registra quanto colheu no dia. */
public class ColhedorFragment extends Fragment {

    private static final String NOME = "Colhedor (você)";
    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_colhedor, container, false);

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
            Repo.colheitas.add(0, new Repo.Colheita(Repo.hoje(), produto, peso, NOME));
            etProduto.setText("");
            etPeso.setText("");
            Toast.makeText(requireContext(), "Colheita registrada!", Toast.LENGTH_SHORT).show();
            refresh();
        });

        root.findViewById(R.id.tvSair).setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        });

        refresh();
        return root;
    }

    private void refresh() {
        LayoutInflater inflater = LayoutInflater.from(requireContext());

        LinearLayout minhas = root.findViewById(R.id.llMinhas);
        minhas.removeAllViews();
        for (Repo.Atividade a : Repo.atividades) {
            if (a.status == Repo.Atividade.CONCLUIDO) continue;
            View row = inflater.inflate(R.layout.item_recente, minhas, false);
            ((TextView) row.findViewById(R.id.tvTitulo)).setText(a.titulo);
            ((TextView) row.findViewById(R.id.tvSub)).setText(a.categoria);
            Util.styleStatus((TextView) row.findViewById(R.id.tvStatus), a.status);
            minhas.addView(row);
        }

        LinearLayout hist = root.findViewById(R.id.llMinhasColheitas);
        hist.removeAllViews();
        for (Repo.Colheita c : Repo.colheitas) {
            if (!NOME.equals(c.voluntario)) continue;
            View row = inflater.inflate(R.layout.item_colheita, hist, false);
            ((TextView) row.findViewById(R.id.tvProduto)).setText(c.produto);
            ((TextView) row.findViewById(R.id.tvInfo)).setText(c.data);
            ((TextView) row.findViewById(R.id.tvPeso)).setText(Repo.kg(c.peso) + " Kg");
            hist.addView(row);
        }
    }
}
