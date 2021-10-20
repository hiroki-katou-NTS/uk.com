package uk.cnv.client.infra.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JmKihon {
	private int pid;
	private int companyCode;
	private String employeeCode;
	private String profilePhotoFileName;
	private String loginCode;
	private String password;

	public static JmKihon fromRS(ResultSet rs) throws SQLException {
		return new JmKihon(
				rs.getInt("kojin_id"),
				rs.getInt("会社CD"),
				rs.getString("社員CD"),
				rs.getString("j_phot"),
				rs.getString("共通ログインCD"),
				rs.getString("共通パスワード")
			);
	}
}
