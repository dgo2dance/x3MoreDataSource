package com.xiaogj.x3.search.service;

import com.alibaba.fastjson.JSONObject;
import com.xiaogj.x3.common.model.PageResult;
import com.xiaogj.x3.search.model.SearchDto;

import java.io.IOException;

/**
 * @author zlt
 * @date 2019/4/24
 */
public interface ISearchService {
    /**
     * StringQuery通用搜索
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     * @return
     */
    PageResult<JSONObject> strQuery(String indexName, SearchDto searchDto) throws IOException;
}
