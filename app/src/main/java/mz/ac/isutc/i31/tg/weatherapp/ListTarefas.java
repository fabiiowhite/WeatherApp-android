package mz.ac.isutc.i31.tg.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import mz.ac.isutc.i31.tg.weatherapp.databinding.ActivityListTarefasBinding;

public class ListTarefas extends AppCompatActivity {
    ActivityListTarefasBinding listTarefasBinding;
    AppCompatButton backBtn2, addBtn;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        listTarefasBinding = ActivityListTarefasBinding.inflate(getLayoutInflater());
        setContentView(listTarefasBinding.getRoot());

        backBtn2 = findViewById(R.id.backBtn2);
        addBtn = findViewById(R.id.addBtn);

        Database database = new Database(ListTarefas.this);
        try {
            listView = listTarefasBinding.list;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, database.listarTarefas());
            listView.setAdapter(adapter);
        }catch(Exception e){
            Toast.makeText(this, "Sem tarefas!", Toast.LENGTH_SHORT).show();
        }


        backBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListTarefas.this,InitializationActivity.class);
                startActivity(intent);

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListTarefas.this,AddTarefa.class);
                startActivity(intent);

            }
        });

        listTarefasBinding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Object item = parent.getItemAtPosition(i);
                String data = item.toString();
                String[] datinha = data.split(":");
                String[] graze = datinha[1].split("°");
                Intent intent = new Intent(ListTarefas.this, OnClickActivity.class);
                intent.putExtra("descrip",datinha[0]);
                intent.putExtra("grauz",graze[0]);
                int id = database.getIdTarefa(datinha[0]);
                String ide = String.valueOf(id);
                intent.putExtra("id",ide);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Clicked item: " + id, Toast.LENGTH_SHORT).show();
            }
        });

    }
}