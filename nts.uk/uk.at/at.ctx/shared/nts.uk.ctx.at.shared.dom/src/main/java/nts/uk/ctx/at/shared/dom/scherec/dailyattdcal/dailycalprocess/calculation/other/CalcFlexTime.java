package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.other;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;

@Getter
public class CalcFlexTime {
	//フレックス時間
	private AttendanceTimeOfExistMinus flexTime;
	//休暇加算時間
	private AttendanceTime vacationAddTime;
	
	public CalcFlexTime(AttendanceTimeOfExistMinus flexTime, AttendanceTime vacationAddTime) {
		this.flexTime = flexTime;
		this.vacationAddTime = vacationAddTime;
	}
	
}
