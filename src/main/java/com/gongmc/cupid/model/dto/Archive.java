package com.gongmc.cupid.model.dto;

import com.gongmc.cupid.model.domain.Post;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     文章归档
 * </pre>
 *
 *
 * @date : 2018/1/20
 */
@Data
public class Archive implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 年份
     */
    private String year;

    /**
     * 月份
     */
    private String month;

    /**
     * 对应的文章数
     */
    private String count;

    /**
     * 对应的文章
     */
    private List<Post> posts;
}
