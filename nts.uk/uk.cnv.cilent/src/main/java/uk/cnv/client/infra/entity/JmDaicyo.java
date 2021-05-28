package uk.cnv.client.infra.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JmDaicyo {
	private int pid;
	private int companyCode;
	private String docFileName;

	public static JmDaicyo fromRS(ResultSet rs) throws SQLException {
		return new JmDaicyo(
				rs.getInt("kojin_id"),
				rs.getInt("会社CD"),
				rs.getString("j_densisyorui")
			);
	}
}
