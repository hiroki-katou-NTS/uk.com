package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;

import java.util.Optional;

/**
 * 年休取得のチェック条件
 */
public class CheckUseAnnualleave {

	/** 使用するか */
	boolean enabled;

	/** 次回の年休付与日が指定した月以内にある人のみチェックする */
	private boolean onlyWithinNextGrantDate;

	/** 次回の年休付与日までの月数 */
	private Optional<Integer> months;

	/** 年休取得義務 */
	private ObligedUseAnnualLeave obligedUseAnnualLeave;

	/** メッセージ */
	private AlarmListAlarmMessage message;

	/**
	 * 条件に該当するか
	 * @param employeeId
	 * @return
	 */
	public Optional<AlarmRecordByEmployee> checkIfEnabled(RequireCheck require, String employeeId) {
		if (enabled) {
			// 次回の年休付与日が指定した月以内にある人のみチェックする
			if(onlyWithinNextGrantDate &&
				require.checkExistHolidayGrantAdapter(employeeId, GeneralDate.today(), new Period(GeneralDate.today(),GeneralDate.today().addMonths(months.get())))){
				return Optional.empty();
			}
			if(!obligedUseAnnualLeave.check(require, employeeId)) {
				return Optional.of(alarm(employeeId));
			}
		}
		return Optional.empty();
	}

	private AlarmRecordByEmployee alarm(String employeeId) {
		return new AlarmRecordByEmployee(
				employeeId,
				DateInfo.none(),
				AlarmListCategoryByEmployee.VACATION,
				"",
				"",
				"",
				message);
	}

	public interface RequireCheck extends ObligedUseAnnualLeave.Require {
		boolean checkExistHolidayGrantAdapter(String employeeId, GeneralDate date, Period period);

	}
}
