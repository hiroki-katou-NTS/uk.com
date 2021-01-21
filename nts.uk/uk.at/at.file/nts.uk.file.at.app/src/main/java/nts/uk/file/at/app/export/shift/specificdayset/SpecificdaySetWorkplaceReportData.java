package nts.uk.file.at.app.export.shift.specificdayset;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificName;

@Getter
@Setter
@NoArgsConstructor
public class SpecificdaySetWorkplaceReportData {
	public SpecificdaySetWorkplaceReportData(String workplaceId, GeneralDate specificDate,
			SpecificDateItemNo specificDateItemNo, SpecificName specificDateItemName, Optional<String> workplaceCode,
			Optional<String> workplaceName) {
		super();
		this.workplaceId = workplaceId;
		this.specificDate = specificDate;
		this.specificDateItemNo = specificDateItemNo;
		this.specificDateItemName = specificDateItemName;
		this.workplaceCode = workplaceCode;
		this.workplaceName = workplaceName;
		this.hierarchyCode = Optional.empty();
	}

	private String workplaceId;
	private GeneralDate specificDate;
	private SpecificDateItemNo specificDateItemNo;
	private SpecificName specificDateItemName;
	private Optional<String> workplaceCode;
	private Optional<String> workplaceName;
	private Optional<String> hierarchyCode;
	
	public static SpecificdaySetWorkplaceReportData createFromJavaType(String workplaceId, 
			GeneralDate specificDate, Integer specificDateItemNo, String specificDateItemName, Optional<String> workplaceCode, Optional<String> workplaceName) {
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
