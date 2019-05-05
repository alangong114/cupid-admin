package com.gongmc.cupid.service;

import com.gongmc.cupid.model.domain.Attachment;
import com.gongmc.cupid.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <pre>
 *     附件业务逻辑接口
 * </pre>
 *
 *
 * @date : 2018/1/10
 */
public interface AttachmentService extends CrudService<Attachment, Long> {

    /**
     * 上传转发
     *
     * @param file    file
     * @param request request
     * @return Map
     */
    Map<String, String> upload(MultipartFile file, HttpServletRequest request);

    /**
     * 原生服务器上传
     *
     * @param file    file
     * @param request request
     * @return Map
     */
    Map<String, String> attachUpload(MultipartFile file, HttpServletRequest request);

    /**
     * 七牛云上传
     *
     * @param file    file
     * @param request request
     * @return Map
     */
    Map<String, String> attachQiNiuUpload(MultipartFile file, HttpServletRequest request);

    /**
     * 又拍云上传
     *
     * @param file    file
     * @param request request
     * @return Map
     */
    Map<String, String> attachUpYunUpload(MultipartFile file, HttpServletRequest request);

    /**
     * 七牛云删除附件
     *
     * @param key key
     * @return boolean
     */
    boolean deleteQiNiuAttachment(String key);

    /**
     * 又拍云删除附件
     *
     * @param fileName fileName
     * @return boolean
     */
    boolean deleteUpYunAttachment(String fileName);

}
