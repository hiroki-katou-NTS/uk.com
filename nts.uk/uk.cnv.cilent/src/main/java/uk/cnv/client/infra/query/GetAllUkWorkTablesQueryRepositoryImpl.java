package uk.cnv.client.infra.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import uk.cnv.client.infra.repository.UkWorkTableDto;
import uk.cnv.client.infra.repository.base.UkRepositoryBase;

public class GetAllUkWorkTablesQueryRepositoryImpl extends UkRepositoryBase{
	private static final String QUERY = ""
			+ "SELECT t.name, c.name, typ.name"
			+ " FROM sys.tables t"
			+ " LEFT OUTER JOIN sys.syscolumns c ON t.object_id = c.id"
			+ " INNER JOIN sys.systypes typ ON c.xtype = typ.xtype AND c.xusertype = typ.xusertype"
			+ " ORDER BY t.name, c.colid";

	public List<UkWorkTableDto> find() throws SQLException {
		return this.getList(QUERY, new SelectRequire<UkWorkTableDto>() {
			@Override
			public UkWorkTableDto toEntity(ResultSet rs) throws SQLException {
				return new UkWorkTableDto(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3));
			}
		});
	}
}
