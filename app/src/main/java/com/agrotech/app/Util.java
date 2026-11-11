package com.agrotech.app;

import android.graphics.Color;
import android.widget.TextView;

public class Util {

    /** Aplica texto e cor de status em um TextView. */
    public static void styleStatus(TextView tv, int status) {
        switch (status) {
            case Repo.Atividade.CONCLUIDO:
                tv.setText("● Concluído");
                tv.setTextColor(Color.parseColor("#2E7D32"));
                break;
            case Repo.Atividade.EXECUCAO:
                tv.setText("● Em andamento");
                tv.setTextColor(Color.parseColor("#F59E0B"));
                break;
            default:
                tv.setText("● Pendente");
                tv.setTextColor(Color.parseColor("#6B7C93"));
        }
    }

    /** Converte "12,5" ou "12.5" em double. Retorna -1 se inválido. */
    public static double parsePeso(String s) {
        try {
            return Double.parseDouble(s.trim().replace(',', '.'));
        } catch (Exception e) {
            return -1;
        }
    }
}
