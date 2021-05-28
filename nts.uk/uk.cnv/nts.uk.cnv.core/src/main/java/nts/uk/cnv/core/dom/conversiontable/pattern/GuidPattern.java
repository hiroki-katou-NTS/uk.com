package nts.uk.cnv.core.dom.conversiontable.pattern;

import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.SelectSentence;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;

public class GuidPattern extends ConversionPattern {

	public GuidPattern(ConversionInfo info) {
		super(info);
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		conversionSql.getSelect().add(
				SelectSentence.createNotFormat("", info.getDatebaseType().spec().newUuid())
			);
		return conversionSql;
	}

}
