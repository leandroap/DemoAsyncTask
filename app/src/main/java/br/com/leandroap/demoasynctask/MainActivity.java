package br.com.leandroap.demoasynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button btDownload;
    private ImageView ivImagem;
    private ProgressDialog progressDialog;
    private DownloadImagemTask downloadImagemTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btDownload = (Button)findViewById(R.id.btDownload);
        ivImagem = (ImageView)findViewById(R.id.ivImagem);

        btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(MainActivity.this,
                        getString(R.string.app_name),
                        getString(R.string.msg_baixando_imagem)
                        );

                String urlImagem = "http://blog.concretesolutions.com.br/wp-content/uploads/2015/04/Android1.png";

                downloadImagemTask = new DownloadImagemTask();
                downloadImagemTask.execute(urlImagem);
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (progressDialog != null
                && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private class DownloadImagemTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String... params){
            Bitmap bitmapImagem = null;

            try {
                bitmapImagem = downloadBitmap(params[0]);

            } catch (IOException ioe){
                ioe.printStackTrace();
            }

            return bitmapImagem;
        }

        @Override
        protected void onPostExecute(Bitmap imagem) {
            super.onPostExecute(imagem);

            if(imagem != null) {
                ivImagem.setImageBitmap(imagem);
            } else {

            }

            progressDialog.dismiss();
        }

        private Bitmap downloadBitmap(String url) throws IOException {
            URL urlImagem = null;
            Bitmap bitmap = null;

            try {
                urlImagem = new URL(url);
                HttpURLConnection conn = (HttpURLConnection)urlImagem.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

    }

}
















