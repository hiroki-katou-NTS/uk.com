package nts.uk.cnv.core.dom.conversiontable.pattern;

import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
/**
 * View結合パターン
 */
@AllArgsConstructor
@Getter
public class ViewJoinPattern extends ConversionPattern {
	ConversionInfo info;

	private Join viewJoin;

	private String viewColumn;

	private String createViewSql;

	@Override
	public ConversionSQL apply(ColumnName column, ConversionSQL conversionSql, boolean removeDuplicate) {
		conversionSql.addJoin(viewJoin);

		ColumnExpression expression = new ColumnExpression(
				viewJoin.tableName.getAlias(),
				viewColumn);
		conversionSql.add(column, expression);

		if(removeDuplicate) {
			conversionSql.addGroupingColumn(expression);
		}

		return conversionSql;
	}

	public String getJoinKeys() {
		return String.join(",", this.getViewJoin().getOnSentences().stream()
				.map(on -> on.getLeft().getName())
				.collect(Collectors.toList()));
	}

}
