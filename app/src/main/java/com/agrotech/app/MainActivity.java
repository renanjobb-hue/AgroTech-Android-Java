package com.agrotech.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_PERFIL = "perfil";

    private DrawerLayout drawer;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final boolean colhedor = "colhedor".equals(getIntent().getStringExtra(EXTRA_PERFIL));
        TextView tvUser = navView.getHeaderView(0).findViewById(R.id.tvHeaderUser);
        tvUser.setText(colhedor ? "Colhedor" : "César Augusto · Coordenador Agrícola");

        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_sair) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            }
            abrir(id);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        // Botão voltar fecha o menu lateral se ele estiver aberto.
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });

        if (savedInstanceState == null) {
            abrir(colhedor ? R.id.nav_colhedor : R.id.nav_dashboard);
        }
    }

    private void abrir(int id) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentFor(id))
                .commit();
        navView.setCheckedItem(id);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(navView.getMenu().findItem(id).getTitle());
        }
    }

    @NonNull
    private Fragment fragmentFor(int id) {
        if (id == R.id.nav_voluntarios) return new VoluntariosFragment();
        if (id == R.id.nav_atividades) return new AtividadesFragment();
        if (id == R.id.nav_colheitas) return new ColheitasFragment();
        if (id == R.id.nav_relatorios) return new RelatoriosFragment();
        if (id == R.id.nav_colhedor) return new ColhedorFragment();
        return new DashboardFragment();
    }
}
