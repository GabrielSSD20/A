package br.edu.fatecpg.service;

import br.edu.fatecpg.model.RespostaCarrinhos;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsomeAPI {

    public RespostaCarrinhos buscarCarrinhos()
            throws IOException, InterruptedException {

        String url = "https://dummyjson.com/carts?limit=0";

        HttpClient client = HttpClient.newHttpClient();

        URI uri = URI.create(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new IOException("Erro ao acessar a API. Status: "
                    + response.statusCode());
        }

        String json = response.body();

        Gson gson = new Gson();

        RespostaCarrinhos resposta =
                gson.fromJson(json, RespostaCarrinhos.class);

        return resposta;
    }
}