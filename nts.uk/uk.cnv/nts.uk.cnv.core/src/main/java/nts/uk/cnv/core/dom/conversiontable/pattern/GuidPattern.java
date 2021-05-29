package nts.uk.cnv.core.dom.conversiontable.pattern;

import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.SelectSentence;

public class GuidPattern extends ConversionPattern {
	private DatabaseSpec spec;

	public GuidPattern(DatabaseSpec spec) {
		this.spec = spec;
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		conversionSql.getSelect().add(
				SelectSentence.createNotFormat("", spec.newUuid())
			);
		return conversionSql;
	}

}
