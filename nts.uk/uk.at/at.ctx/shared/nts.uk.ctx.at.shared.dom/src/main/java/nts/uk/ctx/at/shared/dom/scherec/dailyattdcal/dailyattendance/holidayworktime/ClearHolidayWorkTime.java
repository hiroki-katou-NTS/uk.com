package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkTimeOfDaily;

/**
 * @author ThanhNX
 *
 *         休出時間のクリア
 */
public class ClearHolidayWorkTime {

	private static List<Integer> HOLIDAY_ID = Arrays.asList(266, 271, 276, 281, 286, 291, 296, 301, 306, 311);

	private static List<Integer> TRANSFERTIME_ID = Arrays.asList(267, 272, 277, 282, 287, 292, 297, 302, 307, 312);

	private static List<Integer> APP_ID = Arrays.asList(270, 275, 280, 285, 290, 295, 300, 305, 310, 315);

	// 休出時間のクリア
	public static void clearHolidaytime(HolidayWorkTimeOfDaily holidayTime,
			List<EditStateOfDailyAttd> lstEditState) {

		List<Integer> lstIstEdit = lstEditState.stream().map(x -> x.getAttendanceItemId()).collect(Collectors.toList());
		// 休出時間をクリア
		List<Integer> holdayIndexRemove = HOLIDAY_ID.stream().filter(x -> !lstIstEdit.contains(x))
				.map(x -> ((x - 266) / 5)).collect(Collectors.toList());

		List<Integer> transIndexRemove = TRANSFERTIME_ID.stream().filter(x -> !lstIstEdit.contains(x))
				.map(x -> ((x - 267) / 5)).collect(Collectors.toList());

		List<Integer> appIndexRemove = APP_ID.stream().filter(x -> !lstIstEdit.contains(x)).map(x -> ((x - 270) / 5))
				.collect(Collectors.toList());

		holidayTime.setHolidayWorkFrameTime(clearTime(holidayTime, (index, type) -> {

			if (type == TypeOverTime.VACATION && holdayIndexRemove.contains(index))
				return true;

			if (type == TypeOverTime.TRANSFER && transIndexRemove.contains(index))
				return true;

			if (type == TypeOverTime.APP && appIndexRemove.contains(index))
				return true;
			return false;
		}));

	}

	public static List<HolidayWorkFrameTime> clearTime(HolidayWorkTimeOfDaily holidayTime,
			BiFunction<Integer, TypeOverTime, Boolean> flagRemove) {
		List<HolidayWorkFrameTime> result = new ArrayList<>();
		for (int i = 0; i < holidayTime.getHolidayWorkFrameTime().size(); i++) {
			HolidayWorkFrameTime data = holidayTime.getHolidayWorkFrameTime().get(i);

			if (flagRemove.apply(i, TypeOverTime.VACATION)) {
				// 日別実績の休出時間．休出枠時間．休出時間(list件数分)
				data.addHolidayTime(new AttendanceTime(0), new AttendanceTime(0));
			}
			if (flagRemove.apply(i, TypeOverTime.TRANSFER)) {
				// 日別実績の休出時間．休出枠時間．振替時間(list件数分)
				data = new HolidayWorkFrameTime(data.getHolidayFrameNo(), data.getHolidayWorkTime(),
						Finally.of(TimeDivergenceWithCalculation.defaultValue()), data.getBeforeApplicationTime());
			}
			if (flagRemove.apply(i, TypeOverTime.APP)) {
				// 日別実績の休出時間．休出枠時間．事前申請時間(list件数分)
				data.setBeforeApplicationTime(Finally.of(new AttendanceTime(0)));
			}
			result.add(data);
		}
		return result;

	}

	@AllArgsConstructor
	public enum TypeOverTime {

		VACATION(0),

		TRANSFER(1),

		APP(2);

		public final int value;

	}

}
