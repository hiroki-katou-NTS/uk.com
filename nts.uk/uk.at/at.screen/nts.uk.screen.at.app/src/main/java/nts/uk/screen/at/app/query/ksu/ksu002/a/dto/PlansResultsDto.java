package nts.uk.screen.at.app.query.ksu.ksu002.a.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.screen.at.app.ksu001.processcommon.WorkScheduleWorkInforDto;

@NoArgsConstructor
public class PlansResultsDto extends PersonalSchedulesDataDto{

	//・List<勤務予定（勤務情報）dto>
	@Setter
	@Getter
	public List<WorkScheduleWorkInforDto> workScheduleWorkInfor;

	public PlansResultsDto(PersonalSchedulesDataDto personalSchedulesData,
			List<WorkScheduleWorkInforDto> workScheduleWorkInfor) {
		super(personalSchedulesData, personalSchedulesData.weeklyData);
		this.workScheduleWorkInfor = workScheduleWorkInfor;
	}
	
}
