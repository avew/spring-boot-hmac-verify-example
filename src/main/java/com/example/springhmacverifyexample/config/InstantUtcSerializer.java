package com.example.springhmacverifyexample.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class InstantUtcSerializer extends JsonSerializer<Instant> {

    public InstantUtcSerializer() {
    }

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                .withZone(ZoneOffset.UTC);
        String str = dateTimeFormatter.format(value);

        gen.writeString(str);
    }

}
