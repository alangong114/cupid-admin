package com.gongmc.cupid.web.controller.api;

import cn.hutool.core.util.StrUtil;
import com.gongmc.cupid.model.domain.Category;
import com.gongmc.cupid.model.domain.Post;
import com.gongmc.cupid.model.dto.Archive;
import com.gongmc.cupid.model.dto.JsonResult;
import com.gongmc.cupid.model.enums.BlogPropertiesEnum;
import com.gongmc.cupid.model.enums.PostStatusEnum;
import com.gongmc.cupid.model.enums.PostTypeEnum;
import com.gongmc.cupid.service.CategoryService;
import com.gongmc.cupid.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gongmc.cupid.model.dto.HaloConst.OPTIONS;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * <pre>
 *     文章归档API
 * </pre>
 *
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/archives")
public class ApiArchivesController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据年份归档
     *
     * <p>
     * result json:
     * <pre>
     * {
     *     "code": 200,
     *     "msg": "OK",
     *     "result": [
     *         {
     *             "year": "",
     *             "month": "",
     *             "count": "",
     *             "posts": [
     *                 {
     *                     "postId": "",
     *                     "user": {},
     *                     "postTitle": "",
     *                     "postType": "",
     *                     "postContentMd": "",
     *                     "postContent": "",
     *                     "postUrl": "",
     *                     "postSummary": "",
     *                     "categories": [],
     *                     "tags": [],
     *                     "comments": [],
     *                     "postThumbnail": "",
     *                     "postDate": "",
     *                     "postUpdate": "",
     *                     "postStatus": 0,
     *                     "postViews": 0,
     *                     "allowComment": 1,
     *                     "customTpl": ""
     *                 }
     *             ]
     *         }
     *     ]
     * }
     *     </pre>
     * </p>
     *
     * @return JsonResult
     */
    @GetMapping(value = "/year")
    public JsonResult archivesYear() {
        final List<Archive> archives = postService.findPostGroupByYear();
        if (!CollectionUtils.isEmpty(archives)) {
            return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), archives);
        } else {
            return new JsonResult(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
        }
    }

    /**
     * 根据月份归档
     *
     * <p>
     * result json:
     * <pre>
     * {
     *     "code": 200,
     *     "msg": "OK",
     *     "result": [
     *         {
     *             "year": "",
     *             "month": "",
     *             "count": "",
     *             "posts": [
     *                 {
     *                     "postId": "",
     *                     "user": {},
     *                     "postTitle": "",
     *                     "postType": "",
     *                     "postContentMd": "",
     *                     "postContent": "",
     *                     "postUrl": "",
     *                     "postSummary": "",
     *                     "categories": [],
     *                     "tags": [],
     *                     "comments": [],
     *                     "postThumbnail": "",
     *                     "postDate": "",
     *                     "postUpdate": "",
     *                     "postStatus": 0,
     *                     "postViews": 0,
     *                     "allowComment": 1,
     *                     "customTpl": ""
     *                 }
     *             ]
     *         }
     *     ]
     * }
     *     </pre>
     * </p>
     *
     * @return JsonResult
     */
    @GetMapping(value = "/year/month")
    public List<Archive> archivesYearAndMonth() {
        return postService.findPostGroupByYearAndMonth();
    }

    /**
     * @return JsonResult
     * @Author Aquan
     * @Description 返回所有文章
     * @Date 2019.1.4 11:06
     * @Param
     **/
    @GetMapping(value = "/all")
    public List<Archive> archivesAllPost() {
        return postService.findAllPost();
    }


    /**
     * 根据年份归档
     *
     * <p>
     * result json:
     * <pre>
     * {
     *     "code": 200,
     *     "msg": "OK",
     *     "result": [
     *         {
     *             "year": "",
     *             "month": "",
     *             "count": "",
     *             "posts": [
     *                 {
     *                     "postId": "",
     *                     "user": {},
     *                     "postTitle": "",
     *                     "postType": "",
     *                     "postContentMd": "",
     *                     "postContent": "",
     *                     "postUrl": "",
     *                     "postSummary": "",
     *                     "categories": [],
     *                     "tags": [],
     *                     "comments": [],
     *                     "postThumbnail": "",
     *                     "postDate": "",
     *                     "postUpdate": "",
     *                     "postStatus": 0,
     *                     "postViews": 0,
     *                     "allowComment": 1,
     *                     "customTpl": ""
     *                 }
     *             ]
     *         }
     *     ]
     * }
     *     </pre>
     * </p>
     *
     * @param cateUrl 分类路径
     * @return JsonResult
     */
    @GetMapping(value = "/{cateUrl}/page/{page}")
    public JsonResult categories(@PathVariable("cateUrl") String cateUrl, @PathVariable(value = "page") Integer page,
                                 @SortDefault(sort = "postDate", direction = DESC) Sort sort) {
        final Category category = categoryService.findByCateUrl(cateUrl);
        if (null == category) {
            return new JsonResult(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
        }
        int size = 10;
        if (StrUtil.isNotBlank(OPTIONS.get(BlogPropertiesEnum.INDEX_POSTS.getProp()))) {
            size = Integer.parseInt(OPTIONS.get(BlogPropertiesEnum.INDEX_POSTS.getProp()));
        }
        final Pageable pageable = PageRequest.of(page - 1, size, sort);
        final Page<Post> archives = postService.findPostByCategories(category, pageable);
        if (!CollectionUtils.isEmpty(archives.getContent())) {
            return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), archives);
        } else {
            return new JsonResult(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
        }
    }
}
