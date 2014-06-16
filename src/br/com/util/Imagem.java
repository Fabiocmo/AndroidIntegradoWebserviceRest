package br.com.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;



public class Imagem {

	
	 public String FiletoStringBase64(InputStream fis) throws IOException  
	    {  
		 String    base64String="";
			 byte[] ba= InputStreamtobyte(fis);
		
		 base64String = Base64.encodeToString(ba, Base64.DEFAULT );
			return base64String;  
	    } 
	 
	 public String encodeFileToBase64Binary(File file)
				throws IOException {
	 InputStream is=new FileInputStream(file);
	 byte[] ba= InputStreamtobyte(is);
	 String encodedString = Base64.encodeToString(ba, Base64.DEFAULT );	
	 
			return encodedString;
		}
	 
	 public byte[] loadFile(File file) throws IOException {
		    InputStream is = new FileInputStream(file);
	 
		    byte[] ba= InputStreamtobyte(is);
		    return ba;
		    
		    
		}
	 
	 public void carregaFoto(ImageView imagem,String Foto) throws JSONException {
		 
				byte[] buffer =Stringtobyte(Foto);
				InputStream stream = new ByteArrayInputStream(buffer);
				Bitmap bm = BitmapFactory.decodeStream(stream);
				bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
				imagem.setImageBitmap(bm);
			
	 
}
	 
	 public void carregaFoto(ImageView imagem,byte[] Foto) throws JSONException {
		 
		 if(Foto.length>0){
			InputStream stream = new ByteArrayInputStream(Foto);
			Bitmap bm = BitmapFactory.decodeStream(stream);
			bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
			imagem.setImageBitmap(bm);
		 }

}
	 public byte [] Stringtobyte(String file){
		 return Base64.decode(file, 0);
	 }
	 
	 public byte[] InputStreamtobyte(InputStream is) throws IOException{
		 ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		 int nRead;
		 byte[] data = new byte[4096];

		 while ((nRead = is.read(data, 0, data.length)) != -1) {
		   buffer.write(data, 0, nRead);
		 }

		 buffer.flush();

		 return buffer.toByteArray();
		 
	 }
	 
	 public void carregaImagem(ImageView image,String Caminho) {
			
			if (Caminho!= null) {
				Bitmap bm = BitmapFactory.decodeFile(Caminho);
				bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
				image.setImageBitmap(bm);
			}

		}
}
