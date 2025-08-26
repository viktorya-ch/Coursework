package org.skypro.Star.Bank.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.skypro.Star.Bank.dynamic.RuleCondition;

import java.util.List;

@Converter
public class RuleConditionConverter implements AttributeConverter<List<RuleCondition>, String> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<RuleCondition> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting rule to JSON", e);
        }
    }

    @Override
    public List<RuleCondition> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData,
                    new TypeReference<List<RuleCondition>>() {
                    });
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error parsing rule JSON", e);
        }
    }
}
