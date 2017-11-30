/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import DAOS.*;
import DTO.ClienteDTO;
import DTO.PedidoDTO;
import Interfaz.IPersistencia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nacho
 */
public class Persistencia implements IPersistencia {

    private Connection conn;
    private ClienteDAO cliente;
    private PedidoDAO pedido;
//    private RepartidorDAO repartidor;

    public Persistencia() {
        String user = "root";
        String pwd = "";
        String url = "jdbc:mysql://localhost:3306/TaqueriaZacanta";
        String mySqlDriver = "com.mysql.jdbc.Driver";

        try {
            Class.forName(mySqlDriver);
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cliente = new ClienteDAO();
        pedido = new PedidoDAO();
    }

    @Override
    public void crear(ClienteDTO dto) throws SQLException {
        cliente.create(dto, conn);
    }

    @Override
    public ArrayList<ClienteDTO> listarClientes() throws SQLException {
        return cliente.loadAll(conn);
    }

    @Override
    public ClienteDTO leer(ClienteDTO dto) throws SQLException {
        return cliente.load(dto, conn);
    }

    @Override
    public void actualiza(ClienteDTO dto) throws SQLException {
        cliente.update(dto, conn);
    }

    @Override
    public void elimina(ClienteDTO dto) throws SQLException {
        cliente.delete(dto, conn);
    }

    @Override
    public void crear(PedidoDTO dto) throws SQLException {
        pedido.create(dto, conn);
    }

    @Override
    public ArrayList<PedidoDTO> listarPedidos() throws SQLException {
        return pedido.loadAll(conn);
    }

    @Override
    public PedidoDTO leer(PedidoDTO dto) throws SQLException {
        return pedido.load(dto, conn);
    }

    @Override
    public void actualiza(PedidoDTO dto) throws SQLException {
        pedido.update(dto, conn);
    }

    @Override
    public void elimina(PedidoDTO dto) throws SQLException {
        pedido.delete(dto, conn);
    }

}
