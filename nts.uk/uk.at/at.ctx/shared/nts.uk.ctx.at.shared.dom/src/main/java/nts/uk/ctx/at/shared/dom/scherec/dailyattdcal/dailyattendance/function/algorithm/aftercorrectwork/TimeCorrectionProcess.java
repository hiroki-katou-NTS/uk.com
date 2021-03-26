package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectwork;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt.AutoCorrectStampOfTimeZone;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt.ClearConflictTimeWithDay;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * @author thanh_nx
 *
 *         時刻の補正
 */
@Stateless
public class TimeCorrectionProcess {

	@Inject
	private AutoCorrectStampOfTimeZone autoCorrectStamp;

	@Inject
	private ClearConflictTimeWithDay clearConflictTimeWithDay;

	public void process(String cid, Optional<WorkingConditionItem> workCondition, IntegrationOfDaily domainDaily,
			ScheduleRecordClassifi classification) {

		if (!workCondition.isPresent()) {
			return;
		}
		String employeeId = domainDaily.getEmployeeId();

		GeneralDate date = domainDaily.getYmd();

		// 予定実績区分をチェクする
		if (classification == ScheduleRecordClassifi.RECORD) {
			// 自動打刻セットの時間帯補正
			domainDaily = autoCorrectStamp.autoCorrect(cid, domainDaily, workCondition.get());
		}

		// 矛盾した時刻をクリアする
		clearConflictTimeWithDay.clear(cid, employeeId, date, workCondition.get(), domainDaily.getWorkInformation(),
				domainDaily.getAttendanceLeave(), domainDaily.getEditState());

	}

}
