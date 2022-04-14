package com.micropos.carts.model;

import com.micropos.api.dto.ProductDto;

import java.util.Objects;

public class Item {

    private ProductDto productDto;

    private int quantity;

    public Item(ProductDto productDto, int quantity) {
        this.productDto = productDto;
        this.quantity = quantity;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return quantity == item.quantity && productDto.equals(item.productDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productDto, quantity);
    }

    @Override
    public String toString() {
        return "Item{" +
                "productDto=" + productDto +
                ", quantity=" + quantity +
                '}';
    }

}
