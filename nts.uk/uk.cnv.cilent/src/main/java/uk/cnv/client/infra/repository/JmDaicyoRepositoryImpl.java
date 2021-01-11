package uk.cnv.client.infra.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import uk.cnv.client.dom.fileimport.JmDaicyoRepository;
import uk.cnv.client.infra.entity.JmDaicyo;
import uk.cnv.client.infra.repository.base.ErpRepositoryBase;

public class JmDaicyoRepositoryImpl extends ErpRepositoryBase implements JmDaicyoRepository {

	@Override
	public List<JmDaicyo> getAll(int companyCode) {
		String sql = "SELECT * FROM jm_daicyo t WHERE 会社CD=" + companyCode;

		return this.getList(sql, new SelectRequire<JmDaicyo>() {
			@Override
			public JmDaicyo toEntity(ResultSet rs) throws SQLException {
				return JmDaicyo.fromRS(rs);
			}
		});
	}

}
