package nts.uk.ctx.exio.dom.input.transfer;

import java.util.List;

import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;

public interface ConversionTableRepository {
	ConversionSource getSource(int groupId);
	List<ConversionTable> get(int groupId, ConversionSource source, ConversionCodeType cct);
}
