package nts.uk.cnv.dom.conversiontable.pattern;

import java.util.Optional;
import java.util.TreeMap;

import lombok.Getter;
import nts.uk.cnv.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.FormatType;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.RelationalOperator;
import nts.uk.cnv.dom.conversionsql.SelectSentence;
import nts.uk.cnv.dom.service.ConversionInfo;

/**
 * 条件付き固定値（条件に一致しない場合はそのまま移送）
 * 変換元の値が「-1」の場合だけ固定値をセットする、など
 * @author ai_muto
 *
 */
@Getter
public class FixedValueWithConditionPattern extends ConversionPattern {

	private Join join;

	private String sourceColumn;

	private RelationalOperator relationalOperator;

	private String conditionValue;

	private boolean isParamater;

	private String expression;

	public FixedValueWithConditionPattern(
			ConversionInfo info, Join join, String sourceColumn,
			RelationalOperator relationalOperator, String conditionValue,
			boolean isParamater, String expression) {
		super(info);
		this.join = join;
		this.sourceColumn = sourceColumn;
		this.relationalOperator = relationalOperator;
		this.conditionValue = conditionValue;
		this.isParamater = isParamater;
		this.expression = expression;
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		conversionSql.getFrom().addJoin(join);

		String newExpression = (isParamater)
				? info.getDatebaseType().spec().param(expression)
				: expression;

		String source = join.tableName.getAlias() + "." + sourceColumn;
		TreeMap<FormatType, String> formatTable = new TreeMap<>();
		formatTable.put(
				FormatType.CASE,
				"CASE WHEN " + source + " " + relationalOperator.getSign() + " " + conditionValue + " "
				+ "THEN %s ELSE " + source + " END"
			);

		SelectSentence selectSentence = new SelectSentence(
				new ColumnExpression(Optional.empty(), newExpression),
				formatTable
			);

		conversionSql.getSelect().add(selectSentence);

		return conversionSql;
	}

}
