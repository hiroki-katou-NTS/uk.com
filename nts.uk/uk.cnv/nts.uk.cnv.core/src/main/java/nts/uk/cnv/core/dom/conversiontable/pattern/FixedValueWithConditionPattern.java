package nts.uk.cnv.core.dom.conversiontable.pattern;

import java.util.TreeMap;

import lombok.Getter;
import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.FormatType;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.RelationalOperator;

/**
 * 条件付き固定値（条件に一致しない場合はそのまま移送）
 * 変換元の値が「-1」の場合だけ固定値をセットする、など
 * @author ai_muto
 *
 */
@Getter
public class FixedValueWithConditionPattern extends ConversionPattern {
	private DatabaseSpec spec;

	private Join join;

	private String sourceColumn;

	private RelationalOperator relationalOperator;

	private String conditionValue;

	private boolean isParamater;

	private String expression;

	public FixedValueWithConditionPattern(
			DatabaseSpec spec, Join join, String sourceColumn,
			RelationalOperator relationalOperator, String conditionValue,
			boolean isParamater, String expression) {
		this.spec = spec;
		this.join = join;
		this.sourceColumn = sourceColumn;
		this.relationalOperator = relationalOperator;
		this.conditionValue = conditionValue;
		this.isParamater = isParamater;
		this.expression = expression;
	}

	@Override
	public ConversionSQL apply(ColumnName column, ConversionSQL conversionSql, boolean removeDuplicate) {
		conversionSql.addJoin(join);

		String newExpression = (isParamater)
				? spec.param(expression)
				: expression;

		String source = join.tableName.getAlias() + "." + sourceColumn;
		TreeMap<FormatType, String> formatTable = new TreeMap<>();
		formatTable.put(
				FormatType.CASE,
				"CASE WHEN " + source + " " + relationalOperator.getSign() + " " + conditionValue + " "
				+ "THEN %s ELSE " + source + " END"
			);

		conversionSql.add(column, new ColumnExpression(newExpression), formatTable);
		if(removeDuplicate) {
			conversionSql.addGroupingColumn(column);
		}

		return conversionSql;
	}

}
