package nts.uk.ctx.cloud.operate.infra.tenant.csvimport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.cloud.operate.dom.csvimport.CsvImportRepository;

/**
 * クラウド（Postgres）環境時のみの利用を想定
 * @author ai_muto
 */
@Stateless
public class CsvImportRepositoryImpl extends JpaRepository implements CsvImportRepository {
	private static final String CREATE_TEMP_TABLE_COMMAND = "CREATE TEMPORARY TABLE %s (LIKE %s)";
	private static final String COPY_EXEC_COMMAND = "COPY %s FROM '%s' WITH CSV encoding 'UTF8'";

	private static final String FIND_COLUMNS = "SELECT column_name"
	+ " FROM information_schema.columns"
	+ " WHERE table_schema = 'public' AND UPPER(table_name)=?"
	+ " ORDER BY table_name, ordinal_position";

	private static final String FIND_CONSTRAINTS = "SELECT tc.constraint_name"
			+ " FROM information_schema.table_constraints tc"
			+ " WHERE UPPER(tc.table_name)=?"
			+ " AND tc.constraint_type = 'PRIMARY KEY'";

	@Override
	public void createTempTable(String tempTableName, String tableName) {
		String createTableCommand = String.format(CREATE_TEMP_TABLE_COMMAND, tempTableName, tableName);
		System.out.println("Exec：" + createTableCommand);
		this.jdbcProxy().query(createTableCommand).execute();
	}

	@SneakyThrows
	@Override
	public void exec(String contractCode, String tempTableName, String csvFileName) {
		String copyCommand = String.format(COPY_EXEC_COMMAND, tempTableName, csvFileName);
		System.out.println("Exec：" + copyCommand);
		this.jdbcProxy().query(copyCommand).execute();
	}

	@SneakyThrows
	private List<String> getColumns(String tableName) {
		System.out.println("Exec：" + FIND_COLUMNS + " tableName=" + tableName);
		PreparedStatement ps = this.connection().prepareStatement(FIND_COLUMNS);
		ps.setString(1, tableName);
		ResultSet rs = ps.executeQuery();

		List<String> columns = new ArrayList<>();
		while (rs.next()) {
			columns.add(rs.getString(1));
		}

		return columns;
	}

	@SneakyThrows
	private Optional<String> getContraint(String tableName) {
		System.out.println("Exec：" + FIND_CONSTRAINTS + " tableName=" + tableName);
		PreparedStatement ps = this.connection().prepareStatement(FIND_CONSTRAINTS);
		ps.setString(1, tableName);
		ResultSet rs = ps.executeQuery();

		List<String> results = new ArrayList<>();
		while (rs.next()) {
			results.add(rs.getString(1));
		}

		return results.stream().findFirst();
	}

	@Override
	public void upsert(String tempTableName, String tableName) {

		List<String> columns = getColumns(tableName);
		Optional<String> contraint = getContraint(tableName);

		StringBuilder upsertQuery = new StringBuilder();
		if (contraint.isPresent()) {
			String setSentence = String.join(",", columns.stream()
					.map(col -> col + " = EXCLUDED." + col)
					.collect(Collectors.toList()));
			upsertQuery.append("INSERT INTO " + tableName);
			upsertQuery.append(" (");
			upsertQuery.append("  SELECT " + String.join(",", columns));
			upsertQuery.append("  FROM " + tempTableName);
			upsertQuery.append(" )");
			upsertQuery.append(" ON CONFLICT ON CONSTRAINT " + contraint.get());
			upsertQuery.append(" DO UPDATE SET " + setSentence);
		} else {
			upsertQuery.append("INSERT INTO " + tableName);
			upsertQuery.append(" (");
			upsertQuery.append("  SELECT " + String.join(",", columns));
			upsertQuery.append("  FROM " + tempTableName);
			upsertQuery.append(" )");
		}

		System.out.println("Exec：" + upsertQuery.toString());
		this.jdbcProxy().query(upsertQuery.toString()).execute();

	}
}
