package co.edu.udea.compumovil.gr07_20241.lararapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.edu.udea.compumovil.gr07_20241.lararapp.AgregarNota.AgregarNota;
import co.edu.udea.compumovil.gr07_20241.lararapp.ListarNotas.ListarNota;
import co.edu.udea.compumovil.gr07_20241.lararapp.NotasArchivadas.NotasArchivadas;
import co.edu.udea.compumovil.gr07_20241.lararapp.Perfil.Perfil;

public class MenuPrincipal extends AppCompatActivity {

    Button  ListarNotas, AgregarNotas, cerrarSesion;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    TextView uidPrincipal, nombresPrincipal, correoPrincipal;
    ProgressBar progressBarDatos;

    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        uidPrincipal = findViewById(R.id.Uid_Principal);
        nombresPrincipal = findViewById(R.id.NombrePrincipal);
        correoPrincipal = findViewById(R.id.CorreoPrincipal);
        progressBarDatos = findViewById(R.id.progressBarDatos);

        users = FirebaseDatabase.getInstance().getReference("usuarios");
        AgregarNotas = findViewById(R.id.AgregarNotas);
        ListarNotas = findViewById(R.id.ListarNotas);
        cerrarSesion = findViewById(R.id.CerrarSesion);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        AgregarNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uid_usuario = uidPrincipal.getText().toString();
                String correo_Usuario = correoPrincipal.getText().toString();

                Intent intent = new Intent(MenuPrincipal.this, AgregarNota.class);
                intent.putExtra("Uid",uid_usuario);
                intent.putExtra("correo_usuario",correo_Usuario);

                startActivity(intent);
                Toast.makeText(MenuPrincipal.this,"Agregar Nota", Toast.LENGTH_SHORT).show();
            }
        });
        ListarNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( MenuPrincipal. this, ListarNota.class));
                Toast.makeText(MenuPrincipal.this,"Listar Notas", Toast.LENGTH_SHORT).show();
            }
        });
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salirAplicacion();
            }
        });
    }

    @Override
    protected void onStart() {
        comprobarInicioSesion();
        super.onStart();
    }

    private void comprobarInicioSesion(){
        if(user!=null){
            cargaDeDatos();
        } else {
            startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
        }
    }

    private void cargaDeDatos(){
        users.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    progressBarDatos.setVisibility(View.GONE);
                    nombresPrincipal.setVisibility(View.VISIBLE);
                    correoPrincipal.setVisibility(View.VISIBLE);
                    uidPrincipal.setVisibility(View.VISIBLE);

                    String uid = ""+snapshot.child("uid").getValue();
                    String nombres = ""+snapshot.child("nombre").getValue();
                    String correo = ""+snapshot.child("correo").getValue();

                    uidPrincipal.setText(uid);
                    nombresPrincipal.setText(nombres);
                    correoPrincipal.setText(correo);

                    AgregarNotas.setEnabled(true);
                    ListarNotas. setEnabled(true);

                    cerrarSesion.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void salirAplicacion() {
        firebaseAuth.signOut();
        startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
        Toast.makeText(this,"Cerraste sesion existosamente.",Toast.LENGTH_SHORT).show();
    }
}