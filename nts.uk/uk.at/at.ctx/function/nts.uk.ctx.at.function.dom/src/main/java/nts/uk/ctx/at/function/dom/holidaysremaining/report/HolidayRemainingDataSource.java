package nts.uk.ctx.at.function.dom.holidaysremaining.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HolidayRemainingDataSource {
	private String startMonth;
	private String endMonth;
	private String outputItemSettingCode;
	private int pageBreak;
	public HolidayRemainingDataSource(String startMonth, String endMonth, String outputItemSettingCode, int pageBreak) {
		super();
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.outputItemSettingCode = outputItemSettingCode;
		this.pageBreak = pageBreak;
	}
}
