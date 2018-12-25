package nts.uk.ctx.at.schedule.app.export.shift.businesscalendar.daycalendar;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class ClassCalendarReportData {
	private String companyId;
	
	private GeneralDate date;
	
	private int workingDayAtr;
	
	private String workingDayAtrName;
	
	private String classCode;
	private String className;
	

	public ClassCalendarReportData(String companyId, GeneralDate date, int workingDayAtr, String classCode, String className) {
		super();
		this.companyId = companyId;
		this.date = date;
		this.workingDayAtr = workingDayAtr;
		this.workingDayAtrName = getName(workingDayAtr);
		this.classCode = classCode;
		this.className = className;
	}
	
	public String getYearMonth() {
		return date.yearMonth().toString();
	}
	
	public int getDay() {
		return date.day();
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
	
	public static ClassCalendarReportData createFromJavaType(String workplaceId, 
			GeneralDate date, Integer workingDayAtr, String classCode, String className) {
		return new ClassCalendarReportData(
				workplaceId, 
				date, 
				workingDayAtr,
				classCode,
				className);
	}
}
