package nts.uk.cnv.core.dom.conversiontable.pattern;

import lombok.Getter;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;

/**
 * そのまま移送するパターン
 * @author ai_muto
 */
@Getter
public class NotChangePattern extends ConversionPattern {
	ConversionInfo info;

	private Join join;

	private String sourceColumn;

	public NotChangePattern(ConversionInfo info, Join join, String sourceColumn) {
		this.info = info;
		this.join = join;
		this.sourceColumn = sourceColumn;
	}

	@Override
	public ConversionSQL apply(ColumnName column, ConversionSQL conversionSql, boolean removeDuplicate) {

		conversionSql.addJoin(join);

		if(info.getType() == ConversionCodeType.UPDATE) {
			boolean isPkColumn = join.onSentences.stream().anyMatch(on -> on.getRight().equals(column));
			if (isPkColumn) return conversionSql;
		}
		ColumnExpression expression = new ColumnExpression(join.tableName.getAlias(), sourceColumn);
		conversionSql.add(column, expression);
		if(removeDuplicate) {
			conversionSql.addGroupingColumn(expression);
		}
		return conversionSql;
	}

}
