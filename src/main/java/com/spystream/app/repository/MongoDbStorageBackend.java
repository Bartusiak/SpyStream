package com.spystream.app.repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.security.auth.login.Configuration;
import java.io.IOException;
import java.util.Optional;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class MongoDbStorageBackend implements IConfigurationStorageBackend{

    //zawiera bazę klienta
    private MongoClient mongoClient;

    //zawiera bazę
    private MongoDatabase mongoDatabase;

    //zawiera kolekcje
    private MongoCollection<Document> collection;

    //konstruktor
    public MongoDbStorageBackend(String connectionString, String databaseName, String collectionName){
        //inicjalizacja
        this.mongoClient = (MongoClient) MongoClients.create(new ConnectionString(connectionString));
        this.mongoDatabase = mongoClient.getDatabase(databaseName);
        //ladowanie kolekcji
        this.collection=mongoDatabase.getCollection(collectionName);
    }
    //konfiguracja | @parametry nazwa_srodowiska nazwa_konfiguracji konfiguracja
    public Optional<ConfigurationEntry> getConfiguration(String environment, String name){
        try {
            //ladowanie dokumentu
            Document document = collection.find(and(eq("environment", environment),eq("name",name))).first();
            //mapowanie
            ConfigurationEntry configurationEntry = JacksonHelper.getObjectMapper().readValue(document.get("configurationBody").toString(),ConfigurationEntry.class);
            //odpowiedz
            return Optional.ofNullable(configurationEntry);
        } catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
    //przechowywanie konfiguracji | @parametry nazwa_srodowiska nazwa_konfiguracji konfiguracja
    public void storeConfiguration(String environment, String name, ConfigurationEntry config){
        try{
            //inicjalizacja dokumentu z meta informacji
            Document document = new Document("name",name).append("environment", environment);
            //konfiguracja mapy do dokumentu
            document = document.append("configurationBody", JacksonHelper.getObjectMapper().writeValueAsString(config));
            //przechowaj
            Document exists = collection.find(and(eq("environment",environment),eq("name",name))).first();
            if (exists!=null){
                collection.replaceOne(and(eq("environment",environment),eq("name",name)),document);
            }
            else {
                collection.insertOne(document);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
