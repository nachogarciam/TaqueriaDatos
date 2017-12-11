    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOS;

import DTO.ClienteDTO;
import DTO.PedidoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nacho
 */
public class PedidoDAO {

    private static final String SQL_INSERT
            = "INSERT INTO pedidos ("
            + "idCliente,Telefono, Orden, Fecha, Entregado"
            + ") VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT
            = "SELECT idPedido, idCliente, Telefono, Orden, Fecha, Entregado"
            + " FROM pedidos where Telefono = ?";
    private static final String SQL_SELECT_ALL
            = "SELECT idPedido, Telefono, Orden, Fecha, Entregado "
            + "FROM pedidos";
    private static final String SQL_SELECT_ENTREGADO
            = "SELECT idPedido, Telefono, Orden, Fecha, Entregado "
            + "FROM pedidos where Entregado= ?";

    private static final String SQL_UPDATE
            = "UPDATE pedidos SET "
            + "Orden = ?, Entregado = ?, idRepartidor = ? "
            + " WHERE "
            + "Telefono = ? ";
    private static final String SQL_DELETE
            = "DELETE FROM pedidos "
            + " WHERE "
            + "Telefono = ?";

    public void create(PedidoDTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = null;
        try {
//            System.out.println("qweqwe");
            ps = conn.prepareStatement(SQL_INSERT);

            ps.setInt(1, Integer.parseInt(dto.getCliente().getIdCliente()));
            ps.setString(2, dto.getCliente().getTelefono());
            ps.setString(3, dto.getOrden());
            ps.setTimestamp(4, dto.getFecha());
            ps.setInt(5, 0);

            ps.executeUpdate();

        } finally {
            cerrar(ps);
            cerrar(conn);
        }
    }

    public ArrayList<PedidoDTO> loadAll(Connection conn) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();
            ArrayList<PedidoDTO> results = (ArrayList<PedidoDTO>) getResults(rs);
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

    public PedidoDTO load(PedidoDTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(SQL_SELECT);
            ps.setString(1, dto.getTelefono());
            rs = ps.executeQuery();
            List results = getResults(rs);

            if (results.size() > 0) {
                return (PedidoDTO) results.get(0);
            } else {
                return null;
            }
        } finally {
            cerrar(ps);
            cerrar(rs);
            cerrar(conn);
        }
    }

    public PedidoDTO loadEntregado(PedidoDTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(SQL_SELECT_ENTREGADO);
            ps.setBoolean(1, dto.isEntregado());
            rs = ps.executeQuery();
            List results = getResults(rs);

            if (results.size() > 0) {
                return (PedidoDTO) results.get(0);
            } else {
                return null;
            }
        } finally {
            cerrar(ps);
            cerrar(rs);
            cerrar(conn);
        }
    }

    public void update(PedidoDTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(SQL_UPDATE);
            ps.setString(1, dto.getOrden());
            ps.setBoolean(2, dto.isEntregado());
            ps.setString(3, dto.getRepartidor().getNombre());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(conn);
        }
    }

    public void delete(PedidoDTO dto, Connection conn) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setString(1, dto.getTelefono());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(conn);
        }
    }

    private List getResults(ResultSet rs) throws SQLException {
        List results = new ArrayList();
        while (rs.next()) {
            PedidoDTO dto = new PedidoDTO();
            dto.id = (rs.getInt("idPedido"));
            dto.cliente = (new ClienteDTO(rs.getString("Telefono")));
            dto.setOrden(rs.getString("Orden"));
            dto.setFecha(rs.getTimestamp("Fecha"));
            dto.entregado = rs.getBoolean("Entregado");
            results.add(dto);
        }
        return results;
    }

    /**
     * Método para cerrar el PreparedStatement
     *
     * @param ps
     * @throws SQLException
     */
    private void cerrar(PreparedStatement ps) throws SQLException {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
            }
        }
    }

    /**
     * Método para cerrar el ResultSet
     *
     * @param rs
     */
    private void cerrar(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
    }

    /**
     * Método para cerrar la conexión a base de datos
     *
     * @param conn
     */
    private void cerrar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }
}
