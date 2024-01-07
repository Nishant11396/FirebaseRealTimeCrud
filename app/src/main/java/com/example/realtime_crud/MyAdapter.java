    package com.example.realtime_crud;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import java.util.ArrayList;

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Myviewholder> {

        ArrayList<Model> ary;
        Context context;

        OnItemClickListener onItemClickListener;

    public MyAdapter(ArrayList<Model> demo,Context ctx){
    this.ary=demo;
    this.context=ctx;
    }

        @NonNull
        @Override
        public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_list_item,parent,false);
            return new Myviewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        holder.title.setText(ary.get(position).getTitle());
        holder.subtitle.setText(ary.get(position).getContent());

        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(ary.get(position)));

        }

        @Override
        public int getItemCount() {
            return ary.size();
        }

        public class Myviewholder extends RecyclerView.ViewHolder{

        TextView title,subtitle;



        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.list_item_title);
            subtitle=itemView.findViewById(R.id.list_item_title);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(Model model);
    }
    }
