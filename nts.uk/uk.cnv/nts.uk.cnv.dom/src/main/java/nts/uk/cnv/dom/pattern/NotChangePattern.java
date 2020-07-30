package nts.uk.cnv.dom.pattern;

import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.SelectSentence;
import nts.uk.cnv.dom.service.ConversionInfo;

public class NotChangePattern extends ConversionPattern {

	private Join join;
	
	private String sourceColumn;
	
	public NotChangePattern(ConversionInfo info, Join join, String sourceColumn) {
		super(info);
		this.join = join;
		this.sourceColumn = sourceColumn;
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		conversionSql.getFrom().addJoin(join);
		
		conversionSql.getSelect().add(SelectSentence.createNotFormat(join.tableName.getAlias(), sourceColumn));
		return conversionSql;
	}

}
