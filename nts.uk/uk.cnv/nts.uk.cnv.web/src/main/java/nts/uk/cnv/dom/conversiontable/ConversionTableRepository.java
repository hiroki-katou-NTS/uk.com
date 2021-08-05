package nts.uk.cnv.dom.conversiontable;

import java.util.List;
import java.util.Optional;

import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.cnv.core.dom.conversiontable.OneColumnConversion;

public interface ConversionTableRepository {

	Optional<ConversionTable> get(ConversionInfo info, String category, String tableName, int recordNo, ConversionSource source, boolean isRemoveDuplicate);

	Optional<OneColumnConversion> findColumnConversion(ConversionInfo info, String category, String table, int recordNo, String targetColumn, Join sourceJoin);

	List<OneColumnConversion> find(String category, String table, int recordNo);

	boolean isExists(String category, String table, int recordNo, String targetColumn);

	void insert(String category, String table, int recordNo, OneColumnConversion domain);

	void delete(String category, String table, int recordNo, String targetColumn);
}
