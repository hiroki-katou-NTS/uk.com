package uk.cnv.client.infra.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import uk.cnv.client.dom.fileimport.JinKaisyaMRepository;
import uk.cnv.client.infra.entity.JinKaisyaM;
import uk.cnv.client.infra.repository.base.ErpRepositoryBase;

public class JpaJinKaisyaMRepository extends ErpRepositoryBase implements JinKaisyaMRepository {

	@Override
	public List<JinKaisyaM> getAll() {
		String sql = "SELECT * FROM jinkaisya_m";

		List<JinKaisyaM> result;
		result = this.getList(sql, new SelectRequire<JinKaisyaM>() {
			@Override
			public JinKaisyaM toDomain(ResultSet rs) throws SQLException{
				return JinKaisyaM.fromRS(rs);
			}
		});

		return result;
	}


}
