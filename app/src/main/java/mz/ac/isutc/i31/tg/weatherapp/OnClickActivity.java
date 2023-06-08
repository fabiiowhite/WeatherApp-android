package mz.ac.isutc.i31.tg.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import mz.ac.isutc.i31.tg.weatherapp.databinding.ActivityOnClickBinding;

public class OnClickActivity extends AppCompatActivity {

    ActivityOnClickBinding onClickBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        onClickBinding = ActivityOnClickBinding.inflate(getLayoutInflater());
        setContentView(onClickBinding.getRoot());

        Database database = new Database(OnClickActivity.this);
        Intent intent = getIntent();
        String tarefaa = intent.getStringExtra("descrip");
        String temp = intent.getStringExtra("grauz");
        String idi = intent.getStringExtra("id");

        onClickBinding.tarefa.setText(tarefaa);
        onClickBinding.temp.setText(temp);

        onClickBinding.editar.setOnClickListener(new View.OnClickListener() {
            Tarefa tarefa;
            @Override
            public void onClick(View view) {
                try {
                    tarefa = new Tarefa(onClickBinding.tarefa.getText().toString(), onClickBinding.temp.getText().toString());
                    int ID = Integer.parseInt(idi);
                    database.atualizarTarefa(ID,tarefa);
                    onClickBinding.tarefa.setText("");
                    onClickBinding.temp.setText("");
                    Toast.makeText(OnClickActivity.this, "Atualizada!", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(OnClickActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        onClickBinding.apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //int ID = Integer.parseInt(id);
                    database.deletarTarefa(tarefaa);
                    Toast.makeText(OnClickActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                    onClickBinding.tarefa.setText("");
                    onClickBinding.temp.setText("");
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(OnClickActivity.this, "Errr", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}