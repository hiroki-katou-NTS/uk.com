package nts.uk.cnv.core.dom.conversionsql;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;

public class ConversionInsertSQL implements ConversionSQL {

	private InsertSentence insert;
	private List<SelectSentence> select;
	private FromSentence from;
	private List<WhereSentence> where;
	private List<String> groupingColumns;

	public ConversionInsertSQL(
			InsertSentence insert,
			List<SelectSentence> select,
			FromSentence from,
			List<WhereSentence> where,
			List<String> groupingColumns) {
		this.insert = insert;
		this.select = select;
		this.from = from;
		this.where = where;
		this.groupingColumns = groupingColumns;
	}

	public ConversionInsertSQL(TableFullName table, List<WhereSentence> whereList) {
		this.insert = new InsertSentence(table);
		this.select = new ArrayList<>();
		this.from = new FromSentence();
		this.where = whereList;
		this.groupingColumns = new ArrayList<>();
	}

	@Override
	public void add(ColumnName column, ColumnExpression value) {
		this.insert.addExpression(new ColumnExpression(column.sql()));
		this.select.add(new SelectSentence(value, new TreeMap<>(), column.getName()));
	}
	@Override
	public void add(ColumnName column, ColumnExpression value, TreeMap<FormatType, String> formatTable) {
		this.insert.addExpression(new ColumnExpression(column.sql()));
		this.select.add(new SelectSentence(value, formatTable, column.getName()));
	}

	@Override
	public void addGroupingColumn(ColumnExpression expression) {
		groupingColumns.add(expression.sql());
	}

	@Override
	public void addJoin(Join join) {
		this.from.addJoin(join);
	}

	@Override
	public void addWhere(WhereSentence where) {
		this.where.add(where);
	}

	@Override
	public TableFullName getBaseTable() {
		return this.from.getBaseTable().orElse(null);
	}

	public String build(DatabaseSpec spec) {
		String whereString = (from.getBaseTable().isPresent() && where.size() > 0) ? "\r\n" + WhereSentence.join(where) : "";
		String groupbyString = (groupingColumns.size() > 0)
				? "GROUP BY " + String.join(",", groupingColumns) : "";
		return insert.sql(
				SelectSentence.join(select) + "\r\n" +
				from.sql(spec) +
				whereString +
				groupbyString
			);
	}
}
