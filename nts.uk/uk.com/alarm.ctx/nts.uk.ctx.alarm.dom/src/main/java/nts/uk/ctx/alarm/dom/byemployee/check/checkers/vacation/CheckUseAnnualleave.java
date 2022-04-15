package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.RCAnnualHolidayManagement;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.AnnLeaUsedDaysOutput;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.ObligedAnnLeaUseService;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.ObligedAnnualLeaveUse;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.ObligedUseDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 年休取得のチェック条件
 */
public class CheckUseAnnualleave {

	/** 次回の年休付与日が指定した月以内にある人のみチェックする */
	private boolean onlyWithinNextGrantDate;

	/** 次回の年休付与日までの月数 */
	private Optional<Integer> months;

	/** 前回の年休付与日数が指定した日数以上の人のみチェックする */
	private boolean onlyPreviousGrantDaysOver;

	/** 前回の年休付与日数 */
	private Optional<Integer> previousGrantDays;

	/** 前回付与までの期間が１年未満の場合、期間按分する */
	private boolean proportionalDistributionLessThanOneYear;

	/** 年休取得義務日数 */
	private AnnualLeaveUsedDayNumber obligedDays;

	/** メッセージ */
	private AlarmListAlarmMessage message;

	@Inject
	private ObligedAnnLeaUseService obligedAnnLeaUseService;

	@Inject
	private RCAnnualHolidayManagement rcAnnualHolidayManagement;

	/**
	 * 条件に該当するか
	 * @param employeeId
	 * @return
	 */
	public Optional<AlarmRecordByEmployee> check(RequireCheck require, String employeeId) {
		// 次回の年休付与日が指定した月以内にある人のみチェックする
		if(onlyWithinNextGrantDate &&
				require.checkExistHolidayGrantAdapter(employeeId, GeneralDate.today(), new Period(GeneralDate.today(),GeneralDate.today().addMonths(months.get())))){
			return Optional.empty();
		}

		// 年休付与残数データの取得
		val annualLeaveGrantRemain = new ArrayList<AnnualLeaveGrantRemainingData>();
		if(onlyPreviousGrantDaysOver){
			// 指定した付与日数の付与残数データを取得する
			annualLeaveGrantRemain.addAll(require.findAnnualLeaveGrantRemain(employeeId,
					Optional.of(LeaveGrantNumber.of(new LeaveGrantDayNumber(previousGrantDays.get().doubleValue()), Optional.empty()))));
		}else{
			// すべての付与残数データを取得する
			annualLeaveGrantRemain.addAll(require.findAnnualLeaveGrantRemain(employeeId, Optional.empty()));
		}

		// 年休使用義務を生成
		val obligedAnnualLeaveUse = ObligedAnnualLeaveUse.create(employeeId, proportionalDistributionLessThanOneYear, ReferenceAtr.APP_AND_SCHE, obligedDays, annualLeaveGrantRemain);

		// 年休使用義務日数の取得
		val optObligedUseDays = obligedAnnLeaUseService.getObligedUseDays(GeneralDate.today(), obligedAnnualLeaveUse);
		if(!optObligedUseDays.isPresent()){
			// 年休使用義務日数が取得できなかった場合はチェックしない
			return Optional.empty();
		}

		// 義務日数計算期間内の年休使用数を取得
		val annualLeaveUsedDays = obligedAnnLeaUseService.getAnnualLeaveUsedDays(GeneralDate.today(), obligedAnnualLeaveUse);
		// 年休使用日数が使用義務日数を満たしているかチェックする
		if(!obligedAnnLeaUseService.checkObligedUseDays(optObligedUseDays.get(), annualLeaveUsedDays)){
			return Optional.of(alarm(employeeId));
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

	public interface RequireCheck {
		boolean checkExistHolidayGrantAdapter(String employeeId, GeneralDate date, Period period);

		List<AnnualLeaveGrantRemainingData> findAnnualLeaveGrantRemain(String employeeId, Optional<LeaveGrantNumber> grantDays);

	}
}
