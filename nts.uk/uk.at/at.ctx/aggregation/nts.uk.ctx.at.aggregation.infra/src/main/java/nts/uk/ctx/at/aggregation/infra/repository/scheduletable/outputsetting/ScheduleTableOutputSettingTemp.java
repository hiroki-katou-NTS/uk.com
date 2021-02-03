package nts.uk.ctx.at.aggregation.infra.repository.scheduletable.outputsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleTableOutputSettingTemp {
	
	private String code;
	
	private String name;
	
	private Integer personalInfo;
	
	private Integer additionalInfo;
	
	private Integer attendanceItem;
	
	private Integer personalCatergoryNo;
	
	private Integer workplaceCatergoryNo;
}
