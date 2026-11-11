package com.agrotech.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/** Dados em memória (protótipo). Em uma versão futura pode ser trocado por SQLite/Room ou API. */
public class Repo {

    public static class Voluntario {
        public String nome, especialidade, disponibilidade;
        public int horas;

        public Voluntario(String nome, String especialidade, int horas, String disponibilidade) {
            this.nome = nome;
            this.especialidade = especialidade;
            this.horas = horas;
            this.disponibilidade = disponibilidade;
        }

        public String iniciais() {
            String[] p = nome.trim().split("\\s+");
            String s = p[0].substring(0, 1);
            if (p.length > 1) s += p[p.length - 1].substring(0, 1);
            return s.toUpperCase();
        }
    }

    public static class Atividade {
        public static final int PENDENTE = 0, EXECUCAO = 1, CONCLUIDO = 2;
        public String titulo, categoria, responsavel;
        public int status;

        public Atividade(String titulo, String categoria, String responsavel, int status) {
            this.titulo = titulo;
            this.categoria = categoria;
            this.responsavel = responsavel;
            this.status = status;
        }
    }

    public static class Colheita {
        public String data, produto, voluntario;
        public double peso;

        public Colheita(String data, String produto, double peso, String voluntario) {
            this.data = data;
            this.produto = produto;
            this.peso = peso;
            this.voluntario = voluntario;
        }
    }

    public static final List<Voluntario> voluntarios = new ArrayList<>();
    public static final List<Atividade> atividades = new ArrayList<>();
    public static final List<Colheita> colheitas = new ArrayList<>(); // mais recente primeiro
    public static double metaKg = 168;

    static {
        voluntarios.add(new Voluntario("Andrei Ruola", "Irrigação", 42, "Seg a Sex"));
        voluntarios.add(new Voluntario("Cainan Prada", "Plantio", 28, "Ter e Qui"));
        voluntarios.add(new Voluntario("Douglas Leal", "Manejo", 35, "Seg, Qua e Sex"));

        atividades.add(new Atividade("Regar Setor B (Hortaliças)", "Irrigação", "Andrei Ruola", Atividade.PENDENTE));
        atividades.add(new Atividade("Remover ervas daninhas (Canteiro 4)", "Manejo", "Douglas Leal", Atividade.PENDENTE));
        atividades.add(new Atividade("Semeadura de Alface Americana", "Plantio", "Andrei Ruola", Atividade.EXECUCAO));
        atividades.add(new Atividade("Aplicação de adubo orgânico", "Manejo", "Cainan Prada", Atividade.CONCLUIDO));
        atividades.add(new Atividade("Troca de mangueira Setor A", "Irrigação", "Douglas Leal", Atividade.CONCLUIDO));

        colheitas.add(new Colheita("04/05/2026", "Tomate Cereja", 12.5, "Pablo Antonio"));
        colheitas.add(new Colheita("03/05/2026", "Alface Lisa", 8.0, "Luiz Guilherme"));
        colheitas.add(new Colheita("02/05/2026", "Cenoura", 15.2, "Vinícius Abreu"));
        colheitas.add(new Colheita("01/05/2026", "Beterraba", 10.0, "Isaque Moraes"));
        colheitas.add(new Colheita("30/04/2026", "Couve Manteiga", 5.5, "Renan Job"));
        colheitas.add(new Colheita("29/04/2026", "Tomate Cereja", 58.0, "Andrei Ruola"));
        colheitas.add(new Colheita("28/04/2026", "Alface Lisa", 33.3, "Cainan Prada"));
    }

    public static double totalColhido() {
        double t = 0;
        for (Colheita c : colheitas) t += c.peso;
        return t;
    }

    public static String hoje() {
        return new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR")).format(new Date());
    }

    public static String kg(double v) {
        return String.format(Locale.US, "%.1f", v);
    }
}
