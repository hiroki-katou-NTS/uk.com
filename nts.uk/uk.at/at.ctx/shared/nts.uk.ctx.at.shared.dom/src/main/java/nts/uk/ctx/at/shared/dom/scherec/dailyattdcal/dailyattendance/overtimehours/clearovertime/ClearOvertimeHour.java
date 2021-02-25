package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTime;

/**
 * @author ThanhNX
 *
 *         残業時間のクリア
 */
public class ClearOvertimeHour {

	private static List<Integer> OVERTIME_ID = Arrays.asList(216, 221, 226, 231, 236, 241, 246, 251, 256, 261);

	private static List<Integer> TRANSFERTIME_ID = Arrays.asList(217, 222, 227, 232, 237, 242, 247, 252, 257, 262);

	private static List<Integer> APP_ID = Arrays.asList(220, 225, 230, 235, 240, 245, 250, 255, 260, 265);

	private static List<Integer> FLEX_ID = Arrays.asList(556, 557, 788);

	private static List<Integer> FLEXAPP_ID = Arrays.asList(555);

	// 残業時間のクリア
	public static void clearOvertime(OverTimeOfDaily overTime, List<EditStateOfDailyAttd> lstEditState) {

		List<Integer> lstIstEdit = lstEditState.stream().map(x -> x.getAttendanceItemId()).collect(Collectors.toList());
		// 残業時間をクリア
		List<Integer> overtimeIndexRemove = OVERTIME_ID.stream().filter(x -> !lstIstEdit.contains(x))
				.map(x -> ((x - 216) / 5)).collect(Collectors.toList());

		List<Integer> transIndexRemove = TRANSFERTIME_ID.stream().filter(x -> !lstIstEdit.contains(x))
				.map(x -> ((x - 217) / 5)).collect(Collectors.toList());

		List<Integer> appIndexRemove = APP_ID.stream().filter(x -> !lstIstEdit.contains(x)).map(x -> ((x - 220) / 5))
				.collect(Collectors.toList());

		List<Integer> flexIndexRemove = FLEX_ID.stream().filter(x -> !lstIstEdit.contains(x))
				.collect(Collectors.toList());

		List<Integer> flexAppIndexRemove = FLEXAPP_ID.stream().filter(x -> !lstIstEdit.contains(x))
				.collect(Collectors.toList());

		clearTime(overTime, (index, type) -> {

			if (type == TypeOverTime.OVERTIME && overtimeIndexRemove.contains(index))
				return true;

			if (type == TypeOverTime.TRANSFER && transIndexRemove.contains(index))
				return true;

			if (type == TypeOverTime.APP && appIndexRemove.contains(index))
				return true;

			if (type == TypeOverTime.FLEX && !flexIndexRemove.isEmpty())
				return true;

			if (type == TypeOverTime.FLEX_APP && !flexAppIndexRemove.isEmpty())

				return true;

			return false;
		});

		// 該当する編集状態を削除

	}

	public static void clearTime(OverTimeOfDaily overTime, BiFunction<Integer, TypeOverTime, Boolean> flagRemove) {

		for (int i = 0; i < overTime.getOverTimeWorkFrameTime().size(); i++) {
			OverTimeFrameTime data = overTime.getOverTimeWorkFrameTime().get(i);

			if (flagRemove.apply(i, TypeOverTime.OVERTIME)) {
				// 日別実績の残業時間．残業枠時間．残業時間(list件数分)
				data.setOverTimeWork(TimeDivergenceWithCalculation.defaultValue());
			}
			if (flagRemove.apply(i, TypeOverTime.TRANSFER)) {
				// 日別実績の残業時間．残業枠時間．振替時間 (list件数分)
				data.setTransferTime(TimeDivergenceWithCalculation.defaultValue());
			}
			if (flagRemove.apply(i, TypeOverTime.APP)) {
				// 日別実績の残業時間．残業枠時間．事前申請時間(list件数分)
				data.setBeforeApplicationTime(new AttendanceTime(0));
			}
		}

		// 日別実績の残業時間．フレックス時間．フレックス時間
		// 日別実績の残業時間．フレックス時間．事前申請時間
		if (flagRemove.apply(0, TypeOverTime.FLEX)) {
			overTime.setFlexTime(
					new FlexTime(TimeDivergenceWithCalculationMinusExist.sameTime(new AttendanceTimeOfExistMinus(0)),
							overTime.getFlexTime().getBeforeApplicationTime()));
		}

		if (flagRemove.apply(0, TypeOverTime.FLEX_APP)) {
			overTime.setFlexTime(new FlexTime(overTime.getFlexTime().getFlexTime(), new AttendanceTime(0)));
		}

	}

	@AllArgsConstructor
	public enum TypeOverTime {

		OVERTIME(0),

		TRANSFER(1),

		APP(2),

		FLEX(3),

		FLEX_APP(4);

		public final int value;

	}

}
