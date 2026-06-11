/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.terra_snack;

import com.mycompany.terra_snack.database.DatabaseConnection;
import java.sql.Connection;

/**
 *
 * @author ftih_28
 */
public class Terra_Snack {

    public static void main(String[] args) {

        Connection conn =
                DatabaseConnection.getConnection();

        if (conn != null) {
            System.out.println("Database terhubung.");
        } else {
            System.out.println("Database tidak terhubung.");
        }
    }
}
