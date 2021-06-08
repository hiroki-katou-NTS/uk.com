package nts.uk.ctx.exio.dom.input.transfer;

import java.util.List;

import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;

public interface ConversionTableRepository {
	ConversionSource getSource(String groupName);
	List<ConversionTable> get(String groupName, ConversionSource source, ConversionCodeType cct);
}
