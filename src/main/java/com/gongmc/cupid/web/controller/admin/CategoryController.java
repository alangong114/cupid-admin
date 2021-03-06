package com.gongmc.cupid.web.controller.admin;

import com.gongmc.cupid.model.domain.Category;
import com.gongmc.cupid.model.dto.JsonResult;
import com.gongmc.cupid.model.enums.ResultCodeEnum;
import com.gongmc.cupid.service.CategoryService;
import com.gongmc.cupid.utils.LocaleMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * <pre>
 *     后台分类管理控制器
 * </pre>
 *
 *
 * @date : 2017/12/10
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LocaleMessageUtil localeMessageUtil;

    /**
     * 查询所有分类并渲染category页面
     *
     * @return 模板路径admin/admin_category
     */
    @GetMapping
    public String categories() {
        return "admin/admin_category";
    }

    /**
     * 新增/修改分类目录
     *
     * @param category category对象
     * @return JsonResult
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public JsonResult saveCategory(@Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), error.getDefaultMessage());
            }
        }
        final Category tempCategory = categoryService.findByCateUrl(category.getCateUrl());
        if (null != category.getCateId()) {
            if (null != tempCategory && !category.getCateId().equals(tempCategory.getCateId())) {
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.common.url-is-exists"));
            }
        } else {
            if (null != tempCategory) {
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.common.url-is-exists"));
            }
        }
        category = categoryService.create(category);
        if (null == category) {
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), localeMessageUtil.getMessage("code.admin.common.save-failed"));
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), localeMessageUtil.getMessage("code.admin.common.save-success"));
    }

    /**
     * 处理删除分类目录的请求
     *
     * @param cateId cateId
     * @return 重定向到/admin/category
     */
    @GetMapping(value = "/remove")
    public String removeCategory(@RequestParam("cateId") Long cateId) {
        try {
            categoryService.removeById(cateId);
        } catch (Exception e) {
            log.error("Delete category failed: {}", e.getMessage());
        }
        return "redirect:/admin/category";
    }

    /**
     * 跳转到修改页面
     *
     * @param cateId cateId
     * @param model  model
     * @return 模板路径admin/admin_category
     */
    @GetMapping(value = "/edit")
    public String toEditCategory(Model model, @RequestParam("cateId") Long cateId) {
        final Optional<Category> category = categoryService.fetchById(cateId);
        model.addAttribute("updateCategory", category.orElse(new Category()));
        return "admin/admin_category";
    }
}
