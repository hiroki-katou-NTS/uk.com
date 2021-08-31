package nts.uk.cnv.core.dom.conversiontable.pattern;

import lombok.Getter;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.ConversionUpdateSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;

/**
 * 固定値
 * @author ai_muto
 *
 */
@Getter
public class FixedValuePattern extends ConversionPattern {
	private ConversionInfo info;

	private Join source;

	private boolean isParamater;

	private String expression;

	public FixedValuePattern(ConversionInfo info, Join source, boolean isParamater, String expression) {
		this.info = info;
		this.source = source;
		this.isParamater = isParamater;
		this.expression = expression;
	}

	@Override
	public ConversionSQL apply(ColumnName column, ConversionSQL conversionSql, boolean removeDuplicate) {
		String newExpression = (isParamater)
				? info.getDatebaseType().spec().param(expression)
				: expression;
		if(info.getType() == ConversionCodeType.UPDATE) {
			conversionSql.addJoin(source);
			((ConversionUpdateSQL)conversionSql).addOnSentense(column, newExpression);
		}
		else {
			conversionSql.add(column, new ColumnExpression(newExpression));
		}

		return conversionSql;
	}

}
