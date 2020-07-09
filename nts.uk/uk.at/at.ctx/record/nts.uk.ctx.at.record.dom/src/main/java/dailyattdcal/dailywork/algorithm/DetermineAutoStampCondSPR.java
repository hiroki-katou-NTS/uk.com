package dailyattdcal.dailywork.algorithm;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;

/**
 * @author ThanhNX
 *
 *         SPR連携の自動打刻条件を判断
 */
public class DetermineAutoStampCondSPR {

	public static boolean determine(TimeLeavingOfDailyPerformance timeLeave) {

		List<TimeLeavingWork> lstTimeLeaving = timeLeave.getTimeLeavingWorks().stream().filter(x -> {
			return x.getAttendanceStamp().isPresent() && x.getAttendanceStamp().get().getActualStamp().isPresent()
					&& x.getAttendanceStamp().get().getActualStamp().get().getStampSourceInfo() == StampSourceInfo.SPR
					|| x.getAttendanceStamp().isPresent() && x.getAttendanceStamp().get().getActualStamp().isPresent()
							&& x.getAttendanceStamp().get().getActualStamp().get()
									.getStampSourceInfo() == StampSourceInfo.SPR;
		}).collect(Collectors.toList());
		return lstTimeLeaving.isEmpty();

	}
}
