package mz.ac.isutc.i31.tg.weatherapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myBa.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela Utilizador
    private static final String TABLE_UTILIZADOR = "utilizador";
    private static final String COLUMN_UTILIZADOR_ID = "id";
    private static final String COLUMN_UTILIZADOR_NOME = "nome";


    // Tabela Tarefas
    private static final String TABLE_TAREFAS = "tarefas";
    private static final String COLUMN_TAREFA_ID = "_id";
    private static final String COLUMN_TAREFA_DESCRICAO = "descricao";
    private static final String COLUMN_TAREFA_TEMP = "temperatura";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUtilizadorTable = "CREATE TABLE " + TABLE_UTILIZADOR + "(" +
                COLUMN_UTILIZADOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_UTILIZADOR_NOME + " TEXT"+ ")";
        db.execSQL(createUtilizadorTable);

        String createTarefasTable = "CREATE TABLE " + TABLE_TAREFAS + "(" +
                COLUMN_TAREFA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TAREFA_DESCRICAO + " TEXT, " +
                COLUMN_TAREFA_TEMP + " TEXT" +
                ")";
        db.execSQL(createTarefasTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILIZADOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAREFAS);
        onCreate(db);
    }

    public boolean addUser(String name){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_UTILIZADOR_NOME,name);
        long insert = database.insert(TABLE_UTILIZADOR,null,values);
        if(insert == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean addTarefa(Tarefa tarefa){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TAREFA_DESCRICAO,tarefa.getDescricao());
        values.put(COLUMN_TAREFA_TEMP,tarefa.getTemperatura());

        long insert = sqLiteDatabase.insert(TABLE_TAREFAS,null,values);
        if (insert == -1) {
            return false;
        }else {
            return true;
        }
    }

    @SuppressLint("Range")
    public String exibirNomeUtilizador() {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_UTILIZADOR_NOME + " FROM " + TABLE_UTILIZADOR;
        Cursor cursor = db.rawQuery(selectQuery, null);
    String nomeUtilizador = "";
        if (cursor.moveToLast()) {
            nomeUtilizador = cursor.getString(cursor.getColumnIndex(COLUMN_UTILIZADOR_NOME));

        }

        cursor.close();
        db.close();
        return nomeUtilizador;
    }

    public boolean tabelaTarefasTemDadosPreenchidos() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_TAREFAS;
        Cursor cursor = db.rawQuery(query, null);

        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }

        return count > 0;
    }

    public ArrayList listarTarefas() {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_TAREFAS;
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<String> tarefasList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String descricao = cursor.getString(cursor.getColumnIndex(COLUMN_TAREFA_DESCRICAO));
                @SuppressLint("Range") String temperatura = cursor.getString(cursor.getColumnIndex(COLUMN_TAREFA_TEMP));
                String result = descricao + ": " + temperatura+"°C";
                tarefasList.add(result);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tarefasList;
    }
    @SuppressLint("Range")
    public int getIdTarefa(String descricao) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_TAREFA_ID + " FROM " + TABLE_TAREFAS + " WHERE " + COLUMN_TAREFA_DESCRICAO + " = ?";
        String[] selectionArgs = {descricao};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        int idTarefa = -1;
        if (cursor.moveToFirst()) {
            idTarefa = cursor.getInt(cursor.getColumnIndex(COLUMN_TAREFA_ID));
        }
        cursor.close();
        db.close();
        return idTarefa;
    }

    public boolean atualizarTarefa(int idTarefa, Tarefa tarefa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TAREFA_DESCRICAO, tarefa.getDescricao());
        values.put(COLUMN_TAREFA_TEMP, tarefa.getTemperatura());

        String[] whereArgs = {String.valueOf(idTarefa)};
        String clausa = COLUMN_TAREFA_ID+" = ?";
        Cursor cursor = db.rawQuery("select * from tarefas where _id = ?",whereArgs);
        if (cursor.getCount() > 0){

            long rowsAffected = db.update("tarefas", values, clausa, whereArgs);
            if (rowsAffected == -1) {
                db.close();
                return false;
            }else {
                db.close();
                return true;
            }
        }else {
            return false;
        }


    }
    public void deletarTarefa(String descricao) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] whereArgs = {descricao};
        Cursor cursor = db.rawQuery("select * from tarefas where descricao = ?",whereArgs);
        //String whereClause = COLUMN_TAREFA_ID + " = ?";

        db.delete("tarefas", "descricao=?", whereArgs);
        db.close();
    }




}

