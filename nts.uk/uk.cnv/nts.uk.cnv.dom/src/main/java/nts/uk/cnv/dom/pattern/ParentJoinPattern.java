package nts.uk.cnv.dom.pattern;

import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.SelectSentence;
import nts.uk.cnv.dom.service.ConversionInfo;

public class ParentJoinPattern extends ConversionPattern {

	private Join sourceJoin;
	
	private Join parentJoin;
	
	private String parentColumn;
	
	public ParentJoinPattern(ConversionInfo info, Join sourceJoin, Join parentJoin, String parentColumn) {
		super(info);
		this.sourceJoin = sourceJoin;
		this.parentJoin = parentJoin;
		this.parentColumn = parentColumn;
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		conversionSql.getFrom().addJoin(sourceJoin);
		conversionSql.getFrom().addJoin(parentJoin);

		conversionSql.getSelect().add(SelectSentence.createNotFormat(parentJoin.tableName.getAlias(), parentColumn));

		return conversionSql;
	}

}
