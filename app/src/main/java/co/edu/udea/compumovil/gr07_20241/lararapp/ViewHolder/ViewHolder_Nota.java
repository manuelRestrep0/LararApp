package co.edu.udea.compumovil.gr07_20241.lararapp.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import co.edu.udea.compumovil.gr07_20241.lararapp.R;

public class ViewHolder_Nota extends RecyclerView.ViewHolder {

    View mView;

    private ViewHolder_Nota.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder_Nota.ClickListener clickListener){
        mClickListener = clickListener;
    }

    public ViewHolder_Nota(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v){
               mClickListener.onItemLongClick(v,getAdapterPosition());
               return false;
           }
        });
    }

    public void setearDatos(Context context, String id_nota, String uid_usuario, String correo_usuario,
                            String fecha_hora_registro, String titulo, String descripcion,
                            String fecha_nota,
                            String estado){

        TextView Id_nota_Item, Uid_Usuario_Item, Correo_usuario_Item,Fecha_hora_registro_Item,Titulo_Item,
                Descripcion_Item, Fecha_Item, Estado_Item;

        ImageView Tarea_Finalizada_Item, Tarea_No_Finalizada_Item;

        Id_nota_Item = mView.findViewById(R.id.Id_nota_Item);
        Uid_Usuario_Item = mView.findViewById(R.id.Uid_Usuario_Item);
        Correo_usuario_Item = mView.findViewById(R.id.Correo_usuario_Item);
        Fecha_hora_registro_Item = mView.findViewById(R.id.Fecha_hora_registro_Item);
        Titulo_Item = mView.findViewById(R.id.Titulo_Item);
        Descripcion_Item = mView.findViewById(R.id.Descripcion_Item);
        Fecha_Item = mView.findViewById(R.id.Fecha_Item);
        Estado_Item = mView.findViewById(R.id.Estado_Item);

        Tarea_Finalizada_Item = mView.findViewById(R.id.Tarea_Finalizada_Item);
        Tarea_No_Finalizada_Item = mView.findViewById(R.id.Tarea_No_Finalizada_Item);

        Id_nota_Item.setText(id_nota);
        Uid_Usuario_Item.setText(uid_usuario);
        Correo_usuario_Item.setText(correo_usuario);
        Fecha_hora_registro_Item.setText(fecha_hora_registro);
        Titulo_Item.setText(titulo);
        Descripcion_Item.setText(descripcion);
        Fecha_Item.setText(fecha_nota);
        Estado_Item.setText(estado);

        if (estado.equals("Finalizado")){
            Tarea_Finalizada_Item.setVisibility(View.VISIBLE);
            Tarea_No_Finalizada_Item.setVisibility(View.GONE);
        }else {
            Tarea_Finalizada_Item.setVisibility(View.GONE);
            Tarea_No_Finalizada_Item.setVisibility(View.VISIBLE);
        }

    }
}
