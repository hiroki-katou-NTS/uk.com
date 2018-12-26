package nts.uk.file.at.app.export.shift.businesscalendar.daycalendar;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class CompanyCalendarReportData {
	
	private String companyId;
	
	private GeneralDate date;
	
	private int workingDayAtr;
	
	private String workingDayAtrName;

	public CompanyCalendarReportData(String companyId, GeneralDate date, int workingDayAtr) {
		super();
		this.companyId = companyId;
		this.date = date;
		this.workingDayAtr = workingDayAtr;
		this.workingDayAtrName = getName(workingDayAtr);
	}
	
	private String getName(int workingDayAtr) {
		switch(workingDayAtr) {
			case 0:
				return "稼働日";
//				return TextResource.localize("KSM004_97");
			case 1:
				return "非稼働日（法内）";
//				return TextResource.localize("KSM004_98");
			case 2:
				return "非稼働日（法外）";
//				return TextResource.localize("KSM004_99");
			default:
				break;
		}
		return null;
	}

	public static CompanyCalendarReportData createFromJavaType(String companyId, GeneralDate date, int workingDayAtr){
		return new CompanyCalendarReportData(companyId, date, workingDayAtr);
	}
	
	public String getYearMonth() {
		return date.yearMonth().toString();
	}
	
	public int getDay() {
		return date.day();
	}

}
