package com.example.cadastroprodutos;

import static com.example.cadastroprodutos.BDHelper.COLUNA_ID;
import static com.example.cadastroprodutos.BDHelper.COLUNA_NOME;
import static com.example.cadastroprodutos.BDHelper.COLUNA_PRECO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText nome, preco;
    Button adicionar, listagem;

    BDHelper bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bdHelper = new BDHelper(this);

        nome = findViewById(R.id.nome);
        preco = findViewById(R.id.preco);

        adicionar = findViewById(R.id.adicionar);

        listagem = findViewById(R.id.listagem);

        adicionar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String nomeValor = nome.getText().toString();
                String precoValor = preco.getText().toString();

                if(!nomeValor.isEmpty() && !precoValor.isEmpty()){
                    try{

                        double valor = Double.parseDouble(precoValor);
                        boolean inserido = bdHelper.inserirProduto(nomeValor, valor);

                        if(inserido){
                            Toast.makeText(MainActivity.this, "Produto adicionado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Erro ao adicionar produto", Toast.LENGTH_SHORT).show();
                        }

                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Preço inválido!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Erro ao inserir produto, tente novamente!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}