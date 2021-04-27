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
package me.zhengjie.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.domain.ElTest;
import me.zhengjie.service.ElTestService;
import me.zhengjie.service.dto.ElTestQueryCriteria;
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
@Api(tags = "api/el/test管理")
@RequestMapping("/api/elTest")
public class ElTestController {

    private final ElTestService elTestService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('elTest:list')")
    public void download(HttpServletResponse response, ElTestQueryCriteria criteria) throws IOException {
        elTestService.download(elTestService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/el/test")
    @ApiOperation("查询api/el/test")
    @PreAuthorize("@el.check('elTest:list')")
    public ResponseEntity<Object> query(ElTestQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(elTestService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/el/test")
    @ApiOperation("新增api/el/test")
    @PreAuthorize("@el.check('elTest:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ElTest resources){
        return new ResponseEntity<>(elTestService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/el/test")
    @ApiOperation("修改api/el/test")
    @PreAuthorize("@el.check('elTest:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody ElTest resources){
        elTestService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除api/el/test")
    @ApiOperation("删除api/el/test")
    @PreAuthorize("@el.check('elTest:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        elTestService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}