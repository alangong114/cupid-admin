package com.gongmc.cupid.web.controller.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.gongmc.cupid.model.domain.*;
import com.gongmc.cupid.model.dto.JsonResult;
import com.gongmc.cupid.model.dto.LogsRecord;
import com.gongmc.cupid.model.enums.*;
import com.gongmc.cupid.service.*;
import com.gongmc.cupid.utils.MarkdownUtils;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gongmc.cupid.model.dto.HaloConst.OPTIONS;

/**
 * <pre>
 *     网站初始化控制器
 * </pre>
 *
 * @date : 2018/1/28
 */
@Slf4j
@Controller
@RequestMapping(value = "/install")
public class InstallController {

    @Autowired
    private OptionsService optionsService;

    @Autowired
    private UserService userService;

    @Autowired
    private LogsService logsService;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private MenuService menuService;

    @Autowired
    private Configuration configuration;

    /**
     * 渲染安装页面
     *
     * @param model model
     * @return 模板路径
     */
    @GetMapping
    public String install(Model model) {
        try {
            if (StrUtil.equals(TrueFalseEnum.TRUE.getDesc(), OPTIONS.get(BlogPropertiesEnum.IS_INSTALL.getProp()))) {
                model.addAttribute("isInstall", true);
            } else {
                model.addAttribute("isInstall", false);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "common/install";
    }

    /**
     * 执行安装
     *
     * @param blogLocale      系统语言
     * @param userName        用户名
     * @param userDisplayName 用户名显示名
     * @param userEmail       用户邮箱
     * @param userPwd         用户密码
     * @param request         request
     * @return JsonResult
     */
    @PostMapping(value = "/do")
    @ResponseBody
    public JsonResult doInstall(@RequestParam("blogLocale") String blogLocale,
                                @RequestParam("blogTitle") String blogTitle,
                                @RequestParam("blogUrl") String blogUrl,
                                @RequestParam("userName") String userName,
                                @RequestParam("userDisplayName") String userDisplayName,
                                @RequestParam("userEmail") String userEmail,
                                @RequestParam("userPwd") String userPwd,
                                HttpServletRequest request) {
        try {
            if (StrUtil.equals(TrueFalseEnum.TRUE.getDesc(), OPTIONS.get(BlogPropertiesEnum.IS_INSTALL.getProp()))) {
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), "已初始化，不能再次安装！");
            }
            //创建新的用户
            final User user = new User();
            user.setUserName(userName);
            if (StrUtil.isBlank(userDisplayName)) {
                userDisplayName = userName;
            }
            user.setUserDisplayName(userDisplayName);
            user.setUserEmail(userEmail);
            user.setUserPass(SecureUtil.md5(userPwd));
            userService.create(user);

            //默认分类
            final Category category1 = new Category();
            category1.setCateName("媒体资讯");
            category1.setCateUrl("information");
            category1.setCateDesc("媒体资讯");
            categoryService.create(category1);

            final Category category2 = new Category();
            category2.setCateName("公告");
            category2.setCateUrl("notice");
            category2.setCateDesc("公告");
            categoryService.create(category2);

            //第一篇文章
            final Post post1 = new Post();
            final List<Category> categories = new ArrayList<>();
            categories.add(category1);
            post1.setPostTitle("你好,丘比特");

            post1.setPostUsTitle("Hello Cupid!");
            post1.setPostContentMd("# 你好,丘比特!\n" +
                    "欢迎使用Cupid进行创作，删除这篇资讯后赶紧开始吧。");
            post1.setPostContent(MarkdownUtils.renderMarkdown(post1.getPostContentMd()));
            post1.setPostUsContentMd("# Hello Cupid!\n" +
                    "Welcome to use Cupid for authoring, delete this post and get started.");
            post1.setPostUsContent(MarkdownUtils.renderMarkdown(post1.getPostContentMd()));

            post1.setPostSummary("欢迎使用Cupid进行创作，删除这篇资讯后赶紧开始吧。");
            post1.setPostUsSummary("Welcome to Cupid for authoring, delete this post and get started");
            post1.setPostStatus(0);
            post1.setPostUrl("hello-information");
            post1.setUser(user);
            post1.setCategories(categories);
            post1.setAllowComment(AllowCommentEnum.ALLOW.getCode());
            post1.setPostThumbnail("/static/cupid-frontend/images/thumbnail/thumbnail-" + RandomUtil.randomInt(1, 11) + ".jpg");
            postService.create(post1);

            //
            final Post post2 = new Post();
//            final List<Category> categories = new ArrayList<>();
            categories.add(category2);

            post2.setPostTitle("你好,丘比特");
            post2.setPostUsTitle("Hello Cupid!");
            post2.setPostContentMd("# 你好,丘比特!\n" +
                    "欢迎使用Cupid进行创作，删除这篇公告后赶紧开始吧。");
            post2.setPostContent(MarkdownUtils.renderMarkdown(post2.getPostContentMd()));
            post2.setPostUsContentMd("# Hello Cupid!\n" +
                    "Welcome to use Cupid for authoring, delete this notice and get started.");
            post2.setPostUsContent(MarkdownUtils.renderMarkdown(post2.getPostContentMd()));
            post2.setPostSummary("欢迎使用Cupid进行创作，删除这篇公告后赶紧开始吧。");
            post2.setPostUsSummary("Welcome to use Cupid for authoring, delete this notice and get started.");

            post2.setPostStatus(0);
            post2.setPostUrl("hello-notice");
            post2.setUser(user);
            post2.setCategories(new ArrayList<>());
            post2.setAllowComment(AllowCommentEnum.ALLOW.getCode());
            post2.setPostThumbnail("/static/cupid-frontend/images/thumbnail/thumbnail-" + RandomUtil.randomInt(1, 11) + ".jpg");
            postService.create(post2);


            final Map<String, String> options = new HashMap<>();
            options.put(BlogPropertiesEnum.IS_INSTALL.getProp(), TrueFalseEnum.TRUE.getDesc());
            options.put(BlogPropertiesEnum.BLOG_LOCALE.getProp(), blogLocale);
            options.put(BlogPropertiesEnum.BLOG_TITLE.getProp(), blogTitle);
            options.put(BlogPropertiesEnum.BLOG_URL.getProp(), blogUrl);
            options.put(BlogPropertiesEnum.THEME.getProp(), "anatole");
            options.put(BlogPropertiesEnum.BLOG_START.getProp(), DateUtil.format(DateUtil.date(), "yyyy-MM-dd"));
            options.put(BlogPropertiesEnum.SMTP_EMAIL_ENABLE.getProp(), TrueFalseEnum.FALSE.getDesc());
            options.put(BlogPropertiesEnum.NEW_COMMENT_NOTICE.getProp(), TrueFalseEnum.FALSE.getDesc());
            options.put(BlogPropertiesEnum.COMMENT_PASS_NOTICE.getProp(), TrueFalseEnum.FALSE.getDesc());
            options.put(BlogPropertiesEnum.COMMENT_REPLY_NOTICE.getProp(), TrueFalseEnum.FALSE.getDesc());
            options.put(BlogPropertiesEnum.ATTACH_LOC.getProp(), AttachLocationEnum.SERVER.getDesc());
            options.put(BlogPropertiesEnum.API_STATUS.getProp(), TrueFalseEnum.TRUE.getDesc());
            options.put(BlogPropertiesEnum.API_TOKEN.getProp(), "9f47733dfb9075387f8705dbc7e93199");
            optionsService.saveOptions(options);

            //更新日志
            logsService.save(LogsRecord.INSTALL, "安装成功，欢迎使用", request);

            final Menu menuIndex = new Menu();
            menuIndex.setMenuName("首页");
            menuIndex.setMenuUrl("/");
            menuIndex.setMenuSort(1);
            menuIndex.setMenuIcon(" ");
            menuService.create(menuIndex);

            final Menu menuArchive = new Menu();
            menuArchive.setMenuName("归档");
            menuArchive.setMenuUrl("/archives");
            menuArchive.setMenuSort(2);
            menuArchive.setMenuIcon(" ");
            menuService.create(menuArchive);

            OPTIONS.clear();
            OPTIONS = optionsService.findAllOptions();
            configuration.setSharedVariable("options", OPTIONS);
            configuration.setSharedVariable("user", userService.findUser());
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), e.getMessage());
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "安装成功！");
    }
}
