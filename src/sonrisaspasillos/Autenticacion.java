/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sonrisaspasillos;

/**
 *
 * @author die_a
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Autenticacion {
    public static String autenticarUsuario(String nombreUsuario, String contrasena) {
        Conexion.conectar(); // Conectar a la base de datos usando tu clase Conexion

        try {
            // Utilizar la conexión establecida por la clase Conexion
            Connection connection = Conexion.obtenerConexion();

            String query = "SELECT rol FROM Usuario WHERE nombreUsuario = ? AND contraseña = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nombreUsuario);
                preparedStatement.setString(2, contrasena);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("rol");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //Conexion.desconectar(); // Desconectar al finalizar
        }

        return null; // Autenticación fallida
    }
}

