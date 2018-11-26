package com.spystream.app.repository;

//konfiguracja przechowywania backendu

import java.util.Optional;

public interface IConfigurationStorageBackend {

    //otrzymanie konfiguracji
    Optional<ConfigurationEntry> getConfiguration(String environment,String name);

    //przechowywanie konfiguracji

    void storeConfiguration(String environment,String name,ConfigurationEntry config);

}
