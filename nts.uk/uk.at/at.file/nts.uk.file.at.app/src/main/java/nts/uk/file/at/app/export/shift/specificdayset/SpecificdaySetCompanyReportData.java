package nts.uk.file.at.app.export.shift.specificdayset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificName;

@Getter
@Setter
@AllArgsConstructor
public class SpecificdaySetCompanyReportData {
	private GeneralDate specificDate;
	private SpecificDateItemNo specificDateItemNo;
	private SpecificName specificDateItemName;

	public static SpecificdaySetCompanyReportData createFromJavaType(GeneralDate specificDate, Integer specificDateItemNo, String specificDateItemName) {
		return new SpecificdaySetCompanyReportData(
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
