package nts.uk.file.at.app.export.shift.specificdayset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificName;

@Getter
@Setter
@AllArgsConstructor
public class SpecificdaySetWorkplaceReportData {
	private String workplaceId;
	private GeneralDate specificDate;
	private SpecificDateItemNo specificDateItemNo;
	private SpecificName specificDateItemName;
	private String workplaceCode;
	private String workplaceName;
	
	public static SpecificdaySetWorkplaceReportData createFromJavaType(String workplaceId, 
			GeneralDate specificDate, Integer specificDateItemNo, String specificDateItemName, String workplaceCode, String workplaceName) {
		return new SpecificdaySetWorkplaceReportData(
				workplaceId, 
				specificDate, 
				new SpecificDateItemNo(specificDateItemNo),
				new SpecificName(specificDateItemName),
				workplaceCode,
				workplaceName);
	}
	
	public String getYearMonth() {
		return specificDate.yearMonth().toString();
	}
	
	public int getDay() {
		return specificDate.day();
	}

}
