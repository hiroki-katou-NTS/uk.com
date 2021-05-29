package nts.uk.cnv.core.dom.conversiontable.pattern;

import lombok.Getter;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.SelectSentence;
import nts.uk.cnv.core.dom.conversiontable.pattern.manager.ParentJoinPatternManager;

@Getter
public class ParentJoinPattern extends ConversionPattern {

	private Join sourceJoin;

	private Join mappingJoin;

	private String parentColumn;

	private String parentTableName;
	private String targetTable;

	public ParentJoinPattern(Join sourceJoin, Join mappingJoin, String parentColumn, String parentTableName) {
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
	public ConversionSQL apply(ConversionSQL conversionSql) {
		conversionSql.getFrom().addJoin(sourceJoin);
		conversionSql.getFrom().addJoin(mappingJoin);

		conversionSql.getSelect().add(
				SelectSentence.createNotFormat(
						mappingJoin.tableName.getAlias(),
						ParentJoinPatternManager.parentValueColumnName
				));

		return conversionSql;
	}

}
