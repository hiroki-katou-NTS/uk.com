package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm.aftercorrectatt;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;

/**
 * @author ThanhNX
 *
 *         直行直帰による、戻り時刻補正
 */
public class ReturnDirectTimeCorrection {

	// 直行直帰による、戻り時刻補正
	public static Optional<OutingTimeOfDailyAttd> process(String comapnyId, TimeLeavingOfDailyAttd attendanceLeave,
			Optional<OutingTimeOfDailyAttd> outingTime) {
		// 退勤が直行直帰の出退勤を取得する
		// domain not map
		List<TimeLeavingWork> lstTimeLeaving = attendanceLeave.getTimeLeavingWorks().stream().filter(x -> {
			return x.getAttendanceStamp().isPresent() && x.getAttendanceStamp().get().getActualStamp().isPresent()
					&& x.getAttendanceStamp().get().getActualStamp().get().getTimeDay().getReasonTimeChange()
							.getTimeChangeMeans() == TimeChangeMeans.DIRECT_BOUNCE
					|| x.getAttendanceStamp().isPresent() && x.getAttendanceStamp().get().getActualStamp().isPresent()
							&& x.getAttendanceStamp().get().getActualStamp().get().getTimeDay().getReasonTimeChange()
									.getTimeChangeMeans() == TimeChangeMeans.DIRECT_BOUNCE_APPLICATION;
		}).collect(Collectors.toList());

		if (!outingTime.isPresent())
			return outingTime;

		List<OutingTimeSheet> outingTimeSheets = outingTime.get().getOutingTimeSheets();
		for (TimeLeavingWork leavWork : lstTimeLeaving) {
			Integer timeAttendance = (leavWork.getAttendanceStamp().isPresent()
					&& leavWork.getAttendanceStamp().get().getActualStamp().isPresent()
					&& leavWork.getAttendanceStamp().get().getActualStamp().get().getTimeDay().getTimeWithDay()
							.isPresent())
									? leavWork.getAttendanceStamp().get().getActualStamp().get().getTimeDay()
											.getTimeWithDay().get().v()
									: null;

			Integer timeLeave = (leavWork.getLeaveStamp().isPresent()
					&& leavWork.getLeaveStamp().get().getActualStamp().isPresent()
					&& leavWork.getLeaveStamp().get().getActualStamp().get().getTimeDay().getTimeWithDay().isPresent())
							? leavWork.getLeaveStamp().get().getActualStamp().get().getTimeDay().getTimeWithDay().get()
									.v()
							: null;

			// 出勤～退勤の中で一番最後の外出時間帯を取得
			// 補正対象かどうか判断
			// 戻り時刻を退勤時刻で補正する
			Optional<OutingTimeSheet> outingTimeSheet = outingTime.get().getOutingTimeSheets().stream()
					.filter(x -> !x.getComeBack().isPresent() && x.getGoOut().isPresent()
							&& x.getGoOut().get().getActualStamp().isPresent()
							&& x.getGoOut().get().getActualStamp().get().getTimeDay().getTimeWithDay().isPresent()
							&& inRange(
									x.getGoOut().get().getActualStamp().get().getTimeDay().getTimeWithDay().get().v(),
									timeAttendance, timeLeave))
					.sorted((x, y) -> compareTime(x, y)).findFirst();

			outingTimeSheet.ifPresent(x -> {
				outingTimeSheets.remove(outingTimeSheet.get());
				outingTimeSheets.add(setOutTimeSheet(x, leavWork));
			});
		}
		return Optional.ofNullable(outingTimeSheets.isEmpty() ? null : new OutingTimeOfDailyAttd(outingTimeSheets));
	}

	private static boolean inRange(Integer check, Integer min, Integer max) {
		if (min == null || max == null)
			return false;
		return check >= min && check <= max;
	}

	private static Integer compareTime(OutingTimeSheet goOut1, OutingTimeSheet goOut2) {
		return goOut2.getGoOut().get().getActualStamp().get().getTimeDay().getTimeWithDay().get().v()
				- goOut1.getGoOut().get().getActualStamp().get().getTimeDay().getTimeWithDay().get().v();
	}

	private static OutingTimeSheet setOutTimeSheet(OutingTimeSheet oldValue, TimeLeavingWork leavWork) {
		return new OutingTimeSheet(oldValue.getOutingFrameNo(), oldValue.getGoOut(),
				oldValue.getOutingTimeCalculation(), oldValue.getOutingTime(), oldValue.getReasonForGoOut(),
				leavWork.getLeaveStamp());
	}
}
