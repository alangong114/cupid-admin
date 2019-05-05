package com.gongmc.cupid.task;

import com.gongmc.cupid.model.domain.Post;
import com.gongmc.cupid.service.PostService;
import com.gongmc.cupid.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import static com.gongmc.cupid.model.dto.HaloConst.POSTS_VIEWS;

/**
 *
 * @date : 2018/12/5
 */
@Slf4j
public class PostSyncTask {

    /**
     * 将缓存的图文浏览数写入数据库
     */
    public void postSync() {
        final PostService postService = SpringUtil.getBean(PostService.class);
        int count = 0;
        for (Long key : POSTS_VIEWS.keySet()) {
            Post post = postService.getByIdOfNullable(key);
            if (null != post) {
                post.setPostViews(post.getPostViews() + POSTS_VIEWS.get(key));
                postService.create(post);
                count++;
            }
        }
        log.info("The number of visits to {} posts has been updated", count);
        POSTS_VIEWS.clear();
    }
}
