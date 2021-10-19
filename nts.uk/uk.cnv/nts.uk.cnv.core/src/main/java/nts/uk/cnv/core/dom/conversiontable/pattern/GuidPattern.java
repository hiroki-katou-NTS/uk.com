package nts.uk.cnv.core.dom.conversiontable.pattern;

import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;

public class GuidPattern extends ConversionPattern {
	private DatabaseSpec spec;

	public GuidPattern(DatabaseSpec spec) {
		this.spec = spec;
	}

	@Override
	public ConversionSQL apply(ColumnName column, ConversionSQL conversionSql, boolean removeDuplicate) {
		conversionSql.add(column, new ColumnExpression(spec.newUuid()));
		return conversionSql;
	}

}
