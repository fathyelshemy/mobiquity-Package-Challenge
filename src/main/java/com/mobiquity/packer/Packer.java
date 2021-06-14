package com.mobiquity.packer;

import com.mobiquity.dto.Item;
import com.mobiquity.dto.PackBack;
import com.mobiquity.exception.APIException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Packer {

  public static String pack(String filePath) throws APIException {
    readFile(filePath);
    return null;
  }

  private static Map<Integer, List<Item>> readFile(String filePath){
    try {
      Files.lines(Path.of(filePath)).map(Packer::parsePackBackLine).forEach(packBack -> {
          StringBuilder output=new StringBuilder();

          packBack.getItem().stream()
                .sorted(Comparator.comparingDouble(Item::getValue)
                        .reversed()
                        .thenComparingDouble(Item::getWeight)
                )
                .filter(item -> item.getWeight()<packBack.getMaxWeight())
                .forEach(item -> {
                    packBack.setMaxWeight(packBack.getMaxWeight()-item.getWeight());
                  output.append(String.valueOf(item.getIndex())+",");
                });
            if (output.toString().isEmpty())
                System.out.println("-");
            else
                System.out.println(output.substring(0,output.length()-1));
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Collections.EMPTY_MAP;
  }
  private static PackBack parsePackBackLine(String line){
    PackBack packBack= new PackBack();
    String [] packPackStr=line.split(":");
    packBack.setMaxWeight(Double.parseDouble(packPackStr[0].trim()));
    List<Item> items=Arrays.stream(packPackStr[1].trim().split(" "))
            .map(item-> item.substring(1,item.length()-1))
            .map(itemStr-> {
              String [] itemAttributes= itemStr.split(",");
              var item= new Item();
              item.setIndex(Short.parseShort(itemAttributes[0]));
              item.setWeight(Double.parseDouble(itemAttributes[1]));
              item.setValue(Double.parseDouble(itemAttributes[2].substring(1)));
              return item;
            }).collect(Collectors.toList());
    packBack.setItem(items);
    return packBack;
  }

}
