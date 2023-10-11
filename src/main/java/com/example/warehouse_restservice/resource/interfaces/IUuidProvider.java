package com.example.warehouse_restservice.resource.interfaces;

import java.util.UUID;

public interface IUuidProvider {
    UUID uuid();

    UUID genterateUuid(int testId);
}
