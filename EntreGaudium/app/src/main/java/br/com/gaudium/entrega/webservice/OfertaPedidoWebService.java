package br.com.gaudium.entrega.webservice;

import android.content.Context;
import android.os.AsyncTask;
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
import java.net.URL;
import java.nio.charset.Charset;

import br.com.gaudium.entrega.model.PedidoJsonObj;

public class OfertaPedidoWebService extends AsyncTask {
    String url = "https://dbgapi-desenv.taximachine.com.br/ps/ofertaPedido.php";

    public void obterPedido(Context ctx, OfertaPedidoCallback callback) throws IOException {
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
