package nts.uk.ctx.at.schedule.app.export.shift.businesscalendar.daycalendar;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class WorkplaceCalendarReportData {
	
	private String companyId;
	
	private GeneralDate date;
	
	private int workingDayAtr;
	
	private String workingDayAtrName;
	
	private String workplaceCode;
	private String workplaceName;
	

	public WorkplaceCalendarReportData(String companyId, GeneralDate date, int workingDayAtr, String workplaceCode, String workplaceName) {
		super();
		this.companyId = companyId;
		this.date = date;
		this.workingDayAtr = workingDayAtr;
		this.workingDayAtrName = getName(workingDayAtr);
		this.workplaceCode = workplaceCode;
		this.workplaceName = workplaceName;
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
	
	public static WorkplaceCalendarReportData createFromJavaType(String workplaceId, 
			GeneralDate date, Integer workingDayAtr, String workplaceCode, String workplaceName) {
		return new WorkplaceCalendarReportData(
				workplaceId, 
				date, 
				workingDayAtr,
				workplaceCode,
				workplaceName);
	}
}
