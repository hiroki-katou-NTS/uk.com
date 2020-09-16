package nts.uk.cnv.dom.conversiontable;

import nts.uk.cnv.dom.service.ConversionInfo;

public interface ConversionSourcesRepository {

	ConversionSource get(ConversionInfo info, String sourceId);

}
