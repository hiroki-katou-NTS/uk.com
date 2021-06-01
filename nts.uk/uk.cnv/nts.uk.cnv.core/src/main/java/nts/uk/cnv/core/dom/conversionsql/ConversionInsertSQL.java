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

	public ConversionInsertSQL(InsertSentence insert, List<SelectSentence> select, FromSentence from, List<WhereSentence> where) {
		this.insert = insert;
		this.select = select;
		this.from = from;
		this.where = where;
	}

	public ConversionInsertSQL(TableFullName table, List<WhereSentence> whereList) {
		this.insert = new InsertSentence(table);
		this.select = new ArrayList<>();
		this.from = new FromSentence();
		this.where = whereList;
	}

	@Override
	public void add(ColumnName column, ColumnExpression value) {
		this.insert.addExpression(new ColumnExpression(column.sql()));
		this.select.add(new SelectSentence(value, new TreeMap<>()));
	}
	@Override
	public void add(ColumnName column, ColumnExpression value, TreeMap<FormatType, String> formatTable) {
		this.insert.addExpression(new ColumnExpression(column.sql()));
		this.select.add(new SelectSentence(value, formatTable));
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
		String whereString = (from.getBaseTable().isPresent() && where.size() > 0) ? WhereSentence.join(where) : "";
		return insert.sql(
				SelectSentence.join(select) + "\r\n" +
				from.sql(spec) + "\r\n" +
				whereString
			);
	}
}
