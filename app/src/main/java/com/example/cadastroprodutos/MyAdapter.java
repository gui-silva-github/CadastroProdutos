package com.example.cadastroprodutos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cadastroprodutos.Produto;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Produto> {

    private final Context context;
    private final List<Produto> produtos;

    public MyAdapter(Context context, List<Produto> produtos) {
        super(context, R.layout.item_layout, produtos);
        this.context = context;
        this.produtos = produtos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_layout, parent, false);
        }

        Produto produto = produtos.get(position);

        TextView nomeTextView = convertView.findViewById(R.id.tvNome);
        TextView precoTextView = convertView.findViewById(R.id.tvPreco);

        nomeTextView.setText(produto.getNome());
        precoTextView.setText(String.valueOf(produto.getPreco()));

        return convertView;
    }

    public void removeItem(int position) {
        produtos.remove(position);
        notifyDataSetChanged(); // Notificar que os dados mudaram
    }
}
