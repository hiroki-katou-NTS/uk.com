package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm.aftercorrectatt;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.timestamp.ChangeAttendanceTimeStamp;

/**
 * @author thanh_nx
 *
 *         日別実績の出退勤を変更する
 */
@Stateless
public class ChangeDailyAttendanceProcess {

	@Inject
	private ChangeAttendanceTimeStamp changeAttendanceTimeStamp;

	public List<TimeLeavingWork> changeDaily(String companyId, List<TimeLeavingWork> lstTimeLeavNew,
			List<TimeLeavingWork> lstTimeLeav) {

		// INPUT．「出退勤（List）」をループする
		for (TimeLeavingWork newData : lstTimeLeavNew) {

			// INPUT．「日別実績の出退勤．出退勤．出勤．打刻」を確認する
			Optional<TimeLeavingWork> timeLeavOpt = lstTimeLeav.stream()
					.filter(x -> x.getWorkNo().v() == newData.getWorkNo().v()).findFirst();
			if (!timeLeavOpt.isPresent()) {
				// 「日別実績の出退勤．出退勤」に処理中の「出退勤」を追加する
				lstTimeLeav.add(newData);
				continue;
			}

			// INPUT．「日別実績の出退勤．出退勤．出勤．打刻」を確認する
			if (checkHasAtt(timeLeavOpt.get())) {
				// 勤怠打刻を変更する
				changeAttendanceTimeStamp.change(companyId,
						timeLeavOpt.get().getAttendanceStamp().get().getStamp().get(),
						newData.getAttendanceStamp().get().getStamp().get());
			} else {
				// 処理中の「出退勤．出勤．打刻」 をセットする
				if (checkHasTimeDayAtt(timeLeavOpt.get()) && checkHasTimeDayAtt(newData))
					timeLeavOpt.get().getAttendanceStamp().get().getStamp().get().getTimeDay()
							.setTimeWithDay(Optional.of(newData.getAttendanceStamp().get().getStamp().get().getTimeDay()
									.getTimeWithDay().get()));
			}

			if (checkHasLeav(timeLeavOpt.get())) {
				// 勤怠打刻を変更する
				changeAttendanceTimeStamp.change(companyId, timeLeavOpt.get().getLeaveStamp().get().getStamp().get(),
						newData.getLeaveStamp().get().getStamp().get());
			} else {
				// 処理中の「出退勤．出勤．打刻」 をセットする
				if (checkHasTimeDayLeav(timeLeavOpt.get()) && checkHasTimeDayLeav(newData))
					timeLeavOpt.get().getLeaveStamp().get().getStamp().get().getTimeDay().setTimeWithDay(Optional
							.of(newData.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get()));
			}
		}

		return lstTimeLeav;
	}

	public boolean checkHasAtt(TimeLeavingWork timeLeav) {
		return checkHasTimeDayAtt(timeLeav)
				&& timeLeav.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent();
	}

	public boolean checkHasLeav(TimeLeavingWork timeLeav) {
		return checkHasTimeDayLeav(timeLeav)
				&& timeLeav.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent();
	}

	private boolean checkHasTimeDayAtt(TimeLeavingWork timeLeav) {
		return timeLeav.getAttendanceStamp().isPresent() && timeLeav.getAttendanceStamp().get().getStamp().isPresent()
				&& timeLeav.getAttendanceStamp().get().getStamp().isPresent();
	}

	private boolean checkHasTimeDayLeav(TimeLeavingWork timeLeav) {
		return timeLeav.getLeaveStamp().isPresent() && timeLeav.getLeaveStamp().get().getStamp().isPresent();
	}
}
