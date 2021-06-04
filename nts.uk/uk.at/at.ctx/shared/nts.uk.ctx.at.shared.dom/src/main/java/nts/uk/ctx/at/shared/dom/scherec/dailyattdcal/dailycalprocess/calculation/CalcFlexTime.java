package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculationMinusExist;

/**
 * Output：フレックス時間
 * @author shuichi_ishida
 */
@Getter
public class CalcFlexTime {
	//フレックス時間
	private TimeDivergenceWithCalculationMinusExist flexTime;
	//休暇加算時間
	private AttendanceTime vacationAddTime;
	
	public CalcFlexTime(TimeDivergenceWithCalculationMinusExist flexTime, AttendanceTime vacationAddTime) {
		this.flexTime = flexTime;
		this.vacationAddTime = vacationAddTime;
	}
}
