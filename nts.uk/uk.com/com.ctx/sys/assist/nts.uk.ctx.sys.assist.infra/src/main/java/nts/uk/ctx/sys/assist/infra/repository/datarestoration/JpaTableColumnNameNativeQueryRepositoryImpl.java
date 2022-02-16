package nts.uk.ctx.sys.assist.infra.repository.datarestoration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.TableColumnNameNativeQueryRepository;

@Stateless
public class JpaTableColumnNameNativeQueryRepositoryImpl extends JpaRepository
		implements TableColumnNameNativeQueryRepository {
	private static final String GET_COLUMN_QUERY = "select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = ?tableName";

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTableColumnName(String tableName) {
		Connection connection = this.getEntityManager().unwrap(Connection.class);
		try {
			String dbType = connection.getMetaData().getDatabaseProductName();
			if (dbType.equals("PostgreSQL")) {
				tableName = tableName.toLowerCase();
			}
			return (List<String>) this.getEntityManager().createNativeQuery(GET_COLUMN_QUERY).setParameter("tableName", tableName)
					.getResultList().stream().map(data -> String.valueOf(data).toUpperCase()).collect(Collectors.toList());
		} catch (SQLException e) {
			return Collections.emptyList();
		}
	}
}
