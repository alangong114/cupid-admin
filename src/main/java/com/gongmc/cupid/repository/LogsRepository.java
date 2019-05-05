package com.gongmc.cupid.repository;

import com.gongmc.cupid.model.domain.Logs;
import com.gongmc.cupid.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * <pre>
 *     日志持久层
 * </pre>
 *
 *
 * @date : 2018/1/19
 */
public interface LogsRepository extends BaseRepository<Logs, Long> {

    /**
     * 查询最新的五条数据
     *
     * @return List
     */
    @Query(value = "SELECT * FROM cupid_logs ORDER BY log_created DESC LIMIT 5", nativeQuery = true)
    List<Logs> findTopFive();
}
