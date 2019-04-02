package nts.uk.file.at.app.export.shift.businesscalendar.daycalendar;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.i18n.TextResource;

@Getter
public class CompanyCalendarReportData {
	
	private String companyId;
	
	private GeneralDate date;
	
	private int workingDayAtr;
	
	private String workingDayAtrName;
	
	private String eventName;

	public CompanyCalendarReportData(String companyId, GeneralDate date, int workingDayAtr, String eventName) {
		super();
		this.companyId = companyId;
		this.date = date;
		this.workingDayAtr = workingDayAtr;
		this.workingDayAtrName = getName(workingDayAtr);
		this.eventName = eventName;
	}
	
	private String getName(int workingDayAtr) {
		switch(workingDayAtr) {
			case 0:
//				return "稼働日";
				return TextResource.localize("KSM004_95");
			case 1:
//				return "非稼働日（法内）";
				return TextResource.localize("KSM004_96");
			case 2:
//				return "非稼働日（法外）";
				return TextResource.localize("KSM004_97");
			default:
				break;
		}
		return null;
	}

	public static CompanyCalendarReportData createFromJavaType(String companyId, GeneralDate date, int workingDayAtr, String eventName){
		return new CompanyCalendarReportData(companyId, date, workingDayAtr, eventName);
	}
	
	public String getYearMonth() {
		return date.yearMonth().toString();
	}
	
	public int getDay() {
		return date.day();
	}

}
