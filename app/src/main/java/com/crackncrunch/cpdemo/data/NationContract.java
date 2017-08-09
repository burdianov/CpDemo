package com.crackncrunch.cpdemo.data;

import android.provider.BaseColumns;

public final class NationContract {

    // in case of multiple tables, a separate class implementing BaseColumns must be created
    public static final class NationEntry implements BaseColumns {

        public static final String TABLE_NAME = "countries";

        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_CONTINENT = "continent";
    }
}
