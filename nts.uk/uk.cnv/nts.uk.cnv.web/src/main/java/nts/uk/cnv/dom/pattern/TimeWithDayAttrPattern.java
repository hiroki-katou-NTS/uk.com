package nts.uk.cnv.dom.pattern;

import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.service.ConversionInfo;

public class TimeWithDayAttrPattern extends ConversionPattern {

	private Join sourceJoin;

	private String timeColumn;

	private String dayAttrColumn;

	public TimeWithDayAttrPattern(ConversionInfo info, Join sourceJoin, String timeColumn, String dayAttrColumn) {
		super(info);
		this.sourceJoin = sourceJoin;
		this.timeColumn = timeColumn;
		this.dayAttrColumn = dayAttrColumn;
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
