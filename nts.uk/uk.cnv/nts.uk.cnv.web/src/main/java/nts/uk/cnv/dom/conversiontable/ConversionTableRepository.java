package nts.uk.cnv.dom.conversiontable;

import java.util.List;
import java.util.Optional;

import nts.uk.cnv.dom.service.ConversionInfo;

public interface ConversionTableRepository {

	Optional<ConversionTable> get(ConversionInfo info, String category);

	List<OneColumnConversion> findColumns(String tableName);

}
