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
public class ScvmtMappingFileId implements InsertRequire, TruncateTableRequire {
	/** FILE_ID **/
	private String fileId;

	/** FILE_TYPE **/
	private String fileType;

	/** kojin_id **/
	private int kojin_id;

	@Override
	public PreparedStatement insert(Connection conn) throws SQLException {
		String sql = "INSERT INTO SCVMT_MAPPING_FILE_ID (FILE_ID,FILE_TYPE,kojin_id)"
				+ " VALUES(?,?,?)";
		val ps = conn.prepareStatement(sql);
		ps.setString(1, this.fileId);
		ps.setString(2, this.fileType);
		ps.setInt(3, this.kojin_id);

		return ps;
	}

	@Override
	public PreparedStatement truncateTable(Connection conn) throws SQLException {
		String sql = "TRUNCATE TABLE SCVMT_MAPPING_FILE_ID";
		return conn.prepareStatement(sql);
	}

}
