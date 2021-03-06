package com.gongmc.cupid.utils;

import lombok.extern.slf4j.Slf4j;

import static com.gongmc.cupid.model.dto.HaloConst.OWO;

/**
 * <pre>
 *     OwO表情工具类
 * </pre>
 *
 *
 * @date : 2017/12/22
 */
@Slf4j
public class OwoUtil {

    /**
     * 将表情标志转化为图片地址
     *
     * @param mark 表情标志
     * @return 表情图片地址
     */
    public static String markToImg(String mark) {
        for (String key : OWO.keySet()) {
            mark = mark.replace(key, OWO.get(key));
        }
        return mark;
    }
}
