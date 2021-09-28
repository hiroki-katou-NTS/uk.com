package uk.cnv.client.infra.entity;


import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JmGenAdd {
	private int pid;
	private int companyCode;
	private String mapFileName;

	public static JmGenAdd fromRS(ResultSet rs) throws SQLException {
		return new JmGenAdd(
				rs.getInt("kojin_id"),
				rs.getInt("会社CD"),
				rs.getString("j_gen_map")
			);
	}
}
