/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sonrisaspasillos;

/**
 *
 * @author die_a
 */
public class SonrisasPasillos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Llamamos al método conectar de la clase Conexion
        String nombreUsuario = "cajero";
        String contrasena = "contraseña_del_cajero";

        // Realizar autenticación
        String rol = Autenticacion.autenticarUsuario(nombreUsuario, contrasena);

        if (rol != null) {
            System.out.println("Autenticación exitosa. Rol: " + rol);
            // Aquí puedes realizar acciones específicas según el rol
            if (rol.equals("cajero")) {
                // Realizar acciones específicas para cajeros
                System.out.println("Bienvenido Cajero");
            } else {
                // Otros roles...
            }
        } else {
            System.out.println("Autenticación fallida. Usuario o contraseña incorrectos.");
        }
    }
}
    
