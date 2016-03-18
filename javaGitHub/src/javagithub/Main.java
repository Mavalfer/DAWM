package javagithub;


import java.sql.*;



public class Main {

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
            String insertc1h3 = "INSERT INTO MVHorario VALUES (13,'Miercoles','08:00:00','20:30:00','156')";
            String insertc1h4 = "INSERT INTO MVHorario VALUES (14,'Jueves','08:00:00','20:30:00','156')";
            String insertc1h5 = "INSERT INTO MVHorario VALUES (15,'Viernes','08:00:00','20:30:00','156')";
            String insertc1h6 = "INSERT INTO MVHorario VALUES (16,'Sabado','17:00:00','20:30:00','156')";
            String insertc1h7 = "INSERT INTO MVHorario VALUES (17,'Domingo',null,null,'156')";
            String insertc2h1 = "INSERT INTO MVHorario VALUES (21,'Lunes','08:00:00','20:30:00','278')";
            String insertc2h2 = "INSERT INTO MVHorario VALUES (22,'Martes','08:00:00','20:30:00','278')";
            String insertc2h3 = "INSERT INTO MVHorario VALUES (23,'Miercoles','08:00:00','20:30:00','278')";
            String insertc2h4 = "INSERT INTO MVHorario VALUES (24,'Jueves','08:00:00','20:30:00','278')";
            String insertc2h5 = "INSERT INTO MVHorario VALUES (25,'Viernes','08:00:00','20:30:00','278')";
            String insertc2h6 = "INSERT INTO MVHorario VALUES (26,'Sabado','17:00:00','20:30:00','278')";
            String insertc2h7 = "INSERT INTO MVHorario VALUES (27,'Domingo',null,null,'278')";
            Statement stmt = null;

            try {
                stmt = con.createStatement();
                stmt.executeUpdate(dropTables);
                stmt.executeUpdate(sqlMVComercio);
                stmt.executeUpdate(sqlMVHorario);
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