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

public class VoluntariosFragment extends Fragment {

    private View root;
    private LinearLayout list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_voluntarios, container, false);
        list = root.findViewById(R.id.llVoluntarios);

        final Spinner spArea = root.findViewById(R.id.spArea);
        spArea.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Irrigação", "Plantio", "Manejo", "Colheita", "Adubação"}));

        final EditText etNome = root.findViewById(R.id.etNome);
        final EditText etDisp = root.findViewById(R.id.etDisp);

        root.findViewById(R.id.btnSalvar).setOnClickListener(v -> {
            String nome = etNome.getText().toString().trim();
            if (nome.isEmpty()) {
                etNome.setError("Informe o nome");
                return;
            }
            String disp = etDisp.getText().toString().trim();
            Repo.voluntarios.add(new Repo.Voluntario(nome, (String) spArea.getSelectedItem(), 0, disp));
            etNome.setText("");
            etDisp.setText("");
            Toast.makeText(requireContext(), "Voluntário cadastrado!", Toast.LENGTH_SHORT).show();
            refresh();
        });

        refresh();
        return root;
    }

    private void refresh() {
        list.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        for (final Repo.Voluntario vol : Repo.voluntarios) {
            View row = inflater.inflate(R.layout.item_voluntario, list, false);
            ((TextView) row.findViewById(R.id.tvAvatar)).setText(vol.iniciais());
            ((TextView) row.findViewById(R.id.tvNome)).setText(vol.nome);
            String sub = vol.especialidade;
            if (vol.disponibilidade != null && !vol.disponibilidade.isEmpty()) sub += " · " + vol.disponibilidade;
            ((TextView) row.findViewById(R.id.tvEsp)).setText(sub);
            ((TextView) row.findViewById(R.id.tvHoras)).setText(vol.horas + "h");
            row.findViewById(R.id.tvRemover).setOnClickListener(v -> {
                Repo.voluntarios.remove(vol);
                refresh();
            });
            list.addView(row);
        }
    }
}
