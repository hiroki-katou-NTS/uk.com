package nts.uk.ctx.at.function.dom.holidaysremaining.report;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;

@Getter
@Setter
public class HolidayRemainingDataSource {
	public HolidayRemainingDataSource(String startMonth, String endMonth, String outputItemSettingCode, int pageBreak,
			HolidaysRemainingManagement holidaysRemainingManagement, List<String> listEmployeeIds,
			Map<String, HolidaysRemainingEmployee> mapEmployees) {
		super();
		this.startMonth = GeneralDate.fromString(startMonth, "yyyy/MM/dd");
		this.endMonth = GeneralDate.fromString(endMonth, "yyyy/MM/dd");
		this.outputItemSettingCode = outputItemSettingCode;
		this.pageBreak = pageBreak;
		this.holidaysRemainingManagement = holidaysRemainingManagement;
		this.empIds = listEmployeeIds;
		this.mapEmployees = mapEmployees;
	}
	private GeneralDate startMonth;
	private GeneralDate endMonth;
	private String outputItemSettingCode;
	private int pageBreak;
	private HolidaysRemainingManagement holidaysRemainingManagement;
	private List<String> empIds;
	private Map<String, HolidaysRemainingEmployee> mapEmployees;
}
