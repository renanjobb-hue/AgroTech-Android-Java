package com.agrotech.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.Normalizer;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    // Protótipo: credenciais fixas (sem servidor).
    private static final String USUARIO_COORDENADOR = "cesar augusto";
    private static final String USUARIO_COLHEDOR = "colhedor";
    private static final String SENHA = "agrotech";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final AutoCompleteTextView etUsuario = findViewById(R.id.etUsuario);
        final EditText etSenha = findViewById(R.id.etSenha);
        Button btnEntrar = findViewById(R.id.btnEntrar);

        // Sugestão aparece assim que o campo é tocado, para completar com um toque.
        etUsuario.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, new String[]{"César Augusto"}));
        etUsuario.setThreshold(0);
        etUsuario.setOnClickListener(v -> etUsuario.showDropDown());
        etUsuario.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) etUsuario.post(etUsuario::showDropDown);
        });

        btnEntrar.setOnClickListener(v -> {
            String usuario = normalizar(etUsuario.getText().toString());
            String senha = normalizar(etSenha.getText().toString()).replace(" ", "");

            boolean usuarioOk = usuario.equals(USUARIO_COORDENADOR) || usuario.equals(USUARIO_COLHEDOR);
            if (!usuarioOk) {
                etUsuario.setError("Usuário não encontrado");
                return;
            }
            if (!senha.equals(SENHA)) {
                etSenha.setError("Senha incorreta");
                return;
            }
            Intent intent = new Intent(this, MainActivity.class);
            if (usuario.equals(USUARIO_COLHEDOR)) intent.putExtra(MainActivity.EXTRA_PERFIL, "colhedor");
            startActivity(intent);
            finish();
        });

        findViewById(R.id.tvEsqueceu).setOnClickListener(v ->
                Toast.makeText(this, "Recuperação de senha disponível nas próximas versões.", Toast.LENGTH_SHORT).show());
    }

    /** Minúsculas, sem acentos e sem espaços repetidos: "CÉSAR  Augusto" -> "cesar augusto". */
    private static String normalizar(String s) {
        String semAcento = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return semAcento.toLowerCase(Locale.ROOT).trim().replaceAll("\\s+", " ");
    }
}
