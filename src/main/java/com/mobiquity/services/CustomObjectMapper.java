package com.mobiquity.services;

import com.mobiquity.dto.Item;
import com.mobiquity.dto.BackPack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomObjectMapper {
    /**
     * convert list of strings to backpack pojos
     * @param lines list lines
     * @return list of  BackPack object
     */
    public List<BackPack> mapToPackBacks(List<String> lines) {
        return lines.stream()
                .map(this::getPackBack)
                .collect(Collectors.toList());
    }

    /**
     * used to convert string to pojo BackPack
     * @param line string contains items & max Weight
     * @return  Backpack object
     */
    private BackPack getPackBack(String line) {
        BackPack backPack = new BackPack();
        String [] packPackStr=line.split(":");
        backPack.setMaxWeight(Double.parseDouble(packPackStr[0].trim()));
        backPack.setItem(getItems(packPackStr[1].trim()));
        return backPack;
    }

    /**
     *  used to extract items data from lines
     * @param itemSection part from string line contain special format for string
     * @return list of items
     */
    private List<Item> getItems (String itemSection) {
        return Arrays.stream(itemSection.split(" "))
                .map(item-> item.substring(1,item.length()-1))
                .map(itemStr-> {
                    String [] itemAttributes= itemStr.split(",");
                    var item= new Item();
                    item.setIndex(Short.parseShort(itemAttributes[0]));
                    item.setWeight(Double.parseDouble(itemAttributes[1]));
                    item.setValue(Double.parseDouble(itemAttributes[2].substring(1)));
                    return item;
                }).collect(Collectors.toList());

    }

}
