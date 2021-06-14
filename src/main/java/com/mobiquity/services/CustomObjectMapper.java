package com.mobiquity.services;

import com.mobiquity.dto.Item;
import com.mobiquity.dto.PackBack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomObjectMapper {

    public List<PackBack> mapToPackBacks(List<String> lines) {
        return lines.stream()
                .map(this::getPackBack)
                .collect(Collectors.toList());
    }

    private PackBack getPackBack(String line) {
        PackBack packBack= new PackBack();
        String [] packPackStr=line.split(":");
        packBack.setMaxWeight(Double.parseDouble(packPackStr[0].trim()));
        packBack.setItem(getItems(packPackStr[1].trim()));
        return packBack;
    }
    private List<Item> getItems (String ItemSection) {
        return Arrays.stream(ItemSection.split(" "))
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
