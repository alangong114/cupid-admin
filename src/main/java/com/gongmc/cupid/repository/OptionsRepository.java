package com.gongmc.cupid.repository;

import com.gongmc.cupid.model.domain.Options;
import com.gongmc.cupid.repository.base.BaseRepository;

/**
 * <pre>
 *     系统设置持久层
 * </pre>
 *
 *
 * @date : 2017/11/14
 */
public interface OptionsRepository extends BaseRepository<Options, String> {

    /**
     * 根据key查询单个option
     *
     * @param key key
     * @return Options
     */
    Options findOptionsByOptionName(String key);
}
