package com.grupo.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.grupo.myapplication.R;

import java.sql.RowId;
import java.util.ArrayList;

public class Principal extends AppCompatActivity {

    private PersonaDAO PERSONADAO;

    private EditText TXTNOMBRE;

    private EditText TXTAPELLIDO;

    private EditText TXTDNI;

    private Button BTNAGREGAR;

    private Button BTNLISTAR;

    public ArrayList<Persona> lista = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TXTNOMBRE = findViewById(R.id.TXTNOMBRE);
        TXTAPELLIDO = findViewById(R.id.TXTAPELLIDO);
        TXTDNI = findViewById(R.id.TXTDNI);
        BTNAGREGAR = findViewById(R.id.BTNAGREGAR);
        BTNLISTAR = findViewById(R.id.BTNLISTAR);
        TXTNOMBRE = findViewById(R.id.TXTNOMBRE);
        PERSONADAO = new PersonaDAO(this);

        Validar();
    }

    public void Grabar() {

        String nombre = TXTNOMBRE.getText().toString(); String apellido = TXTAPELLIDO .getText().toString();
        String dni = TXTDNI.getText().toString();

        if (nombre.isEmpty() | apellido.isEmpty() || dni.isEmpty()) {

            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

            Persona persona = new Persona();
            persona.setNombre(nombre);
            persona.setApellido(apellido);
            persona.setDni(dni);

            long RowId = PERSONADAO.Insertar(persona);

            if (RowId != -1) {
                Toast.makeText(this, "Persona agregada correctamente", Toast.LENGTH_SHORT).show();
                TXTNOMBRE.getEditableText().clear();
                TXTAPELLIDO.getEditableText().clear();
                TXTDNI.getEditableText().clear();
            } else {
                Toast.makeText(this, "Error at agregar persona", Toast.LENGTH_SHORT).show();
            }

    }

    public void CargarTabla() {
        PersonaDAO PERSONADAO = new PersonaDAO(this);
        lista = (ArrayList<Persona>)PERSONADAO.ListadoPersonas();

        if(lista.isEmpty()) {
            Toast.makeText(this, "No hay registros para mostrar", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder sb = new StringBuilder();
        for(Persona persona : lista) {
            sb.append("Nombre: ").append(persona.getNombre()).append("\n")
            .append("Apellido: ").append(persona.getApellido()).append("\n")
                    .append("DNI: ").append(persona.getDni()).append("\n\n");
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registros");
        builder.setMessage(sb.toString());
        builder.setPositiveButton("Aceptar", null);
        builder.show();
    }

    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.BTNAGREGAR:
                Grabar();
                break;
                case R.id.BTNLISTAR:
                    CargarTabla();
                    break;
        }
    }

    private void Validar() {
        Drawable customBorder = getResources().getDrawable(R.drawable.border);
        Drawable customBorderRed = getResources().getDrawable(R.drawable.border_red);

        TXTNOMBRE.setBackground(customBorder);
        TXTAPELLIDO.setBackground(customBorder);

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+")){
                    return null;
                }
                return  "";
            }
        };

        TXTNOMBRE.setFilters(new InputFilter[]{filter});
        TXTAPELLIDO.setFilters(new InputFilter[]{filter});

        BTNAGREGAR.setEnabled(false);

        TXTDNI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             if (s.length() == 8) {
                 TXTDNI.setBackground(customBorder);
                 TXTDNI.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                 TXTDNI.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_check, 0);
                 TXTDNI.setError(null);
             } else if (s.length() == 0) {
                 TXTDNI.setBackground(customBorder);
                 TXTDNI.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                 TXTDNI.setError(null);
                 BTNAGREGAR.setEnabled(false);
             } else {
                 TXTDNI.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                 TXTDNI.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_error, 0);

                 TXTDNI.setError("Debe de contener 8 digitos");

                 TXTDNI.setBackground(customBorderRed);
                 BTNAGREGAR.setEnabled(false);
             }
             if (s.length() == 8 && TXTAPELLIDO.length() >= 3 && TXTNOMBRE.length() >= 3) {
                 BTNAGREGAR.setEnabled(true);
             } else {
                 BTNAGREGAR.setEnabled(false);
             }
                if (s.length() > 8) {
                    String text = s.toString();
                    TXTDNI.setText(text.substring(0, 8));
                    TXTDNI.setSelection(8);
                }
            }



            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TXTNOMBRE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                   if(s.length() >= 3) {
                       TXTNOMBRE.setBackground(customBorder);
                       TXTNOMBRE.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                       TXTNOMBRE.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_ckeck, 0);

                       TXTNOMBRE.setError(null);
                   }else if (s.length() == 0) {
                       TXTNOMBRE.setBackground(customBorder);
                       TXTNOMBRE.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                       TXTNOMBRE.setError(null);
                       BTNAGREGAR.setEnabled(false);
                   } else {
                       TXTNOMBRE.setError("Debe contener como minimo 3 caracteres");

                       TXTNOMBRE.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                       TXTNOMBRE.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_error, 0);

                       TXTNOMBRE.setBackground(customBorderRed);
                   }

                   if(s.length() >= 3 && TXTAPELLIDO.length() >= 3 && TXTDNI.length() == 8) {
                       BTNAGREGAR.setEnabled(true);
                   }else {
                       BTNAGREGAR.setEnabled(false);
                   }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TXTAPELLIDO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(s.length() >= 3) {
                   TXTAPELLIDO.setBackground(customBorder);
                   TXTAPELLIDO.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                   TXTAPELLIDO.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_check, 0);
                   TXTAPELLIDO.setError(null);
               } else if(s.length() == 0){
                   TXTAPELLIDO.setBackground(customBorder);
                   TXTAPELLIDO.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                   TXTAPELLIDO.setError(null);
                   BTNAGREGAR.setEnabled(false);
                } else {
                   TXTAPELLIDO.setError("Debe contener minimo 3 caracteres");
                   TXTAPELLIDO.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                   TXTAPELLIDO.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_error,0);
               }

               if (TXTNOMBRE.length() >= 3 && TXTDNI.length() == 8) {
                   BTNAGREGAR.setEnabled(true);
               } else {
                   BTNAGREGAR.setEnabled(false);
               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}