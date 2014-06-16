package br.com.activity;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;

import br.com.android.R;
import br.com.business.Cliente;
import br.com.conexao.WSConnection;
import br.com.dao.ClienteDao;
import br.com.util.Imagem;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class FormularioCliente extends Activity{
	
	private ProgressDialog progress;
	private Toast alerta;
	private static final int TIRAR_FOTO = 101;
	private String caminho;
	private String ErroMensagem="";
	private int Id;
	private String foto="";
	private Imagem imagem=new Imagem();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.formulario_cliente);
	bindControls();
	
	Intent i=getIntent();
	if(i.getIntExtra("Id",0)> 0){
		Id=i.getIntExtra("Id",0);
		carregaItem(i);
	}
	
	btnGravar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Cliente cliente=new Cliente();
			if(Id >0){
				cliente.setId(Id);
			}
			cliente.setNome(edtNome.getEditableText().toString());
			cliente.setEndereco(edtEndereco.getEditableText().toString());
			cliente.setTelefone(edtTelefone.getEditableText().toString());
			if(foto.equalsIgnoreCase("")== false){
			cliente.setFoto(foto);
			}
			Gravar(cliente);
			
		}
	});
	
	imgFoto.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			String arquivo = Environment.getExternalStorageDirectory()// caminho
					+ "/" + System.currentTimeMillis() + ".jpg";
			caminho =arquivo;

			File file = new File(arquivo);
			Uri outputFileUri = Uri.fromFile(file);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			startActivityForResult(intent, TIRAR_FOTO);
		}
	});
	
	}
	
		private void bindControls(){
		edtNome=(EditText)findViewById(R.id.edtNome_form_cliente);
		edtEndereco=(EditText)findViewById(R.id.edtEndereco_form_cliente);
		edtTelefone=(EditText)findViewById(R.id.edtTelefone_form_cliente);
		btnGravar=(Button)findViewById(R.id.btn_form_gravar);
		imgFoto=(ImageView)findViewById(R.id.img_form_cliente);
	}
	
	
	private void Gravar(final Cliente cliente){
	new AsyncTask<Void,Void, Void>(){

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			alerta=Toast.makeText(FormularioCliente.this, "Cliente gravado com sucesso!", Toast.LENGTH_LONG);
			progress=new ProgressDialog(FormularioCliente.this);
			progress.setMessage("Gravando Cliente...");
			progress.show();
			super.onPreExecute();
			
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				
				
				WSConnection connection=new WSConnection();
				connection.post(new ClienteDao().Inserir(cliente));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				ErroMensagem=e.getMessage().toString();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progress.dismiss();
			if(ErroMensagem.equals("")){
				alerta.show();
				FormularioCliente.this.finish();
			}else{
				alerta.setText("Erro: "+ ErroMensagem);
			}
			
		}
		
	}.execute();
		
		
		
	}
	
	private void carregaItem(Intent intent){
		edtNome.setText(intent.getStringExtra("Nome"));
		edtEndereco.setText(intent.getStringExtra("Endereco"));
		edtTelefone.setText(intent.getStringExtra("Telefone"));	
		try {
			imagem.carregaFoto(imgFoto, intent.getByteArrayExtra("Foto"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TIRAR_FOTO) {
			if (resultCode == RESULT_OK) {
				try {
				File Foto= new File(caminho);
				InputStream is=new FileInputStream(Foto);
				this.foto  =imagem.FiletoStringBase64(is);
										
					imagem.carregaImagem(imgFoto, caminho);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		}
	}
	
	
	
	
 
	
	EditText edtNome;
	EditText edtTelefone;
	EditText edtEndereco;
	ImageView imgFoto;
	Button btnGravar;
}
