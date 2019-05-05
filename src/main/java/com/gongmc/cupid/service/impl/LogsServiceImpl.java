package com.gongmc.cupid.service.impl;

import com.gongmc.cupid.model.domain.Logs;
import com.gongmc.cupid.repository.LogsRepository;
import com.gongmc.cupid.service.LogsService;
import com.gongmc.cupid.service.base.AbstractCrudService;
import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <pre>
 *     日志业务逻辑实现类
 * </pre>
 *
 *
 * @date : 2018/1/19
 */
@Service
public class LogsServiceImpl extends AbstractCrudService<Logs, Long> implements LogsService {

    private final LogsRepository logsRepository;

    public LogsServiceImpl(LogsRepository logsRepository) {
        super(logsRepository);
        this.logsRepository = logsRepository;
    }


    /**
     * 保存日志
     *
     * @param logTitle   logTitle
     * @param logContent logContent
     * @param request    request
     */
    @Override
    public void save(String logTitle, String logContent, HttpServletRequest request) {
        final Logs logs = new Logs();
        logs.setLogTitle(logTitle);
        logs.setLogContent(logContent);
        logs.setLogIp(ServletUtil.getClientIP(request));
        logsRepository.save(logs);
    }

    /**
     * 查询最新的五条日志
     *
     * @return List
     */
    @Override
    public List<Logs> findLogsLatest() {
        return logsRepository.findTopFive();
    }
}
