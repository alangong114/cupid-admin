package com.gongmc.cupid.repository;

import com.gongmc.cupid.model.domain.Category;
import com.gongmc.cupid.repository.base.BaseRepository;

/**
 * <pre>
 *     分类持久层
 * </pre>
 *
 *
 * @date : 2017/11/30
 */
public interface CategoryRepository extends BaseRepository<Category, Long> {

    /**
     * 根据分类目录路径查询，用于验证是否已经存在该路径
     *
     * @param cateUrl cateUrl 文章url
     * @return Category
     */
    Category findCategoryByCateUrl(String cateUrl);

    /**
     * 根据分类名称查询
     *
     * @param cateName 分类名称
     * @return Category
     */
    Category findCategoryByCateName(String cateName);
}
