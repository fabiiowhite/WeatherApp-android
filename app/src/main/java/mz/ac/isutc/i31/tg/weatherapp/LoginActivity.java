package mz.ac.isutc.i31.tg.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    AppCompatButton okBtn;
    EditText tf1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        okBtn = findViewById(R.id.button7);
        tf1 = findViewById(R.id.editTextTextPersonName);
        tf1.setBackgroundResource(R.drawable.custom_edittext);
        EditText editText = findViewById(R.id.editTextTextPersonName);
        editText.setHintTextColor(Color.GRAY);


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database database = new Database(LoginActivity.this);
                String name = "";
                try {
                    name = tf1.getText().toString();
                    Toast.makeText(LoginActivity.this, "Thanks!", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(LoginActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    name = "undefined";
                }
                database.addUser(name);
                Intent intent = new Intent(LoginActivity.this,InitializationActivity.class);
                startActivity(intent);

            }
        });


    }
}