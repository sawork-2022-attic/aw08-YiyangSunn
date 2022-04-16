import React, {useEffect, useState} from "react";
import {Button, Card, Spin, Empty} from "antd";
import {PlusOutlined, MinusOutlined} from "@ant-design/icons"

import "./Sider.css";

export default function Sider(props) {
  // 获取购物车数据以及相关可用操作
  const {
    itemList,
    requestAddItem,
    requestGetItems,
    requestRemoveItem,
    requestEmptyItems
  } = props;

  // 加载状态
  const [isLoading, setIsLoading] = useState(true);

  // 从后端读取购物车数据
  useEffect(() => {
    console.log("Fetch item list from backend.");
    requestGetItems().then(() => setIsLoading(false));
    // eslint-disable-next-line
  }, []);

  return (
    <div className="sider">
      {
        isLoading ?
          <div className="spin-container">
            <Spin
              size="large"
            />
          </div> :
          itemList.length <= 0 ?
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                width: "100%",
                height: "100%"
              }}
            >
              <Empty/>
            </div> :
            <div>
              <div className="item-list">
                {
                  itemList.map(item => (
                    <Card
                      key={item.id}
                      cover={
                        <div style={{
                          display: "flex",
                        }}>
                          <img
                            alt={item.image}
                            src={item.image}
                            style={{
                              height: "auto",
                              width: "100px"
                            }}
                          />
                          <span
                            style={{
                              width: "250px"
                            }}
                          >
                            {item.name}
                          </span>
                        </div>
                      }
                    >
                      <Card.Meta
                        title={
                          <div>
                            <Button
                              size="small"
                              danger
                              onClick={() => requestRemoveItem(item.id)}
                            >
                              删除
                            </Button>
                            <div className="title-container">
                              <span
                                style={{
                                  marginRight: "30px"
                                }}
                              >
                                {"￥" + item.price.toFixed(1)}
                              </span>
                              <div className="quantity-group">
                                <Button
                                  icon={<MinusOutlined/>}
                                  size="small"
                                  onClick={() => requestAddItem(item.id, -1)}
                                  disabled={item.quantity === 1}
                                />
                                <span
                                  style={{
                                    marginLeft: "8px",
                                    marginRight: "8px",
                                    fontSize: "18px"
                                  }}
                                >{item.quantity}</span>
                                <Button
                                  icon={<PlusOutlined/>}
                                  size="small"
                                  onClick={() => requestAddItem(item.id, 1)}
                                />
                              </div>
                            </div>
                          </div>
                        }
                      />
                    </Card>
                  ))
                }
              </div>
              <div
                style={{
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                  background: "white"
                }}
              >
                <Button
                  size="middle"
                  danger
                  style={{
                    margin: "5px"
                  }}
                  onClick={requestEmptyItems}
                >
                  清空
                </Button>
              </div>
            </div>
      }
    </div>
  );
}
