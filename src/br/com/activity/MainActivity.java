package br.com.activity;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.adapter.AdapterListCliente;
import br.com.android.R;
import br.com.business.Cliente;
import br.com.conexao.WSConnection;
import br.com.dao.ClienteDao;
import br.com.item.ItemListCliente;
import br.com.util.Imagem;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	private static ProgressDialog progress;
	private static Toast alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
    
    }  


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_context, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	
        	Intent intent=new  Intent(MainActivity.this,FormularioCliente.class);
        	startActivity(new Intent(intent));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
		private  AdapterListCliente adapter;
    	private ItemListCliente item;
    	private ArrayList<ItemListCliente> itens;
    	private String erroMensagem="";
    	private Context context;
    	private Imagem image=new Imagem();
        public PlaceholderFragment() {
        	
        	
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            lstCliente =(ListView)rootView.findViewById(R.id.lstcliente);

            lstCliente.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> adapter,
						View view, int position, long id) {
					// TODO Auto-generated method stub
					registerForContextMenu(lstCliente);
					item=(ItemListCliente) adapter.getItemAtPosition(position);
					return false;
				}
			});
            
            context=rootView.getContext();
            return rootView;
        }
        
    
        
        private void carregaListView(final Context rootView){
        	
        	
        	new AsyncTask<Void, Void, Void>(){
        		@Override
        		protected void onPreExecute() {
        			// TODO Auto-generated method stub
        			alerta=Toast.makeText(rootView, "", Toast.LENGTH_LONG);
        			progress=new ProgressDialog(rootView);
        			progress.setMessage("Carregando Lista....");
        			progress.show();
        			super.onPreExecute();
        		}
        		
        		
        		@Override
    			protected Void doInBackground(Void... params) {
    				// TODO Auto-generated method stub
        			
        			WSConnection connection= new WSConnection();
                	Cliente cli = new Cliente();
                	itens=new ArrayList<ItemListCliente>();
        			try {
        		String[] json=connection.post(new ClienteDao().Pesquisar(cli));
        		JSONArray jsonlist=new JSONArray(json[1]);
        		JSONObject jo;
        		for(int i=0;i<jsonlist.length();i++){
        			jo=new JSONObject(jsonlist.get(i).toString());
        			item=new ItemListCliente(jo.getInt("id"),jo.getString("nome") , jo.getString("endereco"), jo.getString("telefone"),image.Stringtobyte(jo.getString("foto")));
        		itens.add(item);
        		}
        		
        		adapter=new AdapterListCliente(rootView, itens);
        
        		
        			} catch (JSONException e) {
        				// TODO Auto-generated catch block
        				erroMensagem=e.getMessage().toString();
        			}
    				return null;
    			}
        		
        		@Override
        		protected void onPostExecute(Void result) {
        			// TODO Auto-generated method stub
        			try {
        				progress.dismiss();
        			
        	        	lstCliente.setAdapter(adapter);
            			
            			
            			if(erroMensagem.equals("")){
            			//	alerta.show();        				
            			}else{
            				//alerta.setText("Erro: " +erroMensagem);
            				//alerta.show();
            			}
						
					} catch (Exception e) {
						erroMensagem=e.getMessage().toString();
					}
        			      			
        		}
        	        			
        	}.execute();
        	
        }
        
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
        		ContextMenuInfo menuInfo) {
        	// TODO Auto-generated method stub
        	
          	
        	menu.add(0, 0, 0, "Alterar");
        	menu.add(0, 1, 0, "Deletar");
        	super.onCreateContextMenu(menu, v, menuInfo);
        	
      
        	
        }
        
        public boolean onContextItemSelected(MenuItem item) {
        	if(item.getItemId() == 0){
        		
        		Intent intent=new Intent(context,FormularioCliente.class);
        		intent.putExtra("Id", this.item.getId());
        		intent.putExtra("Nome", this.item.getNome());
        		intent.putExtra("Endereco",this.item.getEndereco());
        		intent.putExtra("Telefone", this.item.getTelefone());
        		intent.putExtra("Foto",this.item.getFoto());
        		
        		startActivity(intent);
        		
        	}
        	
        	if(item.getItemId() ==  1){
        		
        	}
        	return super.onContextItemSelected(item);
        
        }
        ListView lstCliente;
        	@Override
        	public void onResume() {
        		// TODO Auto-generated method stub
        		carregaListView(context);
        		super.onResume();
        	}
        	
   
        
        };
        
        
    }
    


