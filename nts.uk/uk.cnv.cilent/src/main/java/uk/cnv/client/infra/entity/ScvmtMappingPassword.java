package uk.cnv.client.infra.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import uk.cnv.client.infra.repository.base.RepositoryBase.InsertRequire;

@Getter
@AllArgsConstructor
public class ScvmtMappingPassword implements InsertRequire {

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
	public PreparedStatement getPreparedStatement(Connection conn) throws SQLException {
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

}
