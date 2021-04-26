package nts.uk.cnv.dom.conversiontable.pattern;

import lombok.Getter;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.SelectSentence;
import nts.uk.cnv.dom.conversiontable.pattern.manager.ParentJoinPatternManager;
import nts.uk.cnv.dom.service.ConversionInfo;

@Getter
public class ParentJoinPattern extends ConversionPattern {

	private Join sourceJoin;

	private Join mappingJoin;

	private String parentColumn;

	private String parentTableName;
	private String targetTable;

	public ParentJoinPattern(ConversionInfo info, Join sourceJoin, Join mappingJoin, String parentColumn, String parentTableName) {
		super(info);
		this.sourceJoin = sourceJoin;
		this.mappingJoin = mappingJoin;
		this.parentColumn = parentColumn;
		this.parentTableName = parentTableName;
		this.targetTable = "";
	}

	public ParentJoinPattern(ConversionInfo info, Join sourceJoin, Join mappingJoin, String parentColumn, String parentTableName, String targetTable) {
		super(info);
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
