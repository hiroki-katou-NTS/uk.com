package nts.uk.cnv.core.dom.conversiontable.pattern;

import lombok.Getter;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;

@Getter
public class TimeWithDayAttrPattern extends ConversionPattern {

	private Join sourceJoin;

	private String timeColumn;

	private String dayAttrColumn;

	public TimeWithDayAttrPattern(Join sourceJoin, String timeColumn, String dayAttrColumn) {
		this.sourceJoin = sourceJoin;
		this.timeColumn = timeColumn;
		this.dayAttrColumn = dayAttrColumn;
	}

	@Override
	public ConversionSQL apply(ColumnName columnName, ConversionSQL conversionSql, boolean removeDuplicate) {
		//0:当日　1:翌日　2:翌々日　9:前日
		String caseSentence =
			"CASE " + this.dayAttrColumn
			+ "   WHEN 0 THEN " + this.timeColumn
			+ "   WHEN 1 THEN " + this.timeColumn + " + 1440 "
			+ "   WHEN 2 THEN " + this.timeColumn + " + 2880 "
			+ "   WHEN 9 THEN " + this.timeColumn + " - 1440 "
			+ "   ELSE 0 "
			+ " END";

		ColumnExpression expression = new ColumnExpression(caseSentence);
		conversionSql.addJoin(sourceJoin);
		conversionSql.add(columnName, expression);
		if(removeDuplicate) {
			conversionSql.addGroupingColumn(expression);
		}
		return conversionSql;
	}

}
