package com.micropos.carts.mapper;

import com.micropos.api.dto.ItemDto;
import com.micropos.carts.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    List<Item> toItems(List<ItemDto> itemDtos);

    List<ItemDto> toItemDtos(List<Item> items);

    @Mapping(
        target = "productDto",
        expression = "java(new ProductDto().id(itemDto.getId()).name(itemDto.getName()).price(itemDto.getPrice()).image(itemDto.getImage()))"
    )
    Item toItem(ItemDto itemDto);

    @Mappings({
            @Mapping(target = "id", expression = "java(item.getProductDto().getId())"),
            @Mapping(target = "name", expression = "java(item.getProductDto().getName())"),
            @Mapping(target = "price", expression = "java(item.getProductDto().getPrice())"),
            @Mapping(target = "image", expression = "java(item.getProductDto().getImage())"),
    })
    ItemDto toItemDto(Item item);
}
