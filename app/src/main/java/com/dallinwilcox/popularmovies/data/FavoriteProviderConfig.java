package com.dallinwilcox.popularmovies.data;

/**
 * Created by dcwilcox on 12/18/2015.
 */

import com.dallinwilcox.popularmovies.inf.MovieApplication;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

@SimpleSQLConfig(
        name = MovieApplication.FAVORITE_PROVIDER,
        authority = "com.dallinwilcox.popularmovies.provider.authority",
        database = "favorite.db",
        version = 1)

public class FavoriteProviderConfig implements ProviderConfig {
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
