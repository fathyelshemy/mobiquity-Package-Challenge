package com.mobiquity.services;

import com.mobiquity.exception.APIException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileOperations {

    public List<String> readRowLines(String filePath) throws APIException {
        List<String> lines = new ArrayList<>();
        try {
            lines=Files.lines(Path.of(filePath)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new APIException("Can't find file");
        }
        return lines;
    }
}
