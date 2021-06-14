package com.mobiquity.packer;

import com.mobiquity.dto.Item;
import com.mobiquity.dto.PackBack;
import com.mobiquity.exception.APIException;
import com.mobiquity.services.CustomObjectMapper;
import com.mobiquity.services.FileOperations;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Packer {

  public static String pack(String filePath) throws APIException {
      FileOperations inputReader= new FileOperations();
      List<String> lines=inputReader.readRowLines(filePath);

      CustomObjectMapper objectMapper= new CustomObjectMapper();
      List<PackBack>packBacks= objectMapper.mapToPackBacks(lines);
      StringBuilder output=new StringBuilder();
      packBacks.forEach(packBack -> output.append(applyGreedyAlgorithm(packBack)).append("\n"));

    return output.toString();
  }

  private static String applyGreedyAlgorithm(PackBack packBack) {
      var output=new StringBuilder();

      packBack.getItem().stream()
              .sorted(Comparator.comparingDouble(Item::getValue)
                      .reversed()
                      .thenComparingDouble(Item::getWeight)
              )
              .filter(item -> item.getWeight()<packBack.getMaxWeight())
              .forEach(item -> {
                  packBack.setMaxWeight(packBack.getMaxWeight()-item.getWeight());
                  output.append(String.valueOf(item.getIndex())).append(",");
              });
      if (output.toString().isEmpty())
          return "-";
      else
          return output.substring(0,output.length()-1);
  }
}
