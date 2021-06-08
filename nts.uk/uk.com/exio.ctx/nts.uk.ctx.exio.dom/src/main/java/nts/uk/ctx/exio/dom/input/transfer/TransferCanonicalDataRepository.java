package nts.uk.ctx.exio.dom.input.transfer;

import java.util.List;

import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;

public interface TransferCanonicalDataRepository {

	int execute(List<ConversionSQL> conversionSqls);
}
