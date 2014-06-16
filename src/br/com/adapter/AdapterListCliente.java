package br.com.adapter;

import java.util.ArrayList;

import org.json.JSONException;

import br.com.android.R;
import br.com.item.ItemListCliente;
import br.com.util.Imagem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterListCliente extends BaseAdapter {
	
	ArrayList<ItemListCliente> itens;
	LayoutInflater inflater;
	Imagem image=new Imagem();

	public AdapterListCliente(Context context,ArrayList<ItemListCliente> itens){
		this.itens=itens;
		inflater=LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itens.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itens.get( position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.item_lstcliente, null);
		ItemListCliente item=itens.get(position);
		((TextView)view.findViewById(R.id.txt_lst_nmcliente)).setText(item.getNome());
		((TextView)view.findViewById(R.id.txt_lst_telcliente)).setText(item.getTelefone());
		
		if(item.getFoto().length > 0){
			
			try {
				ImageView Foto=(ImageView) view.findViewById(R.id.img_lst_cliente);
				image.carregaFoto(Foto, item.getFoto());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
		Toast.makeText(view.getContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
			}
		}
		return view;
	}

}
