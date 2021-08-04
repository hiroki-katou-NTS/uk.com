package nts.uk.cnv.core.dom.conversiontable.pattern;

import lombok.Getter;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversiontable.pattern.manager.ParentJoinPatternManager;

@Getter
public class ParentJoinPattern extends ConversionPattern {

	private Join sourceJoin;

	private Join mappingJoin;

	private String parentColumn;

	private String parentTableName;
	private String targetTable;

	public ParentJoinPattern(Join sourceJoin, Join mappingJoin, String parentTableName, String parentColumn) {
		this.sourceJoin = sourceJoin;
		this.mappingJoin = mappingJoin;
		this.parentColumn = parentColumn;
		this.parentTableName = parentTableName;
		this.targetTable = "";
	}

	public ParentJoinPattern(Join sourceJoin, Join mappingJoin, String parentColumn, String parentTableName, String targetTable) {
		this.sourceJoin = sourceJoin;
		this.mappingJoin = mappingJoin;
		this.parentColumn = parentColumn;
		this.parentTableName = parentTableName;
		this.targetTable = targetTable;
	}

	@Override
	public ConversionSQL apply(ColumnName column, ConversionSQL conversionSql) {
		conversionSql.addJoin(sourceJoin);
		conversionSql.addJoin(mappingJoin);

		conversionSql.add(
				column,
				new ColumnExpression(
						mappingJoin.tableName.getAlias(),
						ParentJoinPatternManager.parentValueColumnName)
				);

		return conversionSql;
	}

}
