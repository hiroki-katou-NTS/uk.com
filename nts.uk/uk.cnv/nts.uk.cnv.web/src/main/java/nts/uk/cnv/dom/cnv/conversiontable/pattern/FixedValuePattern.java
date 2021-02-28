package nts.uk.cnv.dom.cnv.conversiontable.pattern;

import lombok.Getter;
import nts.uk.cnv.dom.cnv.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.cnv.conversionsql.SelectSentence;
import nts.uk.cnv.dom.cnv.service.ConversionInfo;

/**
 * 固定値
 * @author ai_muto
 *
 */
@Getter
public class FixedValuePattern extends ConversionPattern {

	private boolean isParamater;

	private String expression;

	public FixedValuePattern(ConversionInfo info, boolean isParamater, String expression) {
		super(info);
		this.isParamater = isParamater;
		this.expression = expression;
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		String newExpression = (isParamater)
				? info.getDatebaseType().spec().param(expression)
				: expression;
		conversionSql.getSelect().add(SelectSentence.createNotFormat("", newExpression));
		return conversionSql;
	}

}
