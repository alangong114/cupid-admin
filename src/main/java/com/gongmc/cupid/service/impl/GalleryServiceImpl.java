package com.gongmc.cupid.service.impl;

import com.gongmc.cupid.model.domain.Gallery;
import com.gongmc.cupid.repository.GalleryRepository;
import com.gongmc.cupid.service.GalleryService;
import com.gongmc.cupid.service.base.AbstractCrudService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *     图库业务逻辑实现类
 * </pre>
 *
 *
 * @date : 2018/2/26
 */
@Service
public class GalleryServiceImpl extends AbstractCrudService<Gallery, Long> implements GalleryService {

    private static final String GALLERIES_CACHE_NAME = "galleries";

    private final GalleryRepository galleryRepository;

    public GalleryServiceImpl(GalleryRepository galleryRepository) {
        super(galleryRepository);
        this.galleryRepository = galleryRepository;
    }

    /**
     * 保存图片
     *
     * @param gallery gallery
     * @return Gallery
     */
    @Override
    @CacheEvict(value = GALLERIES_CACHE_NAME, allEntries = true, beforeInvocation = true)
    public Gallery create(Gallery gallery) {
        return super.create(gallery);
    }

    /**
     * 根据编号删除图片
     *
     * @param galleryId galleryId
     * @return Gallery
     */
    @Override
    @CacheEvict(value = GALLERIES_CACHE_NAME, allEntries = true, beforeInvocation = true)
    public Gallery removeById(Long galleryId) {
        return super.removeById(galleryId);
    }

    /**
     * 查询所有图片 不分页
     *
     * @return List
     */
    @Override
    @Cacheable(value = GALLERIES_CACHE_NAME, key = "'gallery'")
    public List<Gallery> listAll() {
        return super.listAll();
    }

}
