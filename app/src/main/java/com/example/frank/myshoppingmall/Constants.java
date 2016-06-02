package com.example.frank.myshoppingmall;

/**
 * 创建者     Frank
 * 创建时间   2016/4/22 17:50
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class Constants {


    public static final int LOGIN_RESULT_CODE = 0;

    public static final String DES_KEY = "Cniao5_123456";

    public static final String USER_JSON = "userjson";
    public static final String TOKEN     = "token";

    public static final String WARE_REQUEST_ID = "warerequestid";

    public static final String TO_WEBVIEW = "towebview";

    public static class URL {
        public static final String BASE_URL              = "http://112.124.22.238:8081/course_api/";
        public static final String BANNER_URL            = BASE_URL + "banner/query?type=1";
        public static final String HOME_ITEM_URL         = BASE_URL + "campaign/recommend";
        public static final String HOT_LIST_ITEM         = BASE_URL + "wares/hot";
        public static final String CATEGORY_LEFT_ITEM    = BASE_URL + "category/list";
        public static final String CATEGORY_CONTENT_ITEM = BASE_URL + "wares/list?";

        public static final String WARES_DETAIL = BASE_URL + "wares/detail.html";
        public static final String LOGIN        = BASE_URL + "auth/login";

        public static final String TO_POST_ORDER = BASE_URL + "order/create";

        public static final String GET_MYORDER = BASE_URL + "order/list";

    }


}
