package nts.uk.cnv.core.dom.conversionsql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;

public class ConversionInsertSQL implements ConversionSQL {

	private InsertSentence insert;
	private List<SelectSentence> select;
	private FromSentence from;
	private List<WhereSentence> where;
	private List<String> groupingColumns;	
	
	private String programId;

	public ConversionInsertSQL(
			InsertSentence insert,
			List<SelectSentence> select,
			FromSentence from,
			List<WhereSentence> where,
			List<String> groupingColumns,
			DatabaseSpec spec,
			String programId) {
		this.insert = insert;
		this.select = select;
		this.from = from;
		this.where = where;
		this.groupingColumns = groupingColumns;
		this.programId = programId;
		
		addFixedColumns(spec);
	}

	public ConversionInsertSQL(
			TableFullName table,
			List<WhereSentence> whereList,
			DatabaseSpec spec,
			String programId) {
		this.insert = new InsertSentence(table);
		this.select = new ArrayList<>();
		this.from = new FromSentence();
		this.where = whereList;
		this.groupingColumns = new ArrayList<>();

		addFixedColumns(spec);
	}

	private void addFixedColumns(DatabaseSpec spec) {
		Map<ColumnExpression, SelectSentence> fixedColumns = fixedColumns(spec);
		fixedColumns.entrySet().stream().forEach(fixedColumn -> {
			this.insert.addExpression(fixedColumn.getKey());
			this.select.add(fixedColumn.getValue());
		});
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
				whereString + "\r\n" +
				groupbyString
			);
	}
	
	private Map<ColumnExpression, SelectSentence> fixedColumns(DatabaseSpec spec){
		String sysDatetime = spec.sysDatetime();
		 String ccd = "NULL";
		String scd = "NULL";
		String pg = "'" + programId + "'";
		return Collections.unmodifiableMap( new LinkedHashMap<ColumnExpression, SelectSentence>() {
		    {
		        put (new ColumnExpression("", "INS_DATE"), SelectSentence.createNotFormat("", sysDatetime, "INS_DATE"));
		        put (new ColumnExpression("", "INS_CCD"), SelectSentence.createNotFormat("", ccd, "INS_CCD"));
		        put (new ColumnExpression("", "INS_SCD"), SelectSentence.createNotFormat("", scd, "INS_SCD"));
		        put (new ColumnExpression("", "INS_PG"), SelectSentence.createNotFormat("", pg, "INS_PG"));
		        put (new ColumnExpression("", "UPD_DATE"), SelectSentence.createNotFormat("", sysDatetime, "UPD_DATE"));
		        put (new ColumnExpression("", "UPD_CCD"), SelectSentence.createNotFormat("", ccd, "UPD_CCD"));
		        put (new ColumnExpression("", "UPD_SCD"), SelectSentence.createNotFormat("", scd, "UPD_SCD"));
		        put (new ColumnExpression("", "UPD_PG"), SelectSentence.createNotFormat("", pg, "UPD_PG"));
		        put (new ColumnExpression("", "EXCLUS_VER"), SelectSentence.createNotFormat("", "0", "EXCLUS_VER"));
		    }} );
	}
}
