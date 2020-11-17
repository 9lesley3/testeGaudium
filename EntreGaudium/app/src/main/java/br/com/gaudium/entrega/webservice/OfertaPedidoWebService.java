package br.com.gaudium.entrega.webservice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import br.com.gaudium.entrega.model.PedidoJsonObj;

public class OfertaPedidoWebService extends AsyncTask {
    String url = "https://dbgapi-desenv.taximachine.com.br/ps/ofertaPedido.php";

    public void obterPedido(Context ctx, OfertaPedidoCallback callback) throws IOException {
        //TODO: Trocar pedido hard-coded por chamada ao backend
//		PedidoJsonObj.PedidoObj p = new PedidoJsonObj.PedidoObj();
//		p.setLat_coleta(-22.910112);
//		p.setLng_coleta(-43.173913);
//		p.setEndereco_coleta("Av. Graça Aranha, 26 - Centro");
//
//		PedidoJsonObj.EntregaObj[] destino = new PedidoJsonObj.EntregaObj[3];
//		destino[0] = new PedidoJsonObj.EntregaObj("#1", -22.910852, -43.185296);
//		destino[1] = new PedidoJsonObj.EntregaObj("#2", -22.903223, -43.103135);
//		destino[2] = new PedidoJsonObj.EntregaObj("#3", -22.955188, -43.193763);
//		p.setEntrega(destino);



		/* TODO: Trocar código abaixo por uma requisição GET para a URL do método
		WebRequest wr = new WebRequest("GET");
		wr.setUrl(URL);
		wr.setOnCompleteWebRequest(response -> {
			PedidoJsonObj responseObj = (PedidoJsonObj) WebRequest.parse(response);
			if(responseObj.isSuccess()){
				callback.run(responseObj.getResponse());
			} else{
				Toast.makeText(ctx, "Erro ao obter pedido", Toast.LENGTH_SHORT).show();
			}
		});

		 */

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = new URL(url).openStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String jsonText = readAll(rd);
                    Gson gson = new GsonBuilder().create();
                    Type accountsList = new TypeToken<PedidoJsonObj>() {
                    }.getType();
                    PedidoJsonObj responseObj = gson.fromJson(jsonText, accountsList);
                    if (responseObj.isSuccess()) {
                        callback.run(responseObj.getResponse());
                    } else {
                        Toast.makeText(ctx, "Erro ao obter pedido", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


//		callback.run(p);
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int status;
        while ((status = rd.read()) != -1) {
            stringBuilder.append((char) status);
        }
        return stringBuilder.toString();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    public interface OfertaPedidoCallback {
        void run(PedidoJsonObj.PedidoObj ofertaPedido);
    }
}
