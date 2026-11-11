package com.agrotech.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {

    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        bind();
    }

    private void bind() {
        ((TextView) root.findViewById(R.id.tvTotalVol)).setText(String.valueOf(Repo.voluntarios.size()));
        ((TextView) root.findViewById(R.id.tvColheita)).setText(Repo.kg(Repo.totalColhido()));
        ((TextView) root.findViewById(R.id.tvAtividades)).setText(String.valueOf(Repo.atividades.size()));

        LinearLayout ll = root.findViewById(R.id.llRecentes);
        ll.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        int shown = 0;
        for (int i = Repo.atividades.size() - 1; i >= 0 && shown < 4; i--, shown++) {
            Repo.Atividade a = Repo.atividades.get(i);
            View row = inflater.inflate(R.layout.item_recente, ll, false);
            ((TextView) row.findViewById(R.id.tvTitulo)).setText(a.titulo);
            ((TextView) row.findViewById(R.id.tvSub)).setText(a.responsavel + " · " + a.categoria);
            Util.styleStatus((TextView) row.findViewById(R.id.tvStatus), a.status);
            ll.addView(row);
        }

        root.findViewById(R.id.tvSair).setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        });
    }
}
