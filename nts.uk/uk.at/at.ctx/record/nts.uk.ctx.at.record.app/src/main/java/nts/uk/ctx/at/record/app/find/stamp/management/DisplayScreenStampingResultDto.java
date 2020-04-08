package nts.uk.ctx.at.record.app.find.stamp.management;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampDataOfEmployeesDto;

@Getter
@Setter
@NoArgsConstructor
public class DisplayScreenStampingResultDto {
	

				/** 勤務場所名 */
	public String workPlaceName;
	
	public StampDataOfEmployeesDto stampDataOfEmployeesDto;
	
	public DisplayScreenStampingResultDto(String workPlaceName, StampDataOfEmployeesDto stampDataOfEmployeesDto) {
		super();
		this.workPlaceName = workPlaceName;
		this.stampDataOfEmployeesDto = stampDataOfEmployeesDto;
	}
	
}


