package nts.uk.cnv.core.dom.conversiontable.pattern;

import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
/**
 * 移送元結合パターン
 */
@Getter
public class SourceJoinPattern extends ConversionPattern {
	ConversionInfo info;

	private Join sourceJoin;

	private String sourceColumn;

	public SourceJoinPattern(ConversionInfo info, Join sourceJoin, String sourceColumn) {
		this.info = info;
		this.sourceJoin = sourceJoin;
		this.sourceColumn = sourceColumn;
	}

	@Override
	public ConversionSQL apply(ColumnName column, ConversionSQL conversionSql, boolean removeDuplicate) {
		conversionSql.addJoin(sourceJoin);

		ColumnExpression expression = new ColumnExpression(
				sourceJoin.tableName.getAlias(),
				sourceColumn);
		conversionSql.add(column, expression);

		if(removeDuplicate) {
			conversionSql.addGroupingColumn(expression);
		}

		return conversionSql;
	}

	public String sourceJoinColumns() {
		return String.join(",", this.getSourceJoin().getOnSentences().stream()
				.map(on -> on.getLeft().getName())
				.collect(Collectors.toList()));
	}

}
