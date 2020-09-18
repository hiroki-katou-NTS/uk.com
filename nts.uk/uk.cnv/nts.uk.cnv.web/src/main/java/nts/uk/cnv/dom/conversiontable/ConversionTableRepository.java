package nts.uk.cnv.dom.conversiontable;

import java.util.Optional;

import nts.uk.cnv.dom.service.ConversionInfo;

public interface ConversionTableRepository {

	Optional<ConversionTable> get(ConversionInfo info, String category, String tableName, int recordNo, ConversionSource source);
}
