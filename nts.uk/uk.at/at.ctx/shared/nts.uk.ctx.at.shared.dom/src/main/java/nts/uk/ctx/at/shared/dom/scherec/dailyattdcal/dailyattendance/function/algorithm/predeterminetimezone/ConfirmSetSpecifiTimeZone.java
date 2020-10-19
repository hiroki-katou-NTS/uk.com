package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.predeterminetimezone;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.calculationsetting.query.DetermineAutoSetFutureDayStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.DetermineClassifiByWorkInfoCond;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.DetermineClassifiByWorkInfoCond.AutoStampSetClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.JudgeHdSystemOneDayService;

/**
 * @author ThanhNX
 *
 *         所定時間帯をセットするか確認する
 */
@Stateless
public class ConfirmSetSpecifiTimeZone {

	@Inject
	private DetermineAutoSetFutureDayStamp determineAutoSetFutureDayStamp;

	@Inject
	private JudgeHdSystemOneDayService judgeHdSystemOneDayService;

	// 所定時間帯をセットするか確認する
	public ConfirmSetSpecifiResult confirmset(String companyId, WorkingConditionItem workCondItem,
			WorkInfoOfDailyAttendance workInformation, Optional<TimeLeavingOfDailyAttd> attendanceLeave,
			GeneralDate date) {

		// 勤務情報と労働条件元に直行直帰区分を判断する
		AutoStampSetClassifi autoStampClassifi = DetermineClassifiByWorkInfoCond.determine(workCondItem,
				workInformation);

		if (!autoStampClassifi.isReflect()) {
			return new ConfirmSetSpecifiResult();
		}
		// 未来日の打刻を自動セットするか判断する
		boolean checkAutoSetFuture = determineAutoSetFutureDayStamp.determine(companyId, date);

		if (!checkAutoSetFuture) {
			return new ConfirmSetSpecifiResult();
		}
		// 1日半日出勤・1日休日系の判定
		AttendanceHolidayAttr attHolidayAttr = judgeHdSystemOneDayService
				.judgeHdOnDayWorkPer(workInformation.getRecordInfo().getWorkTypeCode().v());

		if (attHolidayAttr == AttendanceHolidayAttr.HOLIDAY) {
			return new ConfirmSetSpecifiResult();
		}

//		if (!attendanceLeave.isPresent())
//			return new ConfirmSetSpecifiResult();
//		// SPR連携の自動打刻条件を判断
//		boolean checkSpr = DetermineAutoStampCondSPR.determine(attendanceLeave.get());
//		if (!checkSpr)
//			return new ConfirmSetSpecifiResult();

		return new ConfirmSetSpecifiResult(true, Optional.of(autoStampClassifi));
	}

	@AllArgsConstructor
	@Data
	public static class ConfirmSetSpecifiResult {

		// 時刻セット区分
		private boolean timeSetClassification;

		// 自動打刻セット区分
		private Optional<AutoStampSetClassifi> autoStampSetClassifi;

		public ConfirmSetSpecifiResult() {
			this.timeSetClassification = false;
			this.autoStampSetClassifi = Optional.empty();
		}

	}
}
