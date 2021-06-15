package com.mobiquity.packer;

import com.mobiquity.dto.Item;
import com.mobiquity.dto.BackPack;
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
      List<BackPack> backPacks = objectMapper.mapToPackBacks(lines);
      StringBuilder output=new StringBuilder();
      backPacks.forEach(backPack -> output.append(applyGreedyAlgorithm(backPack)).append("\n"));

    return output.toString();
  }

    /**
     * @apiNote apply Greedy algorithm to solve problem by including
     *   max value with allowed weight to fill the backpack
     * @param backPack attribute contains max weight & list<Item> that's used to calculate max N# Item
     * @return String contains indexes of included Items
     */
  private static String applyGreedyAlgorithm(BackPack backPack) {
      var output=new StringBuilder();

      backPack.getItem().stream()
              .sorted(Comparator.comparingDouble(Item::getValue)
                      .reversed()
                      .thenComparingDouble(Item::getWeight)
              )
              .filter(item -> item.getWeight()< backPack.getMaxWeight())
              .forEach(item -> {
                  backPack.setMaxWeight(backPack.getMaxWeight()-item.getWeight());
                  output.append(String.valueOf(item.getIndex())).append(",");
              });
      if (output.toString().isEmpty())
          return "-";
      else
          return output.substring(0,output.length()-1);
  }
}
