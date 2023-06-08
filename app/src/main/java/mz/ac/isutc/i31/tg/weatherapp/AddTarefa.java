package mz.ac.isutc.i31.tg.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.cert.Extension;

import mz.ac.isutc.i31.tg.weatherapp.databinding.ActivityAddTarefaBinding;

public class AddTarefa extends AppCompatActivity {
    ActivityAddTarefaBinding tarefaBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        tarefaBinding = ActivityAddTarefaBinding.inflate(getLayoutInflater());
        setContentView(tarefaBinding.getRoot());

        Database database = new Database(AddTarefa.this);
        tarefaBinding.save.setOnClickListener(new View.OnClickListener() {
            Tarefa tarefa;
            @Override
            public void onClick(View view) {
                try{
                    tarefa = new Tarefa(tarefaBinding.tarefa.getText().toString(),tarefaBinding.temp.getText().toString());
                    database.addTarefa(tarefa);
                    Toast.makeText(AddTarefa.this, "Sucesso!", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(AddTarefa.this, "Failed!", Toast.LENGTH_SHORT).show();
                    tarefa = new Tarefa("error","error");
                    database.addTarefa(tarefa);

                }

            }
        });

        tarefaBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTarefa.this, ListTarefas.class);
                startActivity(intent);
            }
        });

    }

}