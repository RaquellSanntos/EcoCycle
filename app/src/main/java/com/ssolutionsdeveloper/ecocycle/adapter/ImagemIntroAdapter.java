package com.ssolutionsdeveloper.ecocycle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssolutionsdeveloper.ecocycle.R;
import com.ssolutionsdeveloper.ecocycle.model.Introducoes;

import java.util.List;

public class ImagemIntroAdapter extends RecyclerView.Adapter<ImagemIntroAdapter.MyViewHolder> {
    private List<Introducoes>introducoes;
    public ImagemIntroAdapter(List<Introducoes> introducoesList) {
        this.introducoes=introducoesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.introducao_detalhe,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Introducoes introducao = introducoes.get(position);
        holder.imagemIntro.setImageResource(introducao.getImagem());
    }

    @Override
    public int getItemCount() {
        return introducoes.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imagemIntro;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemIntro = itemView.findViewById(R.id.imgIntro);
        }
    }
}
