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
package me.zhengjie.service.impl;

import me.zhengjie.domain.ElTest;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.repository.ElTestRepository;
import me.zhengjie.service.ElTestService;
import me.zhengjie.service.dto.ElTestDto;
import me.zhengjie.service.dto.ElTestQueryCriteria;
import me.zhengjie.service.mapstruct.ElTestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class ElTestServiceImpl implements ElTestService {

    private final ElTestRepository elTestRepository;
    private final ElTestMapper elTestMapper;

    @Override
    public Map<String,Object> queryAll(ElTestQueryCriteria criteria, Pageable pageable){
        Page<ElTest> page = elTestRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(elTestMapper::toDto));
    }

    @Override
    public List<ElTestDto> queryAll(ElTestQueryCriteria criteria){
        return elTestMapper.toDto(elTestRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ElTestDto findById(Integer id) {
        ElTest elTest = elTestRepository.findById(id).orElseGet(ElTest::new);
        ValidationUtil.isNull(elTest.getId(),"ElTest","id",id);
        return elTestMapper.toDto(elTest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ElTestDto create(ElTest resources) {
        return elTestMapper.toDto(elTestRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ElTest resources) {
        ElTest elTest = elTestRepository.findById(resources.getId()).orElseGet(ElTest::new);
        ValidationUtil.isNull( elTest.getId(),"ElTest","id",resources.getId());
        elTest.copy(resources);
        elTestRepository.save(elTest);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            elTestRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ElTestDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ElTestDto elTest : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", elTest.getName());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}