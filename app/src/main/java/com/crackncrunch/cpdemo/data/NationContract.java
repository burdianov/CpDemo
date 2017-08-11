package com.crackncrunch.cpdemo.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class NationContract {

    public static final String CONTENT_AUTHORITY =
            "com.crackncrunch.cpdemo.data.NationProvider";

    public static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_COUNTRIES = "countries";

    // in case of multiple tables, a separate class implementing BaseColumns must be created
    public static final class NationEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(BASE_CONTENT_URI, PATH_COUNTRIES);

        public static final String TABLE_NAME = "countries";

        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_CONTINENT = "continent";
    }
}
