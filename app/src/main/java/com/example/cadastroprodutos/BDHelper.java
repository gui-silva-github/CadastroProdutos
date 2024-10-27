package com.example.cadastroprodutos;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
public class BDHelper extends SQLiteOpenHelper{

    private static final String NOME_BANCO = "ProdutoBD.db";
    private static final int VERSAO_BANCO = 1;
    private static final String NOME_TABELA = "produtos";
    public static final String COLUNA_ID = "_id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_PRECO = "preco";

    public BDHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String criarTabela = "CREATE TABLE " + NOME_TABELA + " (" +
                COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_NOME + " TEXT, " +
                COLUNA_PRECO + " DECIMAL(10,2)" + ")";
        db.execSQL(criarTabela);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOME_TABELA);
        onCreate(db);
    }

    // CREATE
    public boolean inserirProduto(String nome, double preco){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNA_NOME, nome);
        contentValues.put(COLUNA_PRECO, preco);

        long resultado = db.insert(NOME_TABELA, null, contentValues);
        return resultado != -1;
    }

    // READ
    public Cursor obterTodosProdutos(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COLUNA_ID + ", " + COLUNA_NOME + ", " + COLUNA_PRECO + " FROM " + NOME_TABELA, null);
    }

    // UPDATE
    public boolean atualizarProduto(int id, String nome, double preco){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNA_NOME, nome);
        contentValues.put(COLUNA_PRECO, preco);

        int resultado = db.update(NOME_TABELA, contentValues, COLUNA_ID + " = ?", new String[]{String.valueOf(id)});
        return resultado > 0;
    }

    // DELETE
    public boolean deletarProduto(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int resultado = db.delete(NOME_TABELA, COLUNA_ID + " = ?", new String[]{String.valueOf(id)});
        return resultado > 0;
    }

}
