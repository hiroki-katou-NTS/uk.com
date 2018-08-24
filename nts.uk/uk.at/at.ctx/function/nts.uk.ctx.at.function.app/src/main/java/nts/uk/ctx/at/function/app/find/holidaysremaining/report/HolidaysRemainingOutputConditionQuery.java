package nts.uk.ctx.at.function.app.find.holidaysremaining.report;

import lombok.Value;

@Value
public class HolidaysRemainingOutputConditionQuery {
	private String startMonth;
	private String endMonth;
	private String outputItemSettingCode;
	private int pageBreak;
	private String baseDate;
	private int closureId;
}
