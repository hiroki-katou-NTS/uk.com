package uk.cnv.client.infra.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import uk.cnv.client.infra.entity.JmKihon;
import uk.cnv.client.infra.repository.base.ErpRepositoryBase;

public class JmKihonRepositoryImpl extends ErpRepositoryBase {

	public List<JmKihon> findAll() throws SQLException {
		String sql = "SELECT * FROM jm_kihon t";

		return this.getList(sql, new SelectRequire<JmKihon>() {
			@Override
			public JmKihon toEntity(ResultSet rs) throws SQLException {
				return JmKihon.fromRS(rs);
			}
		});
	}


}
