package dailyattdcal.dailywork.algorithm.aftercorrectatt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;

/**
 * @author ThanhNX
 *
 *         直行直帰による、戻り時刻補正
 */
public class ReturnDirectTimeCorrection {

	// 直行直帰による、戻り時刻補正
	public static Optional<OutingTimeOfDailyPerformance> process(TimeLeavingOfDailyPerformance attendanceLeave,
			Optional<OutingTimeOfDailyPerformance> outingTime) {
		// 退勤が直行直帰の出退勤を取得する
		// domain not map
		List<TimeLeavingWork> lstTimeLeaving = attendanceLeave.getTimeLeavingWorks().stream().filter(x -> {
			return x.getAttendanceStamp().isPresent() && x.getAttendanceStamp().get().getActualStamp().isPresent()
					&& x.getAttendanceStamp().get().getActualStamp().get()
							.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT
					|| x.getAttendanceStamp().isPresent() && x.getAttendanceStamp().get().getActualStamp().isPresent()
							&& x.getAttendanceStamp().get().getActualStamp().get()
									.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION;
		}).collect(Collectors.toList());

		if (!outingTime.isPresent())
			return outingTime;

		List<OutingTimeSheet> outingTimeSheets = new ArrayList<>();
		for (TimeLeavingWork leavWork : lstTimeLeaving) {
			Integer timeAttendance = (leavWork.getAttendanceStamp().isPresent()
					&& leavWork.getAttendanceStamp().get().getActualStamp().isPresent())
							? leavWork.getAttendanceStamp().get().getActualStamp().get().getTimeWithDay().v()
							: null;

			Integer timeLeave = (leavWork.getLeaveStamp().isPresent()
					&& leavWork.getLeaveStamp().get().getActualStamp().isPresent())
							? leavWork.getLeaveStamp().get().getActualStamp().get().getTimeWithDay().v()
							: null;

			// 出勤～退勤の中で一番最後の外出時間帯を取得
			// 補正対象かどうか判断
			// 戻り時刻を退勤時刻で補正する
			Optional<OutingTimeSheet> outingTimeSheet = outingTime.get().getOutingTimeSheets().stream()
					.filter(x -> !x.getComeBack().isPresent() && x.getGoOut().isPresent()
							&& x.getGoOut().get().getActualStamp().isPresent()
							&& inRange(x.getGoOut().get().getActualStamp().get().getTimeWithDay().v(), timeAttendance,
									timeLeave))
					.sorted((x, y) -> compareTime(x, y)).findFirst();

			outingTimeSheet.ifPresent(x -> outingTimeSheets.add(setOutTimeSheet(x, leavWork)));
		}
		return Optional.ofNullable(outingTimeSheets.isEmpty() ? null
				: new OutingTimeOfDailyPerformance(outingTime.get().getEmployeeId(), outingTime.get().getYmd(),
						outingTimeSheets));
	}

	private static boolean inRange(Integer check, Integer min, Integer max) {
		if (min == null || max == null)
			return false;
		return check >= min && check <= max;
	}

	private static Integer compareTime(OutingTimeSheet goOut1, OutingTimeSheet goOut2) {
		return goOut2.getGoOut().get().getActualStamp().get().getTimeWithDay().v()
				- goOut1.getGoOut().get().getActualStamp().get().getTimeWithDay().v();
	}

	private static OutingTimeSheet setOutTimeSheet(OutingTimeSheet oldValue, TimeLeavingWork leavWork) {
		return new OutingTimeSheet(oldValue.getOutingFrameNo(), oldValue.getGoOut(),
				oldValue.getOutingTimeCalculation(), oldValue.getOutingTime(), oldValue.getReasonForGoOut(),
				leavWork.getLeaveStamp());
	}
}
