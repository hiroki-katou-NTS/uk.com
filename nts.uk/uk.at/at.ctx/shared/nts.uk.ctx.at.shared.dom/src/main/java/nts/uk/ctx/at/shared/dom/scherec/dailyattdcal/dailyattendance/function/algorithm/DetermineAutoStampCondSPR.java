package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;

/**
 * @author ThanhNX
 *
 *         SPR連携の自動打刻条件を判断
 */
public class DetermineAutoStampCondSPR {

	public static boolean determine(TimeLeavingOfDailyAttd timeLeave) {

		List<TimeLeavingWork> lstTimeLeaving = timeLeave.getTimeLeavingWorks().stream().filter(x -> {
			return x.getAttendanceStamp().isPresent() && x.getAttendanceStamp().get().getActualStamp().isPresent()
					&& x.getAttendanceStamp().get().getActualStamp().get().getTimeDay().getReasonTimeChange()
							.getTimeChangeMeans() == TimeChangeMeans.SPR_COOPERATION
					|| x.getAttendanceStamp().isPresent() && x.getAttendanceStamp().get().getActualStamp().isPresent()
							&& x.getAttendanceStamp().get().getActualStamp().get().getTimeDay().getReasonTimeChange()
									.getTimeChangeMeans() == TimeChangeMeans.SPR_COOPERATION;
		}).collect(Collectors.toList());
		return lstTimeLeaving.isEmpty();

	}
}
