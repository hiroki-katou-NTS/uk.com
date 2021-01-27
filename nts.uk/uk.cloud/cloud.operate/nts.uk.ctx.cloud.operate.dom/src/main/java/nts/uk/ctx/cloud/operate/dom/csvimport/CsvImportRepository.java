package nts.uk.ctx.cloud.operate.dom.csvimport;

public interface CsvImportRepository {

	void createTempTable(String tempTableName, String tableName);
	void exec(String tenantCode, String tempTableName, String csvFileName);
	void upsert(String tempTableName, String tableName);
}
