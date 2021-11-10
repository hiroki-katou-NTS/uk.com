package uk.cnv.client.infra.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JinKaisyaM {
	private int companyCode;
	private String profilePhotePath;
	private String mapPhotePath;
	private String documentPath;
	private String uploadFilesPath;

	public static JinKaisyaM fromRS(ResultSet rs) throws SQLException {
		return new JinKaisyaM(
				rs.getInt("会社CD"),
				rs.getString("j_kaophotentry"),
				rs.getString("j_mapentry"),
				rs.getString("j_densientry"),
				rs.getString("j_uploadentry")
			);
	}

}
