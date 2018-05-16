package nts.uk.ctx.at.function.dom.holidaysremaining.report;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;

@Getter
@Setter
public class HolidayRemainingDataSource {
	public HolidayRemainingDataSource(String startMonth, String endMonth, String outputItemSettingCode, int pageBreak,
			HolidaysRemainingManagement holidaysRemainingManagement, List<HolidaysRemainingEmployee> listEmployee) {
		super();
		this.startMonth = GeneralDate.fromString(startMonth, "yyyy/MM/dd");
		this.endMonth = GeneralDate.fromString(endMonth, "yyyy/MM/dd");
		this.outputItemSettingCode = outputItemSettingCode;
		this.pageBreak = pageBreak;
		this.holidaysRemainingManagement = holidaysRemainingManagement;
		this.listEmployee = listEmployee;
	}
	private GeneralDate startMonth;
	private GeneralDate endMonth;
	private String outputItemSettingCode;
	private int pageBreak;
	private HolidaysRemainingManagement holidaysRemainingManagement;
	private List<HolidaysRemainingEmployee> listEmployee;
}
