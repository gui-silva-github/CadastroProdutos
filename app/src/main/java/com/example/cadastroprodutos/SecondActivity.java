package com.example.cadastroprodutos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private MyAdapter adapter;
    private List<Produto> itemList;

    ListView lista;

    Button cadastro, edicao;
    TextView nome, preco;

    String idValor;
    String nomeValor;
    String precoValor;

    BDHelper bdHelper;
    int produtoIdSelecionado = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        bdHelper = new BDHelper(this);
        itemList = new ArrayList<>();
        adapter = new MyAdapter(this, itemList);

        lista = findViewById(R.id.lista);
        lista.setAdapter(adapter);

        carregarProdutos(); // Carregar produtos para o itemList

        nome = findViewById(R.id.nome);
        preco = findViewById(R.id.preco);
        edicao = findViewById(R.id.editar);
        cadastro = findViewById(R.id.telaCadastro);

        lista.setOnItemClickListener((parent, view, position, id) -> {
            Produto produto = itemList.get(position);

            produtoIdSelecionado = produto.getId();
            idValor = String.valueOf(produto.getId());

            nome.setText(produto.getNome());
            nomeValor = produto.getNome();

            preco.setText(String.valueOf(produto.getPreco()));
            precoValor = String.valueOf(produto.getPreco());

            edicao.setVisibility(View.VISIBLE);
        });

        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float deltaX = e2.getX() - e1.getX();
                if (Math.abs(deltaX) > Math.abs(e2.getY() - e1.getY()) && deltaX < -100) { // Deslizar para a esquerda
                    int position = lista.pointToPosition((int) e1.getX(), (int) e1.getY());
                    if (position != ListView.INVALID_POSITION) {
                        Produto produto = itemList.get(position);
                        new AlertDialog.Builder(SecondActivity.this)
                                .setTitle("Confirmar Exclusão")
                                .setMessage("Tem certeza que deseja excluir este produto?")
                                .setPositiveButton("Sim", (dialog, which) -> {
                                    // Remover do banco de dados
                                    bdHelper.deletarProduto(produto.getId());
                                    // Remover do ListView
                                    adapter.removeItem(position);
                                    Toast.makeText(SecondActivity.this, "Produto excluído!", Toast.LENGTH_SHORT).show();
                                })
                                .setNegativeButton("Cancelar", (dialog, which) -> {
                                    // Fechar o diálogo sem fazer nada
                                    dialog.dismiss();
                                })
                                .show();
                    }
                    return true;
                }
                return false;
            }
        });

        lista.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

        edicao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtra("NOME", nomeValor);
                intent.putExtra("PRECO", precoValor);
                intent.putExtra("ID", idValor);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.second), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void telaCadastro(View v) {
        Intent it_telaCadastro = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(it_telaCadastro);
    }

    public void carregarProdutos() {
        // Obter todos os produtos do banco de dados
        itemList.clear(); // Limpar a lista antes de carregar novos dados
        Cursor cursor = bdHelper.obterTodosProdutos();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(BDHelper.COLUNA_ID));
                String nomeProduto = cursor.getString(cursor.getColumnIndexOrThrow(BDHelper.COLUNA_NOME));
                double precoProduto = cursor.getDouble(cursor.getColumnIndexOrThrow(BDHelper.COLUNA_PRECO));
                Produto produto = new Produto(id, nomeProduto, precoProduto);
                itemList.add(produto);
            }
            cursor.close(); // Fechar o cursor após uso
            adapter.notifyDataSetChanged(); // Notificar o adapter sobre os novos dados
        } else {
            Toast.makeText(this, "Erro ao carregar dados!", Toast.LENGTH_SHORT).show();
        }
    }
}
