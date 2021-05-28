package uk.cnv.client.infra.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import uk.cnv.client.infra.entity.JinKaisyaM;
import uk.cnv.client.infra.repository.base.ErpRepositoryBase;

public class JinKaisyaMRepositoryImpl extends ErpRepositoryBase {

	public List<JinKaisyaM> getAll() throws SQLException {
		String sql = "SELECT * FROM jinkaisya_m";

		List<JinKaisyaM> result;
		result = this.getList(sql, new SelectRequire<JinKaisyaM>() {
			@Override
			public JinKaisyaM toEntity(ResultSet rs) throws SQLException{
				return JinKaisyaM.fromRS(rs);
			}
		});

		return result;
	}


}
