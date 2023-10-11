package com.example.warehouse_restservice.resource.entities.helpers;
import com.example.warehouse_restservice.resource.interfaces.IUuidProvider;

import java.util.UUID;

//Production class to create random UUID that we do not need to know
public class RandomUuidProvider implements IUuidProvider {
    @Override
    public UUID uuid() {
        return UUID.randomUUID();
    }

    @Override
    public UUID genterateUuid(int testId) {
        return null;
    }
}
