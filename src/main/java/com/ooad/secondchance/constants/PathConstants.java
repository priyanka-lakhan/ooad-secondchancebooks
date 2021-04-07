package com.ooad.secondchance.constants;

/**
 * Created by Priyanka on 3/20/21.
 */
public class PathConstants {
    public static final String ASSETS = "assets/";
    public static final String ASSETS_BOOKS = ASSETS + "books";
    public static final String ASSETS_USERS = ASSETS + "users";

    public static final String USER_CONTROLLER = "/users";
    public static final String BOOKS_CONTROLLER = "/books";
    public static final String BOOK_LISTING_CONTROLLER = "/booklisting";
    public static final String BOOK_TRANSACTION_CONTROLLER = "/transaction";
    public static final String BOOKS_BY_ID = BOOKS_CONTROLLER + "{Id:\\d+}";
}
