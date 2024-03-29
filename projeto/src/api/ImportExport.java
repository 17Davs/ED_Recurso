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
 * Classe responsavel por todo o manuseo dos ficheiro JSON
 * @author David Santos e Rafael Coronel
 */
public class ImportExport {

    /**
     * Guarda um mapa num ficheiro JSON
     * @param filePath nome do mapa a ser guardado
     * @param mapa mapa a ser guardado
     */
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
                    arestaJSON.put("Peso", (int) adjMatrix[i][j]);
                    arestasJSON.add(arestaJSON);
                }
            }
        }
        mapaJSON.put("Arestas", arestasJSON);

        try ( FileWriter file = new FileWriter(filePath)) {
            file.write(mapaJSON.toJSONString());
            file.close();
            System.out.println("Mapa exportado com sucesso para: " + filePath);
        } catch (IOException e) {
        }
    }

    /**
     * Importa um mapa guardado num ficheiro JSON
     * @param filePath nome do mapa a ser importado
     * @return Retorna o mapa importado
     */
    public static Mapa<Localidade> importJSON(String filePath) {
        JSONParser jsonParser = new JSONParser();

        try ( FileReader reader = new FileReader(filePath)) {
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

    /**
     * Metodo responsavel por mostrar as caracteristicas do mapa atual
     * @param mapa mapa atual
     * @return retorna as caracteristicas do mapa
     */
    public static String showMapa(Mapa<Localidade> mapa) {
        int numVertices = mapa.getNumVertices();
        double[][] adjMatrix = mapa.getAdjMatrix();

        String string = "";
        string += "Mapa:\n";

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j] != 0) {
                    string += "Localidade " + mapa.getVertex(i) + " está conectada a Localidade " + mapa.getVertex(j) + " com peso " + adjMatrix[i][j] + "\n";
                }
            }
        }
        return string;
    }

    /**
     * Metodo que mostra um mapa a partir de um ficheiro JSON
     * @param filePath nome do mapaa ser mostrado
     * @return retorna o mapa
     */
    public static String showMapaFromJson(String filePath) {
        Mapa<Localidade> mapa = importJSON(filePath);
        int numVertices = mapa.getNumVertices();
        double[][] adjMatrix = mapa.getAdjMatrix();

        String string = "";
        string += "Mapa do arquivo " + filePath + ":\n";

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j] != 0) {
                    string += "Localidade " + mapa.getVertex(i) + " está conectada a Localidade " + mapa.getVertex(j) + " com peso " + adjMatrix[i][j] + "\n";
                }
            }
        }
        return string;
    }
}
