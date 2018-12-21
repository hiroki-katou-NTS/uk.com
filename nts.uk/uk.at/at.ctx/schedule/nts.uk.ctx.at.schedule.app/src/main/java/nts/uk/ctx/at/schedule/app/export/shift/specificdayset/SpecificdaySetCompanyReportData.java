package nts.uk.ctx.at.schedule.app.export.shift.specificdayset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificName;

@Getter
@Setter
@AllArgsConstructor
public class SpecificdaySetCompanyReportData {
	private String companyId;
	private GeneralDate specificDate;
	private SpecificDateItemNo specificDateItemNo;
	private SpecificName specificDateItemName;

	public static SpecificdaySetCompanyReportData createFromJavaType(String companyId, GeneralDate specificDate, Integer specificDateItemNo, String specificDateItemName) {
		return new SpecificdaySetCompanyReportData(
				companyId, 
				specificDate,
				new SpecificDateItemNo(specificDateItemNo),
				new SpecificName(specificDateItemName));
	}
	
	public String getYearMonth() {
		return specificDate.yearMonth().toString();
	}
	
	public int getDay() {
		return specificDate.day();
	}
	
	
}
