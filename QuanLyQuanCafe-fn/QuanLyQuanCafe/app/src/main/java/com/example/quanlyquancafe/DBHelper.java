package com.example.quanlyquancafe;

import static android.provider.BaseColumns._ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "db_coffee.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE tb_account(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "username TEXT," +
                        "email TEXT, " +
                        "password TEXT," +
                        "role INT)"
        );
        sqLiteDatabase.execSQL(
                "CREATE TABLE tb_product(" +
                        "id TEXT PRIMARY KEY, " +
                        "name TEXT, " +
                        "image TEXT, " +
                        "price BIGINT)"
        );
        sqLiteDatabase.execSQL(
                "CREATE TABLE tb_order(" +
                        "id BIGINT, " +
                        "account_id INT, " +
                        "product_id TEXT," +
                        "quantity INT," +
                        "address TEXt," +
                        "phone TEXT, " +
                        "PRIMARY KEY(id, account_id, product_id))"
        );

        ContentValues values = new ContentValues();
        values.put("username", "admin");
        values.put("password", "123456");
        values.put("email", "admin@qlcoffe.vn");
        values.put("role", Role.ADMIN.value);
        sqLiteDatabase.insert("tb_account", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tb_account");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tb_prdocut");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tb_order");
        onCreate(sqLiteDatabase);
    }

    public boolean checkIdProduct(String idProduct) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectedArgs = {idProduct};

        Cursor cursor = db.query(
                "tb_product",
                null,
                "id = ?",
                selectedArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    public Product getProduct(String idProduct) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectedArgs = {idProduct};

        Cursor cursor = db.query(
                "tb_product",
                null,
                "id = ?",
                selectedArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        if (cursor.getCount() <= 0) {
            return null;
        }
        Product product = new Product(
                cursor.getString(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getString(cursor.getColumnIndexOrThrow("image")),
                cursor.getLong(cursor.getColumnIndexOrThrow("price"))
        );

        cursor.close();
        return product;
    }

    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("image", product.getImage());
        values.put("price", product.getPrice());

        return db.update("tb_product", values, "id = ?", new String[]{product.getId()});
    }

    public long addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", product.getId());
        values.put("name", product.getName());
        values.put("image", product.getImage());
        values.put("price", product.getPrice());

        try {
            long result = db.insert("tb_product", null, values);
            Log.d("SQLite", "insert product success");
            return result;
        } catch (Exception exception) {
            Log.e("SQLite", "insert product error " + exception);
            return -1;
        }
    }

    public List<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM tb_product";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() <= 0) {
            return null;
        }
        while (cursor.moveToNext()) {
            Product account = new Product(
                    cursor.getString(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("image")),
                    cursor.getLong(cursor.getColumnIndexOrThrow("price"))
            );
            products.add(account);
        }
        cursor.close();
        return products;
    }

    public boolean deleteProduct(String idProduct) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete("tb_product", "id = ?", new String[]{idProduct});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d("ClassesHelper", "Error while trying to delete all posts and users");
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public boolean checkUsernameIsExits(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectedArgs = {username};

        Cursor cursor = db.query(
                "tb_account",
                null,
                "username = ?",
                selectedArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    public String getUsernameById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectedArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                "tb_account",
                null,
                "id = ?",
                selectedArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            return cursor.getString(cursor.getColumnIndexOrThrow("username"));
        }
        cursor.close();
        return "";
    }


    public Account login(String username, String password) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String[] selectedArgs = {username, password};

            Cursor cursor = db.query(
                    "tb_account",
                    null,
                    "username = ? AND password = ?",
                    selectedArgs,
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            if (cursor.getCount() <= 0) {
                return null;
            }
            int indexUsername = cursor.getColumnIndex("username");
            if (indexUsername == -1) {

            }
            Account account = new Account(
                    cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("username")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("role"))
            );


            cursor.close();
            return account;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public List<Account> getAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM tb_account";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() <= 0) {
            return null;
        }
        while (cursor.moveToNext()) {
            Account account = new Account(
                    cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("username")),
                    ""
            );
            accounts.add(account);
        }
        cursor.close();
        return accounts;
    }

    public Account register(String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("email", email);
        values.put("password", password);
        values.put("role", Role.USER.value);

        Cursor cursor = db.query("tb_account", null, "username = ?", new String[]{username}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return null;
        }
        cursor.close();

        long result = db.insert("tb_account", null, values);
        if (result == -1) return null;
        return new Account(result, username, "", Role.USER.value);
    }

    public boolean createBill(List<Product> products, String address, String phone, long accountId) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            long payId = System.currentTimeMillis();

            products.forEach(new Consumer<Product>() {
                @Override
                public void accept(Product product) {
                    values.put("id", payId);
                    values.put("account_id", accountId);
                    values.put("product_id", product.getId());
                    values.put("quantity", product.getQuantity());
                    values.put("address", address);
                    values.put("phone", phone);

                    Log.d("CartANH", "quantity = " + product.getQuantity());
                    db.insert("tb_order", null, values);
                }
            });
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public List<OrderHistory> getOrdersByAccountId(long accountId) {
        ArrayList<Long> orders = new ArrayList<>();
        ArrayList<OrderHistory> result = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectedArgs = {String.valueOf(accountId)};

        Cursor cursor = db.query(
                "tb_order",
                null,
                "account_id = ?",
                selectedArgs,
                null,
                null,
                null
        );

        if (cursor.getCount() <= 0) {
            return null;
        }

        while (cursor.moveToNext()) {
            long orderId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            if (!orders.contains(orderId)) {
                orders.add(orderId);
            }
        }
        cursor.close();

        if (!orders.isEmpty()) {
            orders.forEach(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) {
                    String[] selectedArgs01 = {
                            String.valueOf(accountId),
                            String.valueOf(aLong)
                    };
                    Cursor cursor01 = db.query(
                            "tb_order",
                            null,
                            "account_id = ? AND id = ?",
                            selectedArgs01,
                            null,
                            null,
                            null
                    );
                    if (cursor01.getCount() > 0) {
                        OrderHistory orderHistory = new OrderHistory();
                        orderHistory.setUsername(SessionManager.getInstance().getCurrentAccount().getUsername());
                        ArrayList<Product> products = new ArrayList<>();
                        while (cursor01.moveToNext()) {
                            orderHistory.setAddress(
                                    cursor01.getString(cursor01.getColumnIndexOrThrow("address"))
                            );
                            orderHistory.setPhone(
                                    cursor01.getString(cursor01.getColumnIndexOrThrow("phone"))
                            );
                            Product product = getProduct(cursor01.getString(cursor01.getColumnIndexOrThrow("product_id")));
                            product.setQuantity(cursor01.getInt(cursor01.getColumnIndexOrThrow("quantity")));
                            products.add(product);
                        }

                        orderHistory.setProducts(products);

                        cursor01.close();
                        result.add(orderHistory);
                    }
                }
            });

        }

        return result;
    }

    public List<OrderHistory> getOrders() {
        ArrayList<OrderHistory> result = new ArrayList<>();

        HashMap<Long, Long> orders = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "tb_order",
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() <= 0) {
            return null;
        }

        while (cursor.moveToNext()) {
            long orderId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            long accountId = cursor.getLong(cursor.getColumnIndexOrThrow("account_id"));
            if (!orders.containsKey(orderId)) {
                orders.put(orderId, accountId);
            }
        }
        cursor.close();

        if (!orders.isEmpty()) {
            for (Map.Entry<Long, Long> entry : orders.entrySet()) {
                String[] selectedArgs01 = {
                        String.valueOf(entry.getValue()),
                        String.valueOf(entry.getKey())
                };
                Cursor cursor01 = db.query(
                        "tb_order",
                        null,
                        "account_id = ? AND id = ?",
                        selectedArgs01,
                        null,
                        null,
                        null
                );
                if (cursor01.getCount() > 0) {
                    OrderHistory orderHistory = new OrderHistory();

                    orderHistory.setUsername(getUsernameById(entry.getValue()));

                    ArrayList<Product> products = new ArrayList<>();
                    while (cursor01.moveToNext()) {
                        orderHistory.setAddress(
                                cursor01.getString(cursor01.getColumnIndexOrThrow("address"))
                        );
                        orderHistory.setPhone(
                                cursor01.getString(cursor01.getColumnIndexOrThrow("phone"))
                        );
                        Product product = getProduct(cursor01.getString(cursor01.getColumnIndexOrThrow("product_id")));
                        product.setQuantity(cursor01.getInt(cursor01.getColumnIndexOrThrow("quantity")));
                        products.add(product);
                    }

                    orderHistory.setProducts(products);

                    cursor01.close();
                    result.add(orderHistory);
                }
            }
        }

        return result;
    }
}
