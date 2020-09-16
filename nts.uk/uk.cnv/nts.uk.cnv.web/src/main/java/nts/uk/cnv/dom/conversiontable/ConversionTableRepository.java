package nts.uk.cnv.dom.conversiontable;

import java.util.List;
import java.util.Optional;

import nts.uk.cnv.dom.service.ConversionInfo;

public interface ConversionTableRepository {

	Optional<ConversionTable> get(ConversionInfo info, String category, String tableName, int recordNo, ConversionSource source);

	//List<ConversionTable> findColumns(ConversionInfo info, String category, int recordNo, String tableName);

	List<ConversionRecord> getRecords(String category, String tableName);

	ConversionRecord getRecord(String category, String tableName, int recordNo);

}
