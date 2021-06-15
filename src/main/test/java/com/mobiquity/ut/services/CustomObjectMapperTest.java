package com.mobiquity.ut.services;

import com.mobiquity.dto.PackBack;
import com.mobiquity.services.CustomObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomObjectMapperTest {

    List<String> lines=new ArrayList<>();
    @BeforeEach
    void setUp() {
        lines.add("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
        lines.add("8 : (1,15.3,€34)");
    }

    @Test
    void mapToPackBacks_validData() {
        CustomObjectMapper objectMapper= new CustomObjectMapper();
        List<PackBack>packBacks=objectMapper.mapToPackBacks(lines);
        Assertions.assertEquals(2,packBacks.size());
        Assertions.assertEquals(81,packBacks.get(0).getMaxWeight());
        Assertions.assertEquals(53.38,packBacks.get(0).getItem().get(0).getWeight());
        Assertions.assertEquals(1,packBacks.get(0).getItem().get(0).getIndex());
        Assertions.assertEquals(45,packBacks.get(0).getItem().get(0).getValue());
        Assertions.assertEquals(6,packBacks.get(0).getItem().size());
    }

    @Test
    void mapToPackBacks_emptyLines(){
        lines.clear();
        CustomObjectMapper objectMapper= new CustomObjectMapper();
        Assertions.assertEquals(0,objectMapper.mapToPackBacks(lines).size());
    }
}