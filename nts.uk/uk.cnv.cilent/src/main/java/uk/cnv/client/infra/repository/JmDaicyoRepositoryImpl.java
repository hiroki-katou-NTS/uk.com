package uk.cnv.client.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import uk.cnv.client.LogManager;
import uk.cnv.client.dom.fileimport.JmDaicyoRepository;
import uk.cnv.client.infra.entity.JmDaicyo;
import uk.cnv.client.infra.repository.base.ErpRepositoryBase;

public class JmDaicyoRepositoryImpl extends ErpRepositoryBase implements JmDaicyoRepository {

	@Override
	public List<JmDaicyo> getAll(int companyCode) throws SQLException {
		String sql = "SELECT * FROM jm_daicyo t WHERE 会社CD=?";

		PreparedStatement ps = null;
		try {
			ps = this.connection().prepareStatement(sql);
			ps.setInt(1, companyCode);
		} catch (SQLException e) {
			LogManager.err(e);
		}

		return this.getList(ps, new SelectRequire<JmDaicyo>() {
			@Override
			public JmDaicyo toEntity(ResultSet rs) throws SQLException {
				return JmDaicyo.fromRS(rs);
			}
		});
	}

}
