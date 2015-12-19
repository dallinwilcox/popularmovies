package com.dallinwilcox.popularmovies.data;

/**
 * Created by dcwilcox on 12/18/2015.
 */

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

@SimpleSQLConfig(
        name = "FavoriteProvider",
        authority = "com.dallinwilcox.popularmovies_provider.authority",
        database = "favorites.db",
        version = 1)

public class FavoriteProviderConfig implements ProviderConfig {
    public static final String FAVORITE_PROVIDER = "FavoriteProvider";

    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
