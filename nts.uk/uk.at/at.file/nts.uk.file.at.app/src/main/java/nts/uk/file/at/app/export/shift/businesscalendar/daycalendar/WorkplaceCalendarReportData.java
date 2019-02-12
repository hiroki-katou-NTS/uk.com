package nts.uk.file.at.app.export.shift.businesscalendar.daycalendar;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.i18n.TextResource;

@Getter
@Setter
@NoArgsConstructor
public class WorkplaceCalendarReportData {
	
	private String workplaceId;
	
	private GeneralDate date;
	
	private int workingDayAtr;
	
	private String workingDayAtrName;
	
	private Optional<String> workplaceCode;
	private Optional<String> workplaceName;
	
	private Optional<String> hierarchyCode;
	

	public WorkplaceCalendarReportData(String workplaceId, GeneralDate date, int workingDayAtr, Optional<String> workplaceCode, Optional<String> workplaceName) {
		super();
		this.workplaceId = workplaceId;
		this.date = date;
		this.workingDayAtr = workingDayAtr;
		this.workingDayAtrName = getName(workingDayAtr);
		this.workplaceCode = workplaceCode;
		this.workplaceName = workplaceName;
		this.hierarchyCode = Optional.empty();
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
	
	public static WorkplaceCalendarReportData createFromJavaType(String workplaceId, 
			GeneralDate date, Integer workingDayAtr, Optional<String> workplaceCode, Optional<String> workplaceName) {
		return new WorkplaceCalendarReportData(
				workplaceId, 
				date, 
				workingDayAtr,
				workplaceCode,
				workplaceName);
	}
}
