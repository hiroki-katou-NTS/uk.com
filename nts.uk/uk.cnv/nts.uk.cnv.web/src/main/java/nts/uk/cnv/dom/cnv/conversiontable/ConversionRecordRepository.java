package nts.uk.cnv.dom.cnv.conversiontable;

import java.util.List;

public interface ConversionRecordRepository {
	List<ConversionRecord> getRecords(String category, String tableName);

	ConversionRecord getRecord(String category, String tableName, int recordNo);

	boolean isExists(String category, String tableName, int recordNo);

	void delete(String category, String tableName, int recordNo);

	void insert(ConversionRecord conversionRecord);

	void update(ConversionRecord domain);

	void swap(String category, String table, int recordNo1, int recordNo2);

}
