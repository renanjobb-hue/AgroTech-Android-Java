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

public class AtividadesFragment extends Fragment {

    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_atividades, container, false);

        final Spinner spCat = root.findViewById(R.id.spCategoria);
        spCat.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Irrigação", "Plantio", "Manejo", "Colheita", "Adubação"}));

        final Spinner spResp = root.findViewById(R.id.spResp);
        List<String> nomes = new ArrayList<>();
        for (Repo.Voluntario v : Repo.voluntarios) nomes.add(v.nome);
        if (nomes.isEmpty()) nomes.add("Sem responsável");
        spResp.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, nomes));

        final EditText etTarefa = root.findViewById(R.id.etTarefa);
        root.findViewById(R.id.btnCriar).setOnClickListener(v -> {
            String t = etTarefa.getText().toString().trim();
            if (t.isEmpty()) {
                etTarefa.setError("Descreva a atividade");
                return;
            }
            String resp = (String) spResp.getSelectedItem();
            Repo.atividades.add(new Repo.Atividade(t, (String) spCat.getSelectedItem(), resp, Repo.Atividade.PENDENTE));
            Toast.makeText(requireContext(), "Atividade criada. " + resp + " foi notificado(a).", Toast.LENGTH_SHORT).show();
            etTarefa.setText("");
            refresh();
        });

        refresh();
        return root;
    }

    private void refresh() {
        fill((LinearLayout) root.findViewById(R.id.llPend), Repo.Atividade.PENDENTE, R.id.tvPend, "PENDENTE");
        fill((LinearLayout) root.findViewById(R.id.llExec), Repo.Atividade.EXECUCAO, R.id.tvExec, "EM EXECUÇÃO");
        fill((LinearLayout) root.findViewById(R.id.llConc), Repo.Atividade.CONCLUIDO, R.id.tvConc, "CONCLUÍDO");
    }

    private void fill(LinearLayout ll, int status, int titleId, String titulo) {
        ll.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        int count = 0;
        for (final Repo.Atividade a : Repo.atividades) {
            if (a.status != status) continue;
            count++;
            View card = inflater.inflate(R.layout.item_atividade, ll, false);
            ((TextView) card.findViewById(R.id.tvTag)).setText(a.categoria);
            ((TextView) card.findViewById(R.id.tvTitulo)).setText(a.titulo);
            ((TextView) card.findViewById(R.id.tvResp)).setText(
                    a.responsavel == null || a.responsavel.isEmpty() ? "Sem responsável" : "👤 " + a.responsavel);
            TextView acao = card.findViewById(R.id.tvAcao);
            acao.setText(status == Repo.Atividade.PENDENTE ? "Iniciar ▶"
                    : status == Repo.Atividade.EXECUCAO ? "Concluir ✔" : "Reabrir ↺");
            acao.setOnClickListener(v -> {
                a.status = (a.status + 1) % 3;
                refresh();
            });
            ll.addView(card);
        }
        ((TextView) root.findViewById(titleId)).setText(titulo + "  (" + count + ")");
    }
}
