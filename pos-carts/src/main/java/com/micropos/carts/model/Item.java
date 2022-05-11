package com.micropos.carts.model;

import com.micropos.api.dto.ProductDto;

import java.util.Objects;

public class Item {

    private ProductDto productDto;

    private int quantity;

    private long timeStamp;

    public Item(ProductDto productDto, int quantity, long timeStamp) {
        this.productDto = productDto;
        this.quantity = quantity;
        this.timeStamp = timeStamp;
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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
