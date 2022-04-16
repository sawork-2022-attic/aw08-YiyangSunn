import React from "react";
import {Badge, Button, Input, Popover} from "antd";
import {AudioOutlined, CameraOutlined, ShoppingCartOutlined, WindowsOutlined} from "@ant-design/icons";

import "./Header.css";

export default function Header(props) {
  // 下拉菜单组件和购物车数据
  const {sider, itemList} = props;

  return (
    <div className="header">
      <div
        className="brand"
        style={{
          color: "black",
          fontSize: "35px"
        }}
      >
        <WindowsOutlined
          style={{
            color: "black",
            fontSize: "60px",
            marginRight: "20px"
          }}
        />
        Micropos
      </div>

      {/*顶部搜索框*/}
      <Input.Search
        className="search"
        placeholder="Not Implemented"
        size="large"
        prefix={<AudioOutlined style={{marginRight: "15px"}}/>}
        suffix={<CameraOutlined style={{marginLeft: "15px"}}/>}
        enterButton
      />

      {/*弹出购物车下拉菜单*/}
      <div
        style={{
          marginLeft: "30px"
        }}
      >
        <Popover
          content={sider}
          // trigger="click"
        >
          <Badge count={itemList.length}>
            <Button
              style={{
                width: "auto",
                height: "auto"
              }}
            >
              <ShoppingCartOutlined
                style={{
                  height: "20px",
                  width: "20px"
                }}
              />
              <span
                style={{
                  fontSize: "16px"
                }}
              >
                我的购物车
              </span>
            </Button>
          </Badge>
        </Popover>
      </div>
    </div>
  );
}
