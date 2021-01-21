package com.xiaogj.x3.oauth.service;

import com.xiaogj.x3.common.model.PageResult;
import com.xiaogj.x3.oauth.model.TokenVo;

import java.util.Map;

/**
 * @author zlt
 */
public interface ITokensService {
    /**
     * 查询token列表
     * @param params 请求参数
     * @param clientId 应用id
     */
    PageResult<TokenVo> listTokens(Map<String, Object> params, String clientId);
}
