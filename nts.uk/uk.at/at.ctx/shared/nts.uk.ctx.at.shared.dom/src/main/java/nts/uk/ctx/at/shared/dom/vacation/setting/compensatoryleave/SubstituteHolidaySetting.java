package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 代休発生設定 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SubstituteHolidaySetting {

	//代休発生に必要な休日出勤時間 
	private HolidayWorkHourRequired holidayWorkHourRequired;
	
	//代休発生に必要な残業時間
	private OvertimeHourRequired overtimeHourRequired;
	
}



