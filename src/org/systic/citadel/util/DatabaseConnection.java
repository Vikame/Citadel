package org.systic.citadel.util;

import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection connection;

    private Plugin plugin;
    private String database;

    public DatabaseConnection(Plugin plugin, String database){
        this.plugin = plugin;
        this.database = database;

        createConnection();
    }

    public PreparedStatement prepareStatement(String sql){
        try{
            if(connection == null || connection.isClosed()){
                createConnection();
            }

            if(connection == null || connection.isClosed()) return null;

            return connection.prepareStatement(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    private void createConnection(){
        try{
            if(connection != null && !connection.isClosed()) return;

            Debug.sendDebugMessage("Opening database connection (" + database + ")");

            long start = System.currentTimeMillis();

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/" + database, "root", "7JXuc%tOb#=co@bRIRtXnGO@Xba&QXR1)ur*_W3PgQs6!S^3mcU&n6O@Cu)S$Xci@o5__uOjmVrx9=1yX_kfu8Xw$cXQ5JQgUuNdn8Bc!=cX9Lo31loXC-t7yx45S1XiHiN3-qTZRvDD5rwVPTWuha8r!&57wrQGNUPBiQ5hc@imuVOq1OhH+k-NHtlVfA*nVtt)W2OfNM$ZE@D+$@_l*bPOooh*RV5TS6nD7=bnEMlcUXsi5ilReo3W8%");

            System.out.println("[" + plugin.getName() + "] Connected to MySQL in " + (System.currentTimeMillis()-start) + "ms.");
        }catch(ClassNotFoundException e){
            System.out.println("[" + plugin.getName() + "] Could not connect to MySQL.");
            System.out.println("[" + plugin.getName() + "] -  Driver 'JDBC' does not exist");
        }catch(Exception e){
            System.out.println("[" + plugin.getName() + "] Could not connect to MySQL.");
            System.out.println("[" + plugin.getName() + "] -  Connection Address: 127.0.0.1/" + database);
            System.out.println("[" + plugin.getName() + "] -  Driver: JDBC");
            System.out.println("[" + plugin.getName() + "] -  Extra Arguments: None");
        }
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
