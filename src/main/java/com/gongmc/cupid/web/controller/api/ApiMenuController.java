package com.gongmc.cupid.web.controller.api;

import com.gongmc.cupid.model.domain.Menu;
import com.gongmc.cupid.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <pre>
 *     菜单API
 * </pre>
 *
 *
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/menus")
public class ApiMenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取所有菜单
     *
     * <p>
     * result json:
     * <pre>
     * {
     *     "code": 200,
     *     "msg": "OK",
     *     "result": [
     *         {
     *             "menuId": ,
     *             "menuName": "",
     *             "menuUrl": "",
     *             "menuSort": ,
     *             "menuIcon": "",
     *             "menuTarget":
     *         }
     *     ]
     * }
     *     </pre>
     * </p>
     *
     * @return JsonResult
     */
    @GetMapping
    public List<Menu> menus() {
        return menuService.listAll();
    }
}
