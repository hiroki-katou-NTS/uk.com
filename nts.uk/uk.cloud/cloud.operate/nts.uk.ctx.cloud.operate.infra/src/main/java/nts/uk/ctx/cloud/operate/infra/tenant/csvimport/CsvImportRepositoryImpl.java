package nts.uk.ctx.cloud.operate.infra.tenant.csvimport;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.cloud.operate.dom.csvimport.CsvImportRepository;

/**
 * クラウド（Postgres）環境時のみの利用を想定
 * @author ai_muto
 */
@Stateless
public class CsvImportRepositoryImpl extends JpaRepository implements CsvImportRepository {
	private static final String CREATE_TEMP_TABLE_COMMAND = "CREATE TEMPORARY TABLE %s (LIKE %s)";
	private static final String COPY_EXEC_COMMAND = "PSQL \\COPY %s FROM %s WITH CSV encoding 'SJIS'";

	private static final String FIND_COLUMNS = "SELECT column_name"
			+ " FROM information_schema.columns"
			+ " WHERE table_name = :tableName"
			+ "ORDER BY ordinal_position";

	private static final String FIND_CONSTRAINTS = "SELECT tc.constraint_name"
			+ " FROM information_schema.table_constraints tc"
			+ " WHERE tc.table_name = :tableName"
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
		String copyCommand = this.connection().getMetaData().getURL().replace("jdbc:", "")
				+ " "
				+ String.format(COPY_EXEC_COMMAND, tempTableName, csvFileName);
		System.out.println("Exec：" + copyCommand);

		val meta = this.connection().getMetaData();
		execute(copyCommand);
	}

	@Override
	public void upsert(String tempTableName, String tableName) {

		List<String> columns = this.queryProxy().query(FIND_COLUMNS, String.class)
				.setParameter("tableName", tableName)
				.getList();
		String contraint = this.queryProxy().query(FIND_CONSTRAINTS, String.class)
				.setParameter("tableName", tableName)
				.getSingle().orElse(null);

		StringBuilder upsertQuery = new StringBuilder();
		if (contraint != null) {
			String setSentence = String.join(",", columns.stream()
					.map(col -> "SET " + col + " = EXCLUDED." + col)
					.collect(Collectors.toList()));
			upsertQuery.append("INSERT INTO " + tableName);
			upsertQuery.append(" VALUES ");
			upsertQuery.append(" (");
			upsertQuery.append("  SELECT " + String.join(",", columns));
			upsertQuery.append("  FROM " + tempTableName);
			upsertQuery.append(" )");
			upsertQuery.append(" ON CONFLICT ON CONSTRAINT " + contraint);
			upsertQuery.append(" DO UPDATE " + setSentence);
		} else {
			upsertQuery.append("INSERT INTO " + tableName);
			upsertQuery.append(" VALUES ");
			upsertQuery.append(" (");
			upsertQuery.append("  SELECT " + String.join(",", columns));
			upsertQuery.append("  FROM " + tempTableName);
			upsertQuery.append(" )");
		}

		System.out.println("Exec：" + upsertQuery.toString());
		this.jdbcProxy().query(upsertQuery.toString()).execute();

	}

	private int execute(String command) throws IOException, InterruptedException {
		Process p = null;
		p = Runtime.getRuntime().exec(command);
		p.waitFor();

		return p.exitValue();
	}

}
