package nts.uk.cnv.dom.conversiontable;

import java.util.Optional;

import nts.uk.cnv.dom.service.ConversionInfo;

public interface ConversionTableRepository {

	Optional<ConversionTable> get(ConversionInfo info, String category, String tableName, int recordNo, ConversionSource source);

	boolean isExists(String category, String table, int recordNo, String targetColumn);

	void insert(String category, String table, int recordNo, OneColumnConversion domain);

	void delete(String category, String table, int recordNo, String targetColumn);
}
