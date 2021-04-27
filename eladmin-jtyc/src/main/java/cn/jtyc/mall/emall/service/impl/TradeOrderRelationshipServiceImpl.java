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
package cn.jtyc.mall.emall.service.impl;

import cn.jtyc.mall.emall.domain.TradeOrderRelationship;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import cn.jtyc.mall.emall.repository.TradeOrderRelationshipRepository;
import cn.jtyc.mall.emall.service.TradeOrderRelationshipService;
import cn.jtyc.mall.emall.service.dto.TradeOrderRelationshipDto;
import cn.jtyc.mall.emall.service.dto.TradeOrderRelationshipQueryCriteria;
import cn.jtyc.mall.emall.service.mapstruct.TradeOrderRelationshipMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author jiezhigang
* @date 2021-04-27
**/
@Service
@RequiredArgsConstructor
public class TradeOrderRelationshipServiceImpl implements TradeOrderRelationshipService {

    private final TradeOrderRelationshipRepository tradeOrderRelationshipRepository;
    private final TradeOrderRelationshipMapper tradeOrderRelationshipMapper;

    @Override
    public Map<String,Object> queryAll(TradeOrderRelationshipQueryCriteria criteria, Pageable pageable){
        Page<TradeOrderRelationship> page = tradeOrderRelationshipRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(tradeOrderRelationshipMapper::toDto));
    }

    @Override
    public List<TradeOrderRelationshipDto> queryAll(TradeOrderRelationshipQueryCriteria criteria){
        return tradeOrderRelationshipMapper.toDto(tradeOrderRelationshipRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TradeOrderRelationshipDto findById(String id) {
        TradeOrderRelationship tradeOrderRelationship = tradeOrderRelationshipRepository.findById(id).orElseGet(TradeOrderRelationship::new);
        ValidationUtil.isNull(tradeOrderRelationship.getId(),"TradeOrderRelationship","id",id);
        return tradeOrderRelationshipMapper.toDto(tradeOrderRelationship);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TradeOrderRelationshipDto create(TradeOrderRelationship resources) {
        resources.setId(IdUtil.simpleUUID()); 
        return tradeOrderRelationshipMapper.toDto(tradeOrderRelationshipRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TradeOrderRelationship resources) {
        TradeOrderRelationship tradeOrderRelationship = tradeOrderRelationshipRepository.findById(resources.getId()).orElseGet(TradeOrderRelationship::new);
        ValidationUtil.isNull( tradeOrderRelationship.getId(),"TradeOrderRelationship","id",resources.getId());
        tradeOrderRelationship.copy(resources);
        tradeOrderRelationshipRepository.save(tradeOrderRelationship);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            tradeOrderRelationshipRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TradeOrderRelationshipDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TradeOrderRelationshipDto tradeOrderRelationship : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("平台编号", tradeOrderRelationship.getOrderId());
            map.put("订单已被读取", tradeOrderRelationship.getOrderRead());
            map.put("配货完成状态", tradeOrderRelationship.getOrderAllocate());
            map.put("订单离港状态", tradeOrderRelationship.getOrderOffHarbour());
            map.put("类型", tradeOrderRelationship.getType());
            map.put("派件", tradeOrderRelationship.getIsDelivery());
            map.put("已经签收", tradeOrderRelationship.getIsSignIn());
            map.put("创建时间", tradeOrderRelationship.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}