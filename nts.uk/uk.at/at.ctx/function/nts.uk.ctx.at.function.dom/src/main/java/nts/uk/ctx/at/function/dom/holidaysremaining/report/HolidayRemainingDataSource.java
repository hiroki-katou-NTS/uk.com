package nts.uk.ctx.at.function.dom.holidaysremaining.report;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;

@Getter
@Setter
public class HolidayRemainingDataSource {
	public HolidayRemainingDataSource(String startMonth, String endMonth, String outputItemSettingCode, int pageBreak,
			HolidaysRemainingManagement holidaysRemainingManagement, List<HolidaysRemainingEmployee> listEmployee) {
		super();
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.outputItemSettingCode = outputItemSettingCode;
		this.pageBreak = pageBreak;
		this.holidaysRemainingManagement = holidaysRemainingManagement;
		this.listEmployee = listEmployee;
	}
	private String startMonth;
	private String endMonth;
	private String outputItemSettingCode;
	private int pageBreak;
	private HolidaysRemainingManagement holidaysRemainingManagement;
	private List<HolidaysRemainingEmployee> listEmployee;
}
