/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Colecao;

/**
 *
 * @author lefoly
 */
public class ColecaoDaoJDBC implements InterfaceDao<Colecao> {

    private final Connection conn;

    public ColecaoDaoJDBC() throws Exception {
        this.conn = ConnFactory.getConnection();
    }

    @Override
public void incluir(Colecao entidade) throws Exception {
    PreparedStatement ps = null; 
    try {
        ps = conn.prepareStatement("INSERT INTO pedidos (nome, equipamento, requerente, dataDoPedido, descricao, localImagem) VALUES(?, ?, ?, ?, ?, ?)");
        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getEquipamento());
        ps.setString(3, entidade.getRequerente());

        if (entidade.getDataDoPedido() != null) {
            ps.setDate(4, java.sql.Date.valueOf(entidade.getDataDoPedido())); 
        } else {
            ps.setNull(4, java.sql.Types.DATE); 
        }

        ps.setString(5, entidade.getDescricao());
        ps.setString(6, entidade.getLocalImagem());

        ps.executeUpdate(); 
    } finally {
        if (ps != null) {
            ps.close(); 
        }
        if (conn != null) {
            conn.close();
        }
    }
}


   @Override
public void editar(Colecao entidade) throws Exception {
    PreparedStatement ps = null; 
    try {
        ps = conn.prepareStatement("UPDATE pedidos SET nome=?, equipamento=?, requerente=?, dataDoPedido=?, descricao=?, localImagem=?, status=? WHERE id=?");
        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getEquipamento());
        ps.setString(3, entidade.getRequerente());
        
        if (entidade.getDataDoPedido() != null) {
            ps.setDate(4, java.sql.Date.valueOf(entidade.getDataDoPedido())); 
        } else {
            ps.setNull(4, java.sql.Types.DATE); 
        }
        
        ps.setString(5, entidade.getDescricao());
        ps.setString(6, entidade.getLocalImagem());
        ps.setBoolean(7, entidade.isStatus());
        ps.setInt(8, entidade.getId());
        
        ps.executeUpdate();
    } finally {
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}


    @Override
    public void excluir(Colecao entidade) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM pedidos WHERE id=?");
            ps.setInt(1, entidade.getId());
            ps.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public Colecao pesquisarPorId(int id) throws Exception {
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        ps = conn.prepareStatement("SELECT * FROM pedidos WHERE id = ?");
        ps.setInt(1, id);

        rs = ps.executeQuery();
        if (rs.next()) {
            Colecao j = new Colecao();
            j.setId(rs.getInt("id"));
            j.setNome(rs.getString("nome"));
            j.setEquipamento(rs.getString("equipamento"));
            j.setRequerente(rs.getString("requerente"));
            
            
            Date sqlDate = rs.getDate("dataDoPedido");
            if (sqlDate != null) {
                j.setDataDoPedido(sqlDate.toLocalDate());
            }

            j.setDescricao(rs.getString("descricao"));
            j.setLocalImagem(rs.getString("localImagem"));
            j.setStatus(rs.getBoolean("status"));
            
            return j;
        }

        return null; 
    } finally {
        if (rs != null) {
            rs.close(); 
        }
        if (ps != null) {
            ps.close(); 
        }
    }
}


   @Override
    public List<Colecao> listar(String param) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (param.equals("")) {
                ps = conn.prepareStatement("SELECT * FROM pedidos");
            } else {
                ps = conn.prepareStatement("SELECT * FROM pedidos WHERE nome LIKE ?");
                String likeParam = "%" + param + "%";
                ps.setString(1, likeParam);
            }

            rs = ps.executeQuery();
            List<Colecao> lista = new ArrayList<>();
            while (rs.next()) {
                Colecao j = new Colecao();
                j.setId(rs.getInt("id"));
                j.setNome(rs.getString("nome"));
                j.setEquipamento(rs.getString("equipamento"));
                j.setRequerente(rs.getString("requerente"));

                Date sqlDate = rs.getDate("dataDoPedido");
                if (sqlDate != null) {
                    j.setDataDoPedido(sqlDate.toLocalDate());
                }

                j.setDescricao(rs.getString("descricao"));
                j.setLocalImagem(rs.getString("localImagem"));
                j.setStatus(rs.getBoolean("status"));
                lista.add(j);
            }
            return lista;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
}

}
 
