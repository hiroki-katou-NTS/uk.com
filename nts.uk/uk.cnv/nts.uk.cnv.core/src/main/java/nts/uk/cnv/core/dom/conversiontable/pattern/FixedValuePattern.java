package nts.uk.cnv.core.dom.conversiontable.pattern;

import lombok.Getter;
import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.SelectSentence;

/**
 * 固定値
 * @author ai_muto
 *
 */
@Getter
public class FixedValuePattern extends ConversionPattern {
	private DatabaseSpec spec;

	private boolean isParamater;

	private String expression;

	public FixedValuePattern(DatabaseSpec spec, boolean isParamater, String expression) {
		this.spec = spec;
		this.isParamater = isParamater;
		this.expression = expression;
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		String newExpression = (isParamater)
				? spec.param(expression)
				: expression;
		conversionSql.getSelect().add(SelectSentence.createNotFormat("", newExpression));
		return conversionSql;
	}

}
