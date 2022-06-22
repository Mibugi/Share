package com.mibugi.share;

import android.provider.BaseColumns;

public class PictureContract {
    private PictureContract(){}
    public static class PictureEntry implements BaseColumns{
        public static final String TABLE_NAME = "picture";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_LOVE_COUNT = "love_count";
    }
}
