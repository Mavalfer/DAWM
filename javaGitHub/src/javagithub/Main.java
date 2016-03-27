package javagithub;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.*;

public class Main {

    public static boolean chkComercio(Statement stmt, String comercio) {
        boolean chk = false;
        try {
            ResultSet rs = stmt.executeQuery("SELECT nombre from MVComercio");
            while (rs.next()){
                if(rs.getString("nombre").equals(comercio)){
                    chk = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chk;
    }

    public static boolean chkFecha(Statement stmt, String comercio) {
        boolean abierto = false;
        LocalDate fecha = LocalDate.now();
        String dia = fecha.getDayOfWeek().toString();  //OJO
        try {
            ResultSet rsID = stmt.executeQuery("SELECT id_mvcomercio FROM MVComercio"
                    + "WHERE nombre = '" + comercio + "'");
            ResultSet rsFecha = stmt.executeQuery("SELECT hInicio, hFIn FROM MVHorario"
                    + "WHERE " + String.valueOf(rsID.getInt("id_comercio"))
                    + "AND dia = '" + dia + "'");
            LocalTime horaLocal = LocalTime.now();
            Time hora = Time.valueOf(horaLocal);
            if (hora.after(rsFecha.getTime("hInicio")) && hora.before(rsFecha.getTime("hFin"))) {
                abierto = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return abierto;
    }

    public static void main(String[] args) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://www.db4free.net:3306/izv_dawm",
                    "izv_dawm", "123456");
            
            String dropTables = "DROP TABLE IF EXISTS MVHorario, MVComercio";
            String sqlMVComercio = "CREATE TABLE MVComercio"
                    + "(id_mvcomercio INT, "
                    + "nombre VARCHAR(50), "
                    + "direccion VARCHAR(50), "
                    + "tipo VARCHAR(50), "
                    + "PRIMARY KEY (id_mvcomercio))";
                     
            String sqlMVHorario = "CREATE TABLE MVHorario"
                    + "(id_mvhorario INT, "
                    + "dia VARCHAR(10), "
                    + "hInicio TIME, "
                    + "hFin TIME, "
                    + "id_mvcomercio INT not null,"
                    + "PRIMARY KEY (id_mvhorario), "
                    + "FOREIGN KEY (id_mvcomercio) REFERENCES MVComercio(id_mvcomercio))";

            String insertc1 = "INSERT INTO MVComercio VALUES (156,'Tienda Molona','Calle Piruleta 3','Industria carnica')";
            String insertc2 = "INSERT INTO MVComercio VALUES (278,'El pollazo','Calle falsa 123','Polleria')";
            String insertc1h1 = "INSERT INTO MVHorario VALUES (11,'Lunes','08:00:00','20:30:00','156')";
            String insertc1h2 = "INSERT INTO MVHorario VALUES (12,'Martes','08:00:00','20:30:00','156')";
            String insertc1h3 = "INSERT INTO MVHorario VALUES (13,'Miércoles','08:00:00','20:30:00','156')";
            String insertc1h4 = "INSERT INTO MVHorario VALUES (14,'Jueves','08:00:00','20:30:00','156')";
            String insertc1h5 = "INSERT INTO MVHorario VALUES (15,'Viernes','08:00:00','20:30:00','156')";
            String insertc1h6 = "INSERT INTO MVHorario VALUES (16,'Sabado','17:00:00','20:30:00','156')";
            String insertc1h7 = "INSERT INTO MVHorario VALUES (17,'Domingo',null,null,'156')";
            String insertc2h1 = "INSERT INTO MVHorario VALUES (21,'Lunes','08:00:00','20:30:00','278')";
            String insertc2h2 = "INSERT INTO MVHorario VALUES (22,'Martes','08:00:00','20:30:00','278')";
            String insertc2h3 = "INSERT INTO MVHorario VALUES (23,'Miércoles','08:00:00','20:30:00','278')";
            String insertc2h4 = "INSERT INTO MVHorario VALUES (24,'Jueves','08:00:00','20:30:00','278')";
            String insertc2h5 = "INSERT INTO MVHorario VALUES (25,'Viernes','08:00:00','20:30:00','278')";
            String insertc2h6 = "INSERT INTO MVHorario VALUES (26,'Sabado','17:00:00','20:30:00','278')";
            String insertc2h7 = "INSERT INTO MVHorario VALUES (27,'Domingo',null,null,'278')";
            Statement stmt = null;
            
            ArrayList<String> inserciones = new ArrayList();
            inserciones.add(insertc1);
            inserciones.add(insertc2);
            inserciones.add(insertc1h1);
            inserciones.add(insertc1h2);
            inserciones.add(insertc1h3);
            inserciones.add(insertc1h4);
            inserciones.add(insertc1h5);
            inserciones.add(insertc1h6);
            inserciones.add(insertc1h7);
            inserciones.add(insertc2h1);
            inserciones.add(insertc2h2);
            inserciones.add(insertc2h3);
            inserciones.add(insertc2h4);
            inserciones.add(insertc2h5);
            inserciones.add(insertc2h6);
            inserciones.add(insertc2h7);

            try {
                stmt = con.createStatement();
                stmt.executeUpdate(dropTables);
                stmt.executeUpdate(sqlMVComercio);
                stmt.executeUpdate(sqlMVHorario);
                for(int i = 0; i < inserciones.size(); i++){
                    stmt.executeUpdate(inserciones.get(i));
                }
                Scanner sc = new Scanner(System.in);
                String comercio;
                do{
                    comercio = sc.nextLine();
                    if (chkComercio(stmt, comercio)){
                        if (chkFecha(stmt, comercio)){
                            System.out.println("Abierto");
                        }else{
                            System.out.println("Cerrado");
                        }
                    }else{
                        System.out.println("Nombre incorrecto");
                    }
                }while(!chkComercio(stmt, comercio));
            /*    ResultSet rs = stmt.executeQuery(insertc1);
                while (rs.next()) {
                    
                }*/
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                stmt.close();
                con.close();
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
}