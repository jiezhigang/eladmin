/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package cn.jtyc.mall.emall.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author jiezhigang
* @date 2021-04-27
**/
@Entity
@Data
@Table(name="trade_order_relationship")
public class TradeOrderRelationship implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "订单编号")
    private String id;

    @Column(name = "order_id",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "平台编号")
    private String orderId;

    @Column(name = "order_read")
    @ApiModelProperty(value = "订单已被读取")
    private Integer orderRead;

    @Column(name = "order_allocate")
    @ApiModelProperty(value = "配货完成状态")
    private Integer orderAllocate;

    @Column(name = "order_off_harbour")
    @ApiModelProperty(value = "订单离港状态")
    private Integer orderOffHarbour;

    @Column(name = "type")
    @ApiModelProperty(value = "类型")
    private String type;

    @Column(name = "is_delivery")
    @ApiModelProperty(value = "派件")
    private Integer isDelivery;

    @Column(name = "is_sign_in")
    @ApiModelProperty(value = "已经签收")
    private Integer isSignIn;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    public void copy(TradeOrderRelationship source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}