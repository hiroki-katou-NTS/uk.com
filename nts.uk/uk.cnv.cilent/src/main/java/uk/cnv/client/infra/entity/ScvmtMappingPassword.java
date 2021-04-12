package uk.cnv.client.infra.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import uk.cnv.client.infra.repository.base.RepositoryBase.InsertRequire;
import uk.cnv.client.infra.repository.base.RepositoryBase.TruncateTableRequire;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScvmtMappingPassword implements InsertRequire, TruncateTableRequire {

	/** kojin_id **/
	private int pid;

	/** 会社CD **/
	private int companyCode;

	/** 社員CD **/
	private String employeeCode;

	/** ユーザID **/
	private String userId;

	/** パスワード **/
	private String password;

	@Override
	public PreparedStatement insert(Connection conn) throws SQLException {
		String sql = "INSERT INTO SCVMT_MAPPING_PASSWORD (kojin_id,会社CD,社員CD,USER_ID,PASSWORD)"
				+ " VALUES(?,?,?,?,?)";
		val ps = conn.prepareStatement(sql);
		ps.setInt(1, this.pid);
		ps.setInt(2, this.companyCode);
		ps.setString(3, this.employeeCode);
		ps.setString(4, this.userId);
		ps.setString(5, this.password);

		return ps;
	}

	@Override
	public PreparedStatement truncateTable(Connection conn) throws SQLException {
		String sql = "TRUNCATE TABLE SCVMT_MAPPING_PASSWORD";
		return conn.prepareStatement(sql);
	}

}
