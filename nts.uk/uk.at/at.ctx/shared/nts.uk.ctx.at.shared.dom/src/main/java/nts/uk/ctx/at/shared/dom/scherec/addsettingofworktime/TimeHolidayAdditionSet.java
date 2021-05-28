/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.Serializable;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeVacationOffSetItem;

/**
 * The Class TimeHolidayAdditionSet.
 */
@Getter
@Setter
@Builder
// 時間休暇加算設定
public class TimeHolidayAdditionSet extends DomainObject implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** The adding method. */
	// 加算方法
	private TimeHolidayAddingMethod addingMethod;
	
	/** The work class. */
	// 勤務区分
	private WorkClassOfTimeHolidaySet workClass;
	
	/**
	 * 加算する時間を判断する
	 * @param dailyTime 時間休暇使用時間
	 * @param time 相殺対象時間
	 * @return 時間休暇加算時間
	 */
	public AttendanceTime getAddTime(AttendanceTime dailyTime, AttendanceTime time) {
		if(this.addingMethod.isAddAllTimeUsed()) {
			return dailyTime;
		}
		if(dailyTime.lessThan(time)) {
			return dailyTime;
		}
		return time;
	}
}
