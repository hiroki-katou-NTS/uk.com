/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fluidworkset;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * The Class BreakTime.
 */
@Getter
// 流動休憩時間帯
public class FluRestTimeGroup {

	// 流動休憩設定
	private List<FluRestTimeSetting> fluidRestTimes;
	
	/**
	 * 指定した経過時間に一致する流動休憩設定を取得する
	 * @return
	 */
	public FluRestTimeSetting getMatchElapsedTime(AttendanceTime elapsedTime) {
		List<FluRestTimeSetting> getList = this.fluidRestTimes.stream().filter(ts -> ts.getFluidElapsedTime()==elapsedTime).collect(Collectors.toList());
		if(getList.size()>1) {
			throw new RuntimeException("Exist duplicate elapsedTime : " + elapsedTime);
		}
		return getList.get(0);
	}
	
}
