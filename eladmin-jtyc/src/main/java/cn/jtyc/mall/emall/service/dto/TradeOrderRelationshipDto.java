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
package cn.jtyc.mall.emall.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author jiezhigang
* @date 2021-04-27
**/
@Data
public class TradeOrderRelationshipDto implements Serializable {

    /** 订单编号 */
    private String id;

    /** 平台编号 */
    private String orderId;

    /** 订单已被读取 */
    private Integer orderRead;

    /** 配货完成状态 */
    private Integer orderAllocate;

    /** 订单离港状态 */
    private Integer orderOffHarbour;

    /** 类型 */
    private String type;

    /** 派件 */
    private Integer isDelivery;

    /** 已经签收 */
    private Integer isSignIn;

    /** 创建时间 */
    private Timestamp createTime;
}