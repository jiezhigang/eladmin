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
package cn.jtyc.mall.emall.rest;

import me.zhengjie.annotation.Log;
import cn.jtyc.mall.emall.domain.TradeOrderRelationship;
import cn.jtyc.mall.emall.service.TradeOrderRelationshipService;
import cn.jtyc.mall.emall.service.dto.TradeOrderRelationshipQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author jiezhigang
* @date 2021-04-27
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "惊天一城管理")
@RequestMapping("/api/tradeOrderRelationship")
public class TradeOrderRelationshipController {

    private final TradeOrderRelationshipService tradeOrderRelationshipService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tradeOrderRelationship:list')")
    public void download(HttpServletResponse response, TradeOrderRelationshipQueryCriteria criteria) throws IOException {
        tradeOrderRelationshipService.download(tradeOrderRelationshipService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询惊天一城")
    @ApiOperation("查询惊天一城")
    @PreAuthorize("@el.check('tradeOrderRelationship:list')")
    public ResponseEntity<Object> query(TradeOrderRelationshipQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(tradeOrderRelationshipService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增惊天一城")
    @ApiOperation("新增惊天一城")
    @PreAuthorize("@el.check('tradeOrderRelationship:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody TradeOrderRelationship resources){
        return new ResponseEntity<>(tradeOrderRelationshipService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改惊天一城")
    @ApiOperation("修改惊天一城")
    @PreAuthorize("@el.check('tradeOrderRelationship:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody TradeOrderRelationship resources){
        tradeOrderRelationshipService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除惊天一城")
    @ApiOperation("删除惊天一城")
    @PreAuthorize("@el.check('tradeOrderRelationship:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody String[] ids) {
        tradeOrderRelationshipService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}