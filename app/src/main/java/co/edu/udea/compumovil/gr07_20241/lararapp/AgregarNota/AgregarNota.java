package co.edu.udea.compumovil.gr07_20241.lararapp.AgregarNota;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import co.edu.udea.compumovil.gr07_20241.lararapp.Objetos.Nota;
import co.edu.udea.compumovil.gr07_20241.lararapp.R;

public class AgregarNota extends AppCompatActivity {

    TextView Uid_Usuario, Correo_Usuario, Fecha_Hora_Actual, Fecha, Estado;
    EditText Titulo, Descripcion;
    Button Btn_Calendario, Btn_Agregar_Nota;

    DatabaseReference BD_Firebase;

    int dia,mes,anio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);

        inicializarVariables();
        obtenerDatos();
        obtenerFechaHoraActual();

        Btn_Agregar_Nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNota();
            }
        });

        Btn_Calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario = Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio = calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AgregarNota.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String diaFormateado, mesFormateado;

                        if(dayOfMonth < 10){
                            diaFormateado = "0"+String.valueOf(dayOfMonth);
                        } else {
                            diaFormateado = String.valueOf(dayOfMonth);
                        }

                        int Mes = month+1;

                        if(Mes < 10){
                            mesFormateado = "0"+String.valueOf(month);
                        } else {
                            mesFormateado = String.valueOf(month);
                        }


                        Fecha.setText(diaFormateado + "/" + mesFormateado + "/" + year);
                    }
                }
                ,anio,mes,dia);
                datePickerDialog.show();
            }
        });

    }

    private void inicializarVariables(){
        Uid_Usuario = findViewById(R.id.Uid_Usuario);
        Correo_Usuario = findViewById(R.id.Correo_Usuario);
        Fecha_Hora_Actual = findViewById(R.id.Fecha_Hora_Actual);
        Fecha = findViewById(R.id.Fecha);
        Estado = findViewById(R.id.Estado);
        Titulo = findViewById(R.id.Titulo);
        Descripcion = findViewById(R.id.Descripcion);
        Btn_Calendario = findViewById(R.id.Btn_Calendario);
        Btn_Agregar_Nota = findViewById(R.id.Agregar_Nota_BD);

        BD_Firebase = FirebaseDatabase.getInstance().getReference();
    }

    private void obtenerDatos(){
        String uid_recuperado = getIntent().getStringExtra("Uid");
        String correo_recuperado = getIntent().getStringExtra("correo_usuario");

        Uid_Usuario.setText(uid_recuperado);
        Correo_Usuario.setText(correo_recuperado);
    }

    private void obtenerFechaHoraActual(){
        String fechaHoraActual = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a"
                , Locale.getDefault()).format(System.currentTimeMillis());

        Fecha_Hora_Actual.setText(fechaHoraActual);
    }

    private void agregarNota(){
        String uid_usuario = Uid_Usuario.getText().toString();
        String correo_usuario = Correo_Usuario.getText().toString();
        String fecha_hora_actual = Fecha_Hora_Actual.getText().toString();
        String titulo = Titulo.getText().toString();
        String descripcion = Descripcion.getText().toString();
        String fecha = Fecha.getText().toString();
        String estado = Estado.getText().toString();

        if (!uid_usuario.isEmpty() && !correo_usuario.isEmpty() && !fecha_hora_actual.isEmpty() &&
                !titulo.isEmpty() && !descripcion.isEmpty() && !fecha.isEmpty() && !estado.isEmpty()) {

            Nota nota = new Nota(correo_usuario + "/" + fecha_hora_actual,
                    uid_usuario,
                    correo_usuario,
                    fecha_hora_actual,
                    titulo,
                    descripcion,
                    fecha,
                    estado);

            String Nota_usuario = BD_Firebase.push().getKey();
            String Nombre_Bd = "Notas_Publicadas";
            BD_Firebase.child(Nombre_Bd).child(Nota_usuario).setValue(nota);

            Toast.makeText(this, "Se ha agregado la nota exitosamente", Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else {
            Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.manu_agenda, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}