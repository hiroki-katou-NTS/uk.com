package nts.uk.cnv.core.dom.conversionsql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import lombok.Getter;
import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;

@Getter
public class ConversionUpdateSQL implements ConversionSQL {

	private UpdateSentence update;
	private FromSentence from;
	private List<WhereSentence> where;
	private List<String> groupingColumns;
	private String programId;

	public ConversionUpdateSQL(
			TableFullName table,
			List<WhereSentence> where,
			DatabaseSpec spec,
			String programId) {
		this.update = new UpdateSentence(table);
		this.from = new FromSentence();
		this.where = where;
		this.groupingColumns = new ArrayList<>();
		this.programId = programId;

		Map<ColumnName, ColumnExpression> fixedColumns = fixedColumns(spec);
		fixedColumns.entrySet().stream().forEach(fixedColumn -> {
			this.update.add(fixedColumn.getKey(), fixedColumn.getValue());
		});
	}

	@Override
	public void add(ColumnName column, ColumnExpression value) {
		this.update.add(column, value);
	}

	@Override
	public void add(ColumnName column, ColumnExpression value, TreeMap<FormatType, String> formatTable) {
		this.update.add(column, value);
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

		return this.update.sql() + "\r\n" +
				from.sql(spec) +
				groupbyString + "\r\n" +
				whereString;
	}

	public void addOnSentense(ColumnName column, String newExpression) {
		Join source = this.from.getJoinTables().stream().findFirst().get();
		source.onSentences.add(new OnSentence(column, new ColumnName(newExpression), Optional.empty()));
	}
	
	private Map<ColumnName, ColumnExpression> fixedColumns(DatabaseSpec spec){
		String sysDatetime = spec.sysDatetime();
		 String ccd = "''";
		String scd = "''";
		String pg = "'" + programId + "'";
		return Collections.unmodifiableMap( new LinkedHashMap<ColumnName, ColumnExpression>() {
		    {
		        put (new ColumnName("UPD_DATE"), new ColumnExpression("", sysDatetime));
		        put (new ColumnName("UPD_CCD"), new ColumnExpression("", ccd));
		        put (new ColumnName("UPD_SCD"), new ColumnExpression("", scd));
		        put (new ColumnName("UPD_PG"), new ColumnExpression("", pg));
		        put (new ColumnName("EXCLUS_VER"), new ColumnExpression("", "(EXCLUS_VER + 1)"));
		    }} );
	}
}
