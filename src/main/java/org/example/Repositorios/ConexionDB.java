package org.example.Repositorios;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.example.Utils.Campos;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ConexionDB {

    private static ConexionDB instance;
    private MongoClient mongoClient;
    private MongoDatabase database;

    private ConexionDB() {
        String uri = "mongodb+srv://root:root@clusterada.m5wzn.mongodb.net/?retryWrites=true&w=majority&appName=ClusterADA";

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new com.mongodb.ConnectionString(uri))
                .codecRegistry(pojoCodecRegistry)
                .build();

        mongoClient = MongoClients.create(settings);

        database = mongoClient.getDatabase(Campos.NOMBRE_DB);
    }


    public static ConexionDB getInstance() {
        if (instance == null) {
            instance = new ConexionDB();
        }
        return instance;
    }

    public MongoDatabase recuperarDatabase(){
        return database;
    }

    public void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
