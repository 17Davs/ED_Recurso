/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collections.implementations;


import api.Localidade;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author David Santos
 */
public class Mapa<T> extends Network<T> {

    public Mapa() {
    }

    public Mapa(int capacidade) {
        super(capacidade);
    }

    public T[] getVertexes() {
        T[] temp = (T[]) new Object[numVertices];
        int i = 0;
        for (i = 0; i < numVertices; i++) {
            temp[i] = vertices[i];
        }
        return temp;
    }

    public void exportToJSON(String filePath) {
        JSONObject mapaJSON = new JSONObject();
        mapaJSON.put("NumeroVertices", numVertices);

        JSONArray localidadesJSON = new JSONArray();
        for (int i = 0; i < numVertices; i++) {
            JSONObject localizacaoJSON = new JSONObject();
            localizacaoJSON.put("Nome", ((Localidade) vertices[i]).getNome());
            localidadesJSON.add(localizacaoJSON);
        }
        mapaJSON.put("Localidades", localidadesJSON);

        JSONArray arestasJSON = new JSONArray();
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (isAdjacent(i, j)) {
                    JSONObject arestaJSON = new JSONObject();
                    arestaJSON.put("Origem", i);
                    arestaJSON.put("Destino", j);
                    arestaJSON.put("Peso", adjMatrix[i][j]);
                    arestasJSON.add(arestaJSON);
                }
            }
        }
        mapaJSON.put("Arestas", arestasJSON);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(mapaJSON.toJSONString());
            file.close();
            System.out.println("Mapa exportado com sucesso para: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Mapa<Localidade> importJSON(String filePath) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) jsonParser.parse(reader);
            } catch (ParseException ex) {

            }

            long numVertices = (Long) jsonObject.get("NumeroVertices");
            Mapa<Localidade> mapa = new Mapa<>((int) numVertices);

            JSONArray LocalidadesArray = (JSONArray) jsonObject.get("Localidades");
            for (int i = 0; i < LocalidadesArray.size(); i++) {
                JSONObject localidadeJson = (JSONObject) LocalidadesArray.get(i);
                String nome = (String) localidadeJson.get("Nome");
                Localidade localidade = new Localidade(nome);
                mapa.addVertex(localidade);

            }

            JSONArray arestasArray = (JSONArray) jsonObject.get("Arestas");
            for (int i = 0; i < arestasArray.size(); i++) {
                JSONObject aresta = (JSONObject) arestasArray.get(i);
                long origem = (long) aresta.get("Origem");
                long destino = (long) aresta.get("Destino");
                long peso = (long) aresta.get("Peso");

                mapa.addEdge((int) origem, (int) destino, peso);
            }

            return mapa;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Mapa<>();
    }

    public void showMapaFromJSON(String filePath) {

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray localidadesArray = (JSONArray) jsonObject.get("Localidades");
            JSONArray arestasArray = (JSONArray) jsonObject.get("Arestas");

            System.out.println("Vertices:");
            for (int i = 0; i < localidadesArray.size(); i++) {
                JSONObject localidade = (JSONObject) localidadesArray.get(i);
                System.out.println(localidade.get("Nome"));
            }

            System.out.println("\nArestas:");
            for (int i = 0; i < arestasArray.size(); i++) {
                JSONObject aresta = (JSONObject) arestasArray.get(i);
                long origem = (long) aresta.get("Origem");
                long destino = (long) aresta.get("Destino");
                long peso = (long) aresta.get("Peso");

                String nomeOrigem = ((JSONObject) localidadesArray.get((int) origem)).get("Nome").toString();
                String nomeDestino = ((JSONObject) localidadesArray.get((int) destino)).get("Nome").toString();

                System.out.println(nomeOrigem + " -- " + nomeDestino + " (Peso: " + peso + ")");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

}


