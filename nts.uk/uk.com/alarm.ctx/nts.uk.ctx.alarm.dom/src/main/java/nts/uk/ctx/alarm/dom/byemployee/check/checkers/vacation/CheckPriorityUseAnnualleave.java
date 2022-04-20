package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.request.dom.application.annualholiday.ReNumAnnLeaReferenceDateExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaveImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * 年休優先使用のチェック条件
 */
public class CheckPriorityUseAnnualleave {

	/** 使用するか */
	boolean enabled;

	/** 抽出対象の勤務種類 */
	private List<WorkTypeCode> workTypeList;

	/** メッセージ */
	private AlarmListAlarmMessage message;

	/**
	 * 条件に該当するか
	 * @param employeeId
	 * @return
	 */
	public Iterable<AlarmRecordByEmployee> checkIfEnabled(RequireCheck require, String employeeId, DatePeriod period) {
		if(enabled) {
			val prospect = require.getIntegrationOfDailyProspect(employeeId, period);
			return () -> prospect.stream()
					// 年休残数>0　かつ　指定の勤務種類を使用している場合
					.filter(p -> require.getReferDateAnnualLeaveRemain(p.getEmployeeId(), p.getYmd()).getRemainingDays().doubleValue() > 0 &&
							workTypeList.contains(p.getWorkInformation().getRecordInfo().getWorkTypeCode()))
					.map(p -> alarm(p.getEmployeeId(), p.getYmd()))
					.iterator();
		}
		return Collections.emptyList();
	}

	private AlarmRecordByEmployee alarm(String employeeId, GeneralDate date) {
		return new AlarmRecordByEmployee(
				employeeId,
				new DateInfo(date),
				AlarmListCategoryByEmployee.VACATION,
				"",
				"",
				"",
				message);
	}

	public interface RequireCheck {
		ReNumAnnLeaReferenceDateExport getReferDateAnnualLeaveRemain(String employeeID, GeneralDate date);

		List<IntegrationOfDaily> getIntegrationOfDailyProspect(String employeeId, DatePeriod period);
	}
}
