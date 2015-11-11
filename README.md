#Popular Movies App
This app comprises assignments from Udacity's Android Nanodegree to show popular movies.

##TMDB
This app references themoviedb.org and requires an API key provided by themoviedb.org
which should be kept private in accordance with the terms and conditions.

###TMDB API Key Setup
The API key for TMDB should be defined as a string resource named tmdb_api_key.
I defined mine in app/src/main/res/values/api_key.xml which is already added to the .gitignore
to prevent accidentally adding it to the repo.

##Volley library
The Volley library is added as a git submodule which references the official android repository
and must be properly initialized.  Reference https://git-scm.com/book/en/v2/Git-Tools-Submodules
for details, but the following summary is provided for convenience below.

###Volley library checkout instructions
Either clone the entire project with the --recursive flag as in
`git clone https://github.com/dallinwilcox/popularmovies.git --recursive`
-OR-
run the following git commands once the parent project has been cloned
`git submodule init`
`git submodule update`