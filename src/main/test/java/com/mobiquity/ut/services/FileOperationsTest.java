package com.mobiquity.ut.services;

import com.mobiquity.exception.APIException;
import com.mobiquity.services.FileOperations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


class FileOperationsTest {

    @Test
    void readRowLines_FileExists() throws APIException {
        FileOperations fileOperations= new FileOperations();
        List<String>lines= fileOperations.readRowLines("src/main/test/resources/example_input");
        Assertions.assertEquals(4,lines.size());
        Assertions.assertEquals("8 : (1,15.3,€34)",lines.get(1));
    }

    @Test
    void readRowLines_FileNotExists() {
        FileOperations fileOperations= new FileOperations();
        Assertions.assertThrows(APIException.class,()-> fileOperations.readRowLines("src/main/test/resources/dummy_path"));
    }
}