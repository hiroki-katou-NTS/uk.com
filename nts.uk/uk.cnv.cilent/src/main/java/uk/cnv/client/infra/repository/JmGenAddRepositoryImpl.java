package uk.cnv.client.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import uk.cnv.client.LogManager;
import uk.cnv.client.dom.fileimport.JmGenAddRepository;
import uk.cnv.client.infra.entity.JmGenAdd;
import uk.cnv.client.infra.repository.base.ErpRepositoryBase;

public class JmGenAddRepositoryImpl extends ErpRepositoryBase implements JmGenAddRepository {

	@Override
	public List<JmGenAdd> findAll(int companyCode) throws SQLException {
		String sql = "SELECT * FROM jm_genadd t WHERE 会社CD=?";

		PreparedStatement ps = null;
		try {
			ps = this.connection().prepareStatement(sql);
			ps.setInt(1, companyCode);
		} catch (SQLException e) {
			LogManager.err(e);
		}

		return this.getList(ps, new SelectRequire<JmGenAdd>() {
			@Override
			public JmGenAdd toEntity(ResultSet rs) throws SQLException {
				return JmGenAdd.fromRS(rs);
			}
		});
	}

}
