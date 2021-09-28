package nts.uk.cnv.core.dom.conversionsql;

import java.util.TreeMap;

import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;

/**
 * コンバートSQL
 * @author ai_muto
 *
 */
public interface ConversionSQL {

	public void addWhere(WhereSentence where);
	public void addJoin(Join join);
	public void add(ColumnName column, ColumnExpression value);
	public void add(ColumnName column, ColumnExpression value, TreeMap<FormatType, String> formatTable);
	public void addGroupingColumn(ColumnExpression expression);

	public TableFullName getBaseTable();

	public String build(DatabaseSpec spec);
}
