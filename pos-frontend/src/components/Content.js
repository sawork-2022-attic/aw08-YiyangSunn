import React, {useContext} from "react"
import {Button, List} from "antd"
import "./Content.css"
import {useRequest} from "ahooks"
import {CartContext} from "../App"

export default function Content() {

  // 从上下文中取操作
  const {requestAddItem} = useContext(CartContext);

  // 页面第一次载入时从后端取数据
  const {data, error, loading} = useRequest(loadProducts)

  if (error) {
    console.log(error.message)
  }

  // 如何渲染列表项
  const renderItem = product => {
    return (
      <List.Item>
        <div className="product-container">
          <div className="product-image-container">
            <img className="product-image" alt={product.image} src={product.image}/>
          </div>
          <div className="product-price">
            {"￥" + product.price.toFixed(1)}
          </div>
          <div className="product-description">
            {product.name}
          </div>
          <div className="product-actions">
            <Button
              className="add-item-button"
              onClick={() => {requestAddItem(product.id, 1)}}
            >
              加入购物车
            </Button>
          </div>
        </div>
      </List.Item>
    )
  }

  return (
    <div className="content">
      <div className="product-list">
        <List
          dataSource={data}
          grid={{column: 4, gutter: 20}}
          loading={loading}
          renderItem={renderItem}
        />
      </div>
    </div>
  )
}

const loadProducts = async () => {
  return await fetch(
    "http://localhost:6001/api/products",
    {
      method: "GET"
    }
  ).then(response => {
    if (response.ok) {
      return response.json()
    } else {
      throw Error("Failed to load products!")
    }
  })
}
