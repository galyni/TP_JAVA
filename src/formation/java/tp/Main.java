package formation.java.tp;

import java.sql.*;

public class Main {

    public static void main(String[] args)
    {
        // SQL Server avec précision de l'instance et du port d'écoute
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=Librairie;integratedSecurity=true;authenticationScheme=nativeAuthentication;";
        // SQL Server avec instance et port d'écoute par défaut
        //String url = "jdbc:sqlserver://localhost;databaseName=structures";
//        String user = "user";
//        String mdp = "";


        Connection connexion = null;
        Statement state = null;
        PreparedStatement ps =  null;
        ResultSet result = null;

        try {
            connexion = DriverManager.getConnection(url);
            state = connexion.createStatement();
            result = state.executeQuery("select * from Editors");
            while(result.next()){
                String Nom = result.getString("Name");
                System.out.printf("Nom : " + Nom);
            }
            result.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (result != null)
                    result.close();
                if (state != null)
                    state.close();
                if (connexion != null)
                    connexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
