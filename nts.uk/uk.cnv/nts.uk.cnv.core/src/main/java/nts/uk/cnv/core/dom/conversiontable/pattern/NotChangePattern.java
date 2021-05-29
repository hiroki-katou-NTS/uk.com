package nts.uk.cnv.core.dom.conversiontable.pattern;

import lombok.Getter;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;

/**
 * そのまま移送するパターン
 * @author ai_muto
 */
@Getter
public class NotChangePattern extends ConversionPattern {

	private Join join;

	private String sourceColumn;

	public NotChangePattern(Join join, String sourceColumn) {
		this.join = join;
		this.sourceColumn = sourceColumn;
	}

	@Override
	public ConversionSQL apply(ColumnName column, ConversionSQL conversionSql) {
		conversionSql.addJoin(join);

		conversionSql.add(
				column,
				new ColumnExpression(join.tableName.getAlias(), sourceColumn));
		return conversionSql;
	}

}
