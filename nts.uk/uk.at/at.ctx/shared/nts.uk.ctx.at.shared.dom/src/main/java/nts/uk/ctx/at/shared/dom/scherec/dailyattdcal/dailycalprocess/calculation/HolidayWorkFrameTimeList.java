package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;

/**
 * @author thanh_nx
 *
 *         休出枠時間一覧
 */
@AllArgsConstructor
@Data
public class HolidayWorkFrameTimeList {

	// 一覧
	private List<HolidayWorkFrameTime> lstHolWFrame;

	// 事前申請上限制御処理
	public List<HolidayWorkFrameTime> afterUpperControl(AutoCalSetting autoCalcSet) {

		if (autoCalcSet.getUpLimitORtSet() != TimeLimitUpperLimitSetting.LIMITNUMBERAPPLICATION) {
			return lstHolWFrame.stream().map(x -> x.clone()).collect(Collectors.toList());
		}

		return lstHolWFrame.stream().map(x -> {
			val temp = x.clone();
			int beforeValueApp = temp.getBeforeApplicationTime().isPresent() ?  temp.getBeforeApplicationTime().get().v() : 0;
			if (temp.getHolidayWorkTime().isPresent() && beforeValueApp <= temp.getHolidayWorkTime().get().getTime()
							.valueAsMinutes()) {
				temp.getHolidayWorkTime().get().setTime(new AttendanceTime(beforeValueApp));
			}
			return temp;
		}).collect(Collectors.toList());

	}
}
