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
import me.zhengjie.domain.Test;
import me.zhengjie.service.TestService;
import me.zhengjie.service.dto.TestQueryCriteria;
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
@Api(tags = "案例：测试管理")
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('test:list')")
    public void download(HttpServletResponse response, TestQueryCriteria criteria) throws IOException {
        testService.download(testService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询案例：测试")
    @ApiOperation("查询案例：测试")
    @PreAuthorize("@el.check('test:list')")
    public ResponseEntity<Object> query(TestQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(testService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增案例：测试")
    @ApiOperation("新增案例：测试")
    @PreAuthorize("@el.check('test:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Test resources){
        return new ResponseEntity<>(testService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改案例：测试")
    @ApiOperation("修改案例：测试")
    @PreAuthorize("@el.check('test:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Test resources){
        testService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除案例：测试")
    @ApiOperation("删除案例：测试")
    @PreAuthorize("@el.check('test:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        testService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}