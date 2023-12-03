/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sonrisaspasillos;

import com.mysql.cj.jdbc.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author die_a
 */
public class Conexion {
    static Connection conexion;
    private static String bd="proyecto";
    private static String user="root";
    private static String password="12345";
    private static String host="localhost";
    private static String
    server="jdbc:mysql://"+host+"/"+bd;
    // Método para conectar a la base de datos
    public static void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el controlador de MySQL
            conexion = DriverManager.getConnection(server, user, password);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar a la base de datos");
        }
    }

    // Método para desconectar de la base de datos
    public static void desconectar() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Desconexión exitosa");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error al desconectar de la base de datos");
            }
        }
    }
      // Método para obtener la conexión activa
    public static Connection obtenerConexion() {
        return conexion;
    }
    
    // Método para obtener el total de un pedido específico
    public static double obtenerTotalPedido(int codigoPedido) {
        double total = 0;

        try {
            // Preparar la consulta SQL
            String sql = "SELECT Monto_Total FROM Pedido WHERE Codigo_Pedido = ?";
            try (Connection connection = obtenerConexion();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                // Establecer el parámetro Codigo_Pedido en la consulta preparada
                preparedStatement.setInt(1, codigoPedido);

                // Ejecutar la consulta y obtener el resultado
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Obtener el Monto_Total desde el resultado
                        total = resultSet.getDouble("Monto_Total");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    
    }
     public static double obtenerSubtotalPedido(int codigoPedido) {
        double subtotal = 0;

        try {
            // Preparar la consulta SQL
            String sql = "SELECT SubTotal FROM Pedido WHERE Codigo_Pedido = ?";
            try (Connection connection = obtenerConexion();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                // Establecer el parámetro Codigo_Pedido en la consulta preparada
                preparedStatement.setInt(1, codigoPedido);

                // Ejecutar la consulta y obtener el resultado
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Obtener el SubTotal desde el resultado
                        subtotal = resultSet.getDouble("SubTotal");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subtotal;
    }
     
    public static void actualizarDescuentoPedido(int pedidoId, double nuevoDescuento) {
    try {
        Connection conexion = obtenerConexion();

        // Verificar si ya hay una conexión abierta
        if (conexion == null || conexion.isClosed()) {
            // Si no hay una conexión abierta, crear una nueva conexión
            conectar();
            conexion = obtenerConexion();
        }

        // Preparar la llamada al procedimiento almacenado
        String sql = "CALL ActualizarDescuentoPedido(?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            // Establecer los parámetros
            statement.setInt(1, pedidoId);
            statement.setDouble(2, nuevoDescuento);

            // Ejecutar la actualización
            statement.executeUpdate();
        }

        // Cerrar la conexión si no la obtuvimos de la existente
        if (conexion != null && conexion != obtenerConexion()) {
            conexion.close();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public static void actualizarMedioPagoYEstado(int pedidoId, String nuevoMedioPago, String nuevoEstado) {
    try {
        Connection conexion = obtenerConexion();

        // Verificar si ya hay una conexión abierta
        if (conexion == null || conexion.isClosed()) {
            // Si no hay una conexión abierta, crear una nueva conexión
            conectar();
            conexion = obtenerConexion();
        }

        // Preparar la llamada al procedimiento almacenado
        String sql = "CALL ActualizarMedioPagoYEstado(?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            // Establecer los parámetros
            statement.setInt(1, pedidoId);
            statement.setString(2, nuevoMedioPago);
            statement.setString(3, nuevoEstado);

            // Ejecutar la actualización
            statement.executeUpdate();
        }

        // Cerrar la conexión si no la obtuvimos de la existente
        if (conexion != null && conexion != obtenerConexion()) {
            conexion.close();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static void actualizarCantidadTransaccion(int pedidoId) {
    try {
        Connection conexion = obtenerConexion();

        // Verificar si ya hay una conexión abierta
        if (conexion == null || conexion.isClosed()) {
            // Si no hay una conexión abierta, crear una nueva conexión
            conectar();
            conexion = obtenerConexion();
        }

        // Evaluar la condición antes de ejecutar el procedimiento almacenado
            // Preparar la llamada al procedimiento almacenado
            String sql = "CALL ActualizarCantidadTransaccion(?)";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                // Establecer el parámetro
                statement.setInt(1, pedidoId);

                // Ejecutar el procedimiento almacenado
                statement.execute();
            }

        // Cerrar la conexión si no la obtuvimos de la existente
        if (conexion != null && conexion != obtenerConexion()) {
            conexion.close();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static DefaultTableModel obtenerModeloTransacciones() {
    // Definir las columnas para el modelo de la tabla
    String[] columnas = {"id_transaccion", "id_pedido", "Fecha", "Motivo", "Cantidad"};

    // Crear un modelo de tabla con las columnas
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

    // Realizar la consulta a la base de datos
    String sql = "SELECT id_transaccion, id_pedido, Fecha, Motivo, Cantidad FROM Transacciones";

    try {
        Connection conexion = obtenerConexion();
        PreparedStatement statement = conexion.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        // Iterar sobre los resultados y agregarlos al modelo de la tabla
        while (resultSet.next()) {
            Object[] fila = {
                    resultSet.getInt("id_transaccion"),
                    resultSet.getInt("id_pedido"),
                    resultSet.getDate("Fecha"),
                    resultSet.getString("Motivo"),
                    resultSet.getDouble("Cantidad")
            };
            modelo.addRow(fila);
        }

        // No cerramos la conexión aquí

    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Devolver el modelo
    return modelo;
}
    
    public static void ejecutarProcedimientoDevolucion(int idPedido, String motivo) {
        try {
            // Preparar la llamada al procedimiento almacenado
            String sql = "CALL InsertarDevolucion(?, ?)";
            try (CallableStatement callableStatement = (CallableStatement) obtenerConexion().prepareCall(sql)) {
                // Establecer los parámetros del procedimiento
                callableStatement.setInt(1, idPedido);
                callableStatement.setString(2, motivo);

                // Ejecutar el procedimiento almacenado
                callableStatement.executeUpdate();
                System.out.println("Procedimiento almacenado ejecutado con éxito.");
               
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al ejecutar el procedimiento almacenado");
        }
    }
    
    
}



