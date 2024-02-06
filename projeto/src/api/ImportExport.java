/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import collections.implementations.ArrayUnorderedList;
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
public class ImportExport {

    private int numVertices;
    private int numArestas;

    public int getNumArestas() {
        return numArestas;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumArestas(int numArestas) {
        this.numArestas = numArestas;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public static void exportToJSON(String filePath, Mapa<Localidade> mapa) {
        double[][] adjMatrix = mapa.getAdjMatrix();
        int numVertices = mapa.getNumVertices();
        ArrayUnorderedList<Localidade> localidades = mapa.getVertexes();

        JSONObject mapaJSON = new JSONObject();
        mapaJSON.put("NumeroVertices", numVertices);

        JSONArray localidadesJSON = new JSONArray();
        for (int i = 0; i < numVertices; i++) {
            JSONObject localizacaoJSON = new JSONObject();
            localizacaoJSON.put("Nome", (localidades.get(i)).getNome());
            localidadesJSON.add(localizacaoJSON);
        }
        mapaJSON.put("Localidades", localidadesJSON);

        JSONArray arestasJSON = new JSONArray();
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (mapa.isAdjacent(i, j)) {
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
        }
        return new Mapa<>();
    }

    public static String showMapa(Mapa<Localidade> mapa) {
        int numVertices = mapa.getNumVertices();
        double[][] adjMatrix = mapa.getAdjMatrix();

        String string = "";
        string += "mapa:\n";

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j] != 0) {
                    string += "Localidade " + i + " está conectada a Localidade " + j + " com peso " + adjMatrix[i][j] + "\n";
                }
            }
        }
        return string;
    }

    public static String showMapa(String filePath) {
        Mapa<Localidade> mapa = importJSON(filePath);
        int numVertices = mapa.getNumVertices();
        double[][] adjMatrix = mapa.getAdjMatrix();

        String string = "";
        string += "Mapa do arquivo " + filePath + ":\n";

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j] != 0) {
                    string += "Localidade " + i + " está conectada a Localidade " + j + " com peso " + adjMatrix[i][j] + "\n";
                }
            }
        }
        return string;
    }
}
