package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.UsedDays;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

/**
 * @author thanh_nx
 *
 *         日別勤怠を補正する
 */
public class CorrectDailyAttendanceService {

	// 補正する
	public static IntegrationOfDaily processAttendanceRule(Require require, IntegrationOfDaily domainDaily,
			ChangeDailyAttendance changeAtt) {
		return require.process(domainDaily, changeAtt);
	}

	// 振休振出として扱う日数を補正する
	public static WorkInfoOfDailyAttendance correctFurikyu(Require require,
			WorkInfoOfDailyAttendance workInformationBefore, WorkInfoOfDailyAttendance workInformationAfter) {

		// 反映前の勤務実績の勤務種類に、[振休]または[振出]が含まれるかのチェック
		Optional<WorkType> workType = require.getWorkType(workInformationBefore.getRecordInfo().getWorkTypeCode().v());

		Optional<WorkType> workTypeAfter = require.getWorkType(workInformationAfter.getRecordInfo().getWorkTypeCode().v());
		if (!workType.isPresent() || !workTypeAfter.isPresent()
				|| (workType.get().getDailyWork().getClassification() != WorkTypeClassification.Shooting
						&& workType.get().getDailyWork().getClassification() != WorkTypeClassification.Pause)) {
			/// どちらも含まれない
			return workInformationAfter;
		}

		Pair<WorkTypeUnit, WorkTypeClassification> classifi = getClassifiAfter(workType.get().getDailyWork(),
				workTypeAfter.get().getDailyWork());
		if (workType.get().getDailyWork().getClassification() == WorkTypeClassification.Shooting
				&& classifi.getRight() != WorkTypeClassification.Holiday) {
			/// 振出が含まれる
			workInformationAfter.setNumberDaySuspension(Optional.of(new NumberOfDaySuspension(
					new UsedDays(classifi.getLeft() == WorkTypeUnit.OneDay ? 1.0 : 0.5), FuriClassifi.DRAWER)));

		}

		if (workType.get().getDailyWork().getClassification() == WorkTypeClassification.Pause
				&& classifi.getRight() != WorkTypeClassification.Attendance) {
			/// 振休が含まれる
			workInformationAfter.setNumberDaySuspension(Optional.of(new NumberOfDaySuspension(
					new UsedDays(classifi.getLeft() == WorkTypeUnit.OneDay ? 1.0 : 0.5), FuriClassifi.SUSPENSION)));
		}

		return workInformationAfter;
	}

	private static Pair<WorkTypeUnit, WorkTypeClassification> getClassifiAfter(DailyWork unitBefore, DailyWork after) {

		if (unitBefore.getWorkTypeUnit() == WorkTypeUnit.OneDay) {
			return Pair.of(WorkTypeUnit.OneDay, after.getOneDay());
		}

		if (unitBefore.getWorkTypeUnit() == WorkTypeUnit.MonringAndAfternoon && !unitBefore.getMorning().isHolidayType()) {
			return Pair.of(WorkTypeUnit.MonringAndAfternoon, after.getMorning());
		}

		return Pair.of(WorkTypeUnit.MonringAndAfternoon, after.getAfternoon());
	}

	public static interface Require extends WorkInformation.Require {

		// CorrectionAttendanceRule
		public IntegrationOfDaily process(IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt);
	}
}
