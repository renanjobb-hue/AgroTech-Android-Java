package com.agrotech.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RelatoriosFragment extends Fragment {

    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_relatorios, container, false);

        // Rendimento dos últimos meses (dados de exemplo, em Kg)
        ((BarChartView) root.findViewById(R.id.barChart)).setData(
                new String[]{"Jan", "Fev", "Mar", "Abr", "Mai"},
                new float[]{450, 520, 480, 650, 700});

        final EditText etMeta = root.findViewById(R.id.etMeta);
        root.findViewById(R.id.btnMeta).setOnClickListener(v -> {
            double meta = Util.parsePeso(etMeta.getText().toString());
            if (meta <= 0) {
                etMeta.setError("Informe uma meta válida");
                return;
            }
            Repo.metaKg = meta;
            etMeta.setText("");
            Toast.makeText(requireContext(), "Meta atualizada!", Toast.LENGTH_SHORT).show();
            refreshMeta();
        });

        refreshMeta();
        refreshTopProdutos();
        return root;
    }

    private void refreshMeta() {
        double total = Repo.totalColhido();
        double pct = Repo.metaKg > 0 ? total / Repo.metaKg * 100 : 0;
        ((TextView) root.findViewById(R.id.tvPercent)).setText(Math.round(pct) + "%");
        ((ProgressBar) root.findViewById(R.id.pbMeta)).setProgress((int) Math.min(100, Math.round(pct)));
        String info = Repo.kg(total) + " Kg de " + Repo.kg(Repo.metaKg) + " Kg";
        if (pct >= 100) info += "  ·  Meta superada! 🎉";
        else info += "  ·  Faltam " + Repo.kg(Repo.metaKg - total) + " Kg";
        ((TextView) root.findViewById(R.id.tvMetaInfo)).setText(info);
    }

    private void refreshTopProdutos() {
        // Agrupa pela primeira palavra do produto (ex.: "Tomate Cereja" -> "Tomate")
        Map<String, Double> soma = new LinkedHashMap<>();
        for (Repo.Colheita c : Repo.colheitas) {
            String chave = c.produto.trim().split("\\s+")[0];
            Double atual = soma.get(chave);
            soma.put(chave, (atual == null ? 0 : atual) + c.peso);
        }
        List<Map.Entry<String, Double>> itens = new ArrayList<>(soma.entrySet());
        Collections.sort(itens, (a, b) -> Double.compare(b.getValue(), a.getValue()));

        double total = Repo.totalColhido();
        int n = Math.min(5, itens.size());
        String[] labels = new String[n];
        float[] valores = new float[n];
        for (int i = 0; i < n; i++) {
            float v = itens.get(i).getValue().floatValue();
            labels[i] = itens.get(i).getKey() + " " + Math.round(total > 0 ? v / total * 100 : 0) + "%";
            valores[i] = v;
        }
        ((PieChartView) root.findViewById(R.id.pieChart)).setData(labels, valores);
    }
}
