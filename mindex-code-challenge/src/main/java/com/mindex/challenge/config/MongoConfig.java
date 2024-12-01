package com.mindex.challenge.config;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.lang.NonNull;

import java.net.InetSocketAddress;

@EnableMongoRepositories(basePackageClasses = EmployeeRepository.class)
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private MongoServer server;

    @Override
    @NonNull
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    @Bean
    @NonNull
    public MongoClient mongoClient() {
        server = new MongoServer(new MemoryBackend());
        InetSocketAddress serverAddress = server.bind();
        String mongoConnectionString = String.format("mongodb://%s:%d", serverAddress.getHostName(), serverAddress.getPort());
        return MongoClients.create(mongoConnectionString);
    }

    @Override
    public void destroy() {
        if (server != null) {
            server.shutdown();
        }
    }
}