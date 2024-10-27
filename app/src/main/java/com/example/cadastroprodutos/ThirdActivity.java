package com.example.cadastroprodutos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity {

    EditText nome, preco;

    Button salvar;

    BDHelper bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);

        Intent intent = getIntent();

        bdHelper = new BDHelper(this);

        String nomeProduto = intent.getStringExtra("NOME");
        String precoProduto = intent.getStringExtra("PRECO");
        String idProduto = intent.getStringExtra("ID");

        double precoValor = Double.parseDouble(precoProduto);
        int idValor = Integer.parseInt(idProduto);

        nome = findViewById(R.id.nome);
        preco = findViewById(R.id.preco);

        salvar = findViewById(R.id.salvarAlteracoes);

        nome.setText(nomeProduto);
        preco.setText(precoProduto);

        salvar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean retorno = false;
                if(!nome.getText().toString().equals(nomeProduto) || Double.parseDouble(preco.getText().toString()) != precoValor) {
                    retorno = bdHelper.atualizarProduto(idValor, nome.getText().toString(), Double.parseDouble(preco.getText().toString()));

                    if (retorno) {
                        Toast.makeText(ThirdActivity.this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ThirdActivity.this, "Erro ao atualizar os dados!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ThirdActivity.this, "Modifique os valores!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.third), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void cadastro(View v){
        Intent it_telaCadastro = new Intent(ThirdActivity.this, MainActivity.class);
        startActivity(it_telaCadastro);
    }

    public void listagem(View v){
        Intent it_telaListagem = new Intent(ThirdActivity.this, SecondActivity.class);
        startActivity(it_telaListagem);
    }

}