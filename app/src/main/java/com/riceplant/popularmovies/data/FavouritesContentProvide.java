package com.riceplant.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.riceplant.popularmovies.data.FavouritesContract.FavouritesAdd.TABLE_NAME;

public class FavouritesContentProvide extends ContentProvider {

        public static final int FAVOURITES = 700;
        public static final int FAVOURITES_WITH_ID = 701;
        private static final UriMatcher sUriMatcher = buildUriMatcher();

        private static UriMatcher buildUriMatcher() {
            UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            uriMatcher.addURI(FavouritesContract.AUTHORITY, FavouritesContract.PATH_FAVOURITES, FAVOURITES);
            uriMatcher.addURI(FavouritesContract.AUTHORITY, FavouritesContract.PATH_FAVOURITES + "/#", FAVOURITES_WITH_ID);
            return uriMatcher;
        }

        private FavouritesDbHelper mFavouritesDbHelper;

        @Override
        public boolean onCreate() {
            Context context = getContext();
            mFavouritesDbHelper = new FavouritesDbHelper(context);
            return true;
        }

        @Nullable
        @Override
        public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
            final SQLiteDatabase db = mFavouritesDbHelper.getReadableDatabase();
            int match = sUriMatcher.match(uri);
            Cursor returnCursor;

            switch (match){
                case FAVOURITES:
                    returnCursor = db.query(TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                    break;

                case FAVOURITES_WITH_ID:
                    String id = uri.getPathSegments().get(1);
                    String mSelection = "_id=?";
                    String[] mSelectionArgs = new String[]{id};

                    returnCursor = db.query(TABLE_NAME,
                            projection,
                            mSelection,
                            mSelectionArgs,
                            null,
                            null,
                            sortOrder);
                    break;

                default:
                    throw new UnsupportedOperationException("Unknown uri: "+ uri);
            }
            returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
            return returnCursor;
        }

        @Nullable
        @Override
        public String getType(@NonNull Uri uri) {
            int match = sUriMatcher.match(uri);

            switch (match){
                case FAVOURITES:
                    return "vnd.android.cursor.dir" + "/" + FavouritesContract.AUTHORITY + "/" + FavouritesContract.PATH_FAVOURITES;
                case FAVOURITES_WITH_ID:
                    return "vnd.android.cursor.item" + "/" + FavouritesContract.AUTHORITY + "/" + FavouritesContract.PATH_FAVOURITES;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        @Nullable
        @Override
        public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
            final SQLiteDatabase db = mFavouritesDbHelper.getWritableDatabase();
            int match = sUriMatcher.match(uri);
            Uri returnUri; //Uri to be returned

            switch (match){
                case FAVOURITES:
                    long id = db.insert(TABLE_NAME, null, values);
                    if (id > 0 ){
                        returnUri = ContentUris.withAppendedId(FavouritesContract.FavouritesAdd.CONTENT_URI, id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into" + uri);
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Unknow uri: " +uri);
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return returnUri;
        }

        @Override
        public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
            final SQLiteDatabase db = mFavouritesDbHelper.getWritableDatabase();
            int match = sUriMatcher.match(uri);
            int favoritesDeleted;

            switch (match) {
                case FAVOURITES:
                    favoritesDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

            if (favoritesDeleted != 0){
                getContext().getContentResolver().notifyChange(uri,null);
            }
            return favoritesDeleted;
        }

        @Override
        public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
            int favoriteUpdated;
            int match = sUriMatcher.match(uri);

            switch (match){
                case FAVOURITES_WITH_ID:
                    String id = uri.getPathSegments().get(1);
                    favoriteUpdated = mFavouritesDbHelper.getWritableDatabase()
                            .update(TABLE_NAME, values, "_id=?", new String[]{id});
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

            if (favoriteUpdated != 0){
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return favoriteUpdated;
        }
}
