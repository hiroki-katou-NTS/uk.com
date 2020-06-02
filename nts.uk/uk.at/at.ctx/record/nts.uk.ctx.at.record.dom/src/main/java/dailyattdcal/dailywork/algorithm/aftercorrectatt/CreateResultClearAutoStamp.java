package dailyattdcal.dailywork.algorithm.aftercorrectatt;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import dailyattdcal.dailywork.algorithm.DetermineClassifiByWorkInfoCond.AutoStampSetClassifi;
import dailyattdcal.dailywork.algorithm.common.ClearAutomaticStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;

/**
 * @author ThanhNX
 *
 *         自動打刻をクリアした結果を作成する
 */
@Stateless
public class CreateResultClearAutoStamp {

	@Inject
	private ClearAutomaticStamp clearAutomaticStamp;

	// 自動打刻をクリアした結果を作成する
	public void create(AutoStampSetClassifi autoStampSetClassifi, String workTypeCode,
			Optional<TimeLeavingOfDailyPerformance> attendanceLeave) {

		if (!attendanceLeave.isPresent())
			return;

		for (TimeLeavingWork timeLeave : attendanceLeave.get().getTimeLeavingWorks()) {

			// 出勤反映を確認
			if (autoStampSetClassifi.getAttendanceReflect() != NotUseAtr.USE
					&& timeLeave.getAttendanceStamp().isPresent()) {
				// 自動打刻をクリアする
				TimeActualStamp timeActualStamp = clearAutomaticStamp.clear(workTypeCode,
						timeLeave.getAttendanceStamp().get());
				timeLeave.setTimeLeavingWork(timeLeave.getWorkNo(), Optional.of(timeActualStamp),
						timeLeave.getLeaveStamp());
			}

			// 退勤反映を確認
			if (autoStampSetClassifi.getLeaveWorkReflect() != NotUseAtr.USE && timeLeave.getLeaveStamp().isPresent()) {
				// 自動打刻をクリアする
				TimeActualStamp timeActualStamp = clearAutomaticStamp.clear(workTypeCode,
						timeLeave.getLeaveStamp().get());
				timeLeave.setTimeLeavingWork(timeLeave.getWorkNo(), timeLeave.getAttendanceStamp(),
						Optional.of(timeActualStamp));
			}

		}

		// 変更後の日別実績の出退勤を返す
	}
}
