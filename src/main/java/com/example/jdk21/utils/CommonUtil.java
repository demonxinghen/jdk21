package com.example.jdk21.utils;

import com.example.jdk21.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;

/**
 * @author admin
 * @date 2024/1/3 18:45
 */
public class CommonUtil {

    public static boolean isIgnoreUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        for (String ignoreUrl : CommonConstant.FILTER_IGNORE_URLS) {
            if (url.startsWith(ignoreUrl)) {
                return true;
            }
        }
        return false;
    }
}
