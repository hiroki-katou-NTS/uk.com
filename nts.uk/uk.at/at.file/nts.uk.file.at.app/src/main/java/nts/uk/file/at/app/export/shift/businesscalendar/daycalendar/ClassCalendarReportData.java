package nts.uk.file.at.app.export.shift.businesscalendar.daycalendar;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.i18n.TextResource;

@Getter
public class ClassCalendarReportData {
	private String classId;
	
	private GeneralDate date;
	
	private int workingDayAtr;
	
	private String workingDayAtrName;
	
	private String className;
	

	public ClassCalendarReportData(String classId, GeneralDate date, int workingDayAtr, String className) {
		super();
		this.classId = classId;
		this.date = date;
		this.workingDayAtr = workingDayAtr;
		this.workingDayAtrName = getName(workingDayAtr);
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
	
	public static ClassCalendarReportData createFromJavaType(String classId, 
			GeneralDate date, Integer workingDayAtr, String className) {
		return new ClassCalendarReportData(
				classId, date, workingDayAtr, className);
	}
}
