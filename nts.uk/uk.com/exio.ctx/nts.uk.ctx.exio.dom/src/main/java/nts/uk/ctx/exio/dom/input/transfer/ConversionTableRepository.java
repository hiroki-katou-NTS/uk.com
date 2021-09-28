package nts.uk.ctx.exio.dom.input.transfer;

import java.util.List;

import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionRecord;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;

public interface ConversionTableRepository {
	ConversionSource getSource(String domainName);
	List<ConversionRecord> getRecords(String category, String tableName);
	List<ConversionTable> get(String domainName, ConversionSource source, ConversionCodeType cct);
}
