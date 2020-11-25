package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.predeterminetimezone.ConfirmSetSpecifiTimeZone;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.predeterminetimezone.ConfirmSetSpecifiTimeZone.ConfirmSetSpecifiResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.functionalgorithm.SetTimeOfAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * @author thanh_nx
 *
 *         自動打刻セットする
 */
@Stateless
public class AutoStampSettingProcess {

	@Inject
	private ConfirmSetSpecifiTimeZone confirmSetSpecifiTimeZone;

	@Inject
	private SetTimeOfAttendance setTimeOfAttendance;

	@Inject
	private ChangeDailyAttendanceProcess changeDailyAttendanceProcess;

	public TimeLeavingOfDailyAttd process(String companyId, GeneralDate date, WorkingConditionItem workingCond,
			WorkInfoOfDailyAttendance workInfo, TimeLeavingOfDailyAttd timeLeavingOptional) {

		// 所定時間帯をセットするか確認する
		ConfirmSetSpecifiResult confirmSetSpecifiResult = confirmSetSpecifiTimeZone.confirmset(companyId, workingCond,
				workInfo, Optional.ofNullable(timeLeavingOptional), date);

		// 返ってきた「時刻セット区分」を確認する
		if (!confirmSetSpecifiResult.isTimeSetClassification()) {
			// INPUT．「日別実績の出退勤」を返す
			return timeLeavingOptional;
		}

		// 出退勤の時刻をセットする
		List<TimeLeavingWork> lstTimeLeavNew = setTimeOfAttendance.process(companyId, workInfo,
				confirmSetSpecifiResult.getAutoStampSetClassifi().get());

		// 日別実績の出退勤を変更する
		List<TimeLeavingWork> lstTimeLeavResult = changeDailyAttendanceProcess.changeDaily(companyId, lstTimeLeavNew,
				timeLeavingOptional == null ? new ArrayList<>() : timeLeavingOptional.getTimeLeavingWorks());

		// 返ってきた「日別実績の出退勤」を返す
		if (timeLeavingOptional != null) {
			timeLeavingOptional.setTimeLeavingWorks(lstTimeLeavResult);
			return timeLeavingOptional;
		}
		TimeLeavingOfDailyAttd attd = new TimeLeavingOfDailyAttd(lstTimeLeavResult, new WorkTimes(0));
		attd.setCountWorkTime();
		return attd;
	}

}
