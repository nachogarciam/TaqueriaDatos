/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import DTO.ClienteDTO;
import DTO.PedidoDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nacho
 */
public interface IPersistencia {

    public void crear(ClienteDTO dto) throws SQLException;

    public ArrayList<ClienteDTO> listarClientes() throws SQLException;

    public ClienteDTO leer(ClienteDTO dto) throws SQLException;

    public void actualiza(ClienteDTO dto) throws SQLException;

    public void elimina(ClienteDTO dto) throws SQLException;

    public void crear(PedidoDTO dto) throws SQLException;

    public ArrayList<PedidoDTO> listarPedidos() throws SQLException;

    public PedidoDTO leer(PedidoDTO dto) throws SQLException;

    public void actualiza(PedidoDTO dto) throws SQLException;

    public void elimina(PedidoDTO dto) throws SQLException;
//
//    public void crear(RepartidorDTO dto) throws SQLException;
//
//    public ArrayList<ClienteDTO> listarRepartidores() throws SQLException;
//
//    public RepartidorDTO leer(RepartidorDTO dto) throws SQLException;
//
//    public void actualiza(RepartidorDTO dto) throws SQLException;
//
//    public void elimina(RepartidorDTO dto) throws SQLException;

}
