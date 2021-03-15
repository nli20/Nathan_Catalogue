package com.zjanvier.cataloguedefilms.Data;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zjanvier.cataloguedefilms.Activities.DetailsActivity;
import com.zjanvier.cataloguedefilms.Model.Poke;
import com.zjanvier.cataloguedefilms.R;

import java.util.List;

public class PokeRecyclerViewAdapter extends RecyclerView.Adapter<PokeRecyclerViewAdapter.ViewHolder>
{
    private Context context;
    private List<Poke> pokeList;
    public PokeRecyclerViewAdapter(Context context, List<Poke> pokes) {
        this.context=context;
        pokeList=pokes;
    }

    @NonNull
    @Override
    public PokeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ///return null;
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull PokeRecyclerViewAdapter.ViewHolder holder, int position) {
        Poke poke = pokeList.get(position);
        String spriteLink =  poke.getSprite();

        holder.name.setText(poke.getName());
        holder.type.setText(poke.getType());

        Picasso.get()
                .load(spriteLink)
                .fit()
                .placeholder(android.R.drawable.ic_btn_speak_now)
                .into(holder.sprite);
        //holder.number.setText(poke.getNumber());
    }

    @Override
    public int getItemCount() {
        return pokeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        ImageView sprite;
        TextView type;
        TextView number;
        TextView ability;
        TextView height;
        TextView weight;

        public ViewHolder(@NonNull View itemView, final Context ctx)
        {
            super(itemView);
            context=ctx;

            name = itemView.findViewById(R.id.pokeNameID);
            sprite = itemView.findViewById(R.id.pokeImageID);
            type = itemView.findViewById(R.id.pokeTypeID);
            number = itemView.findViewById(R.id.pokeNumberID);
            ability= itemView.findViewById(R.id.pokeAbilityID);
            height=itemView.findViewById(R.id.pokeHeightID);
            weight=itemView.findViewById(R.id.pokeWeightID);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Poke poke = pokeList.get(getAdapterPosition());

                    Intent intent = new Intent(context, DetailsActivity.class);

                    intent.putExtra("poke", poke);
                    ctx.startActivity(intent);



                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
