/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOS;

import DTO.ClienteDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nacho
 */
public class ClienteDAO {

    private static final String SQL_INSERT
            = "INSERT INTO clientes ("
            + "Nombre, Direccion, Telefono"
            + ") VALUES (?, ?, ?)";
    private static final String SQL_SELECT
            = "SELECT idCliente, Nombre, Direccion, Telefono "
            + " FROM clientes where Telefono = ?";
    private static final String SQL_SELECT_ALL
            = "SELECT idCliente, Nombre, Direccion, Telefono "
            + "FROM clientes";
    private static final String SQL_UPDATE
            = "UPDATE clientes SET "
            + "Nombre = ?, Direccion = ?, Telefono = ?"
            + " WHERE "
            + "idCliente = ? ";
    private static final String SQL_DELETE
            = "DELETE FROM clientes "
            + " WHERE "
            + "idCliente = ?";

    /**
     * Método que recibe un objeto ClienteDTO y Connection; que permite
     * insertar en la base de datos los atributos del objeto ClienteDTO
     *
     * @param dto Cliente
     * @param conn conexión
     * @throws SQLException
     */
    public void create(ClienteDTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(SQL_INSERT);

            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getDireccion());
            ps.setString(3, dto.getTelefono());

            ps.executeUpdate();

        } finally {
            cerrar(ps);
            cerrar(conn);
        }
    }
    
    /**
     * Método que selecciona desde la base de datos un registro en el cuál coincidan las claves del objeto dado en el parámetro
     * @param dto Cliente
     * @param conn Conexión
     * @return
     * @throws SQLException 
     */
    public ClienteDTO load(ClienteDTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(SQL_SELECT);
            ps.setString(1, dto.getTelefono());
            rs = ps.executeQuery();
            List results = getResults(rs);
           
            if (results.size() > 0) {
                return (ClienteDTO) results.get(0);
            } else {
                return null;
            }
        } finally {
            cerrar(ps);
            cerrar(rs);
            cerrar(conn);            
        }
    }

       /**
     * Método que selecciona todos los registros de la base de datos y los regresa en forma de lista
     * @param conn Conexión
     * @return List results
     * @throws SQLException 
     */
    public ArrayList<ClienteDTO> loadAll(Connection conn) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(SQL_SELECT_ALL);            
            rs = ps.executeQuery();
            ArrayList<ClienteDTO> results = (ArrayList<ClienteDTO>) getResults(rs);
            ps.executeQuery();
            if (results.size() > 0) {
                return results;
            } else {
                return null;
            }
        } finally {
            cerrar(ps);
            cerrar(rs);
            cerrar(conn);            
        }
    }
    
    /**
     * Método que actualiza en la base de datos un registro si este coincide con la clave dada por el parámetro
     * @param dto Cliente
     * @param conn Conexión
     * @throws SQLException 
     */
    public void update(ClienteDTO dto, Connection conn) throws SQLException{
        PreparedStatement ps = null;       
        try {
            ps = conn.prepareStatement(SQL_UPDATE);             
            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getDireccion());
            ps.setString(3, dto.getTelefono());            
            ps.setString(4, dto.getIdCliente());
            ps.executeUpdate();            
        } finally {
            cerrar(ps);            
            cerrar(conn);            
        }
    }
    
     /**
     * Método que elimina un registro de la base de datos si este coincide con la clave dada por el parámetro
     * @param dto Cliente
     * @param conn Conexión
     * @throws SQLException 
     */
    public void delete(ClienteDTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = null;       
        try {
            ps = conn.prepareStatement(SQL_DELETE); 
            ps.setString(1, dto.getIdCliente());  
            ps.executeUpdate();            
        } finally {
            cerrar(ps);            
            cerrar(conn);            
        }
    }
    
     private List getResults(ResultSet rs) throws SQLException{
        List results = new ArrayList();
        while (rs.next()) {
            ClienteDTO dto = new ClienteDTO();
            dto.setIdCliente(rs.getString("idCliente"));
            dto.setNombre(rs.getString("Nombre"));
            dto.setDireccion(rs.getString("Direccion"));
            dto.setTelefono(rs.getString("Telefono"));
            results.add(dto);
        }
        return results;
    }
     
      /**
     * Método para cerrar el PreparedStatement
     * @param ps
     * @throws SQLException 
     */
    private void cerrar (PreparedStatement ps) throws SQLException{
        if (ps != null) {
            try {
                ps.close();
            } catch(SQLException e) {}
        }
    }
    /**
     * Método para cerrar el ResultSet
     * @param rs 
     */
    private void cerrar (ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch(SQLException e) {}
        }
    }
    /**
     * Método para cerrar la conexión a base de datos
     * @param conn 
     */
    private void cerrar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch(SQLException e) {}
        } 
    }
}
