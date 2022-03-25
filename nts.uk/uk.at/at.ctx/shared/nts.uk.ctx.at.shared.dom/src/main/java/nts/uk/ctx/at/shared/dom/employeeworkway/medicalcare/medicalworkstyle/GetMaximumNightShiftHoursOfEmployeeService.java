package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 社員の夜勤上限時間を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.社員の働き方.医療介護.医療勤務形態.社員の夜勤上限時間を取得する
 * @author lan_lt
 *
 */
@Stateless
public class GetMaximumNightShiftHoursOfEmployeeService {
	/**
	 * 取得する
	 * @param require
	 * @param sid 社員ID
	 * @param baseDate 基準日
	 * @return
	 */
	public static Optional<AttendanceTimeMonth> get(Require require, String sid, GeneralDate baseDate) {
		Optional<EmpMedicalWorkStyleHistoryItem> medicalWorkHisItem = require.getEmpMedicalWorkFormHisItem(sid, baseDate);

		if (!medicalWorkHisItem.isPresent())
			return Optional.empty();

		NightShiftUpperLimitTime maxNightShiftHours = require.getNightShiftUpperLimitTime();

		if (medicalWorkHisItem.get().isOnlyNightShift())
			return Optional.of(maxNightShiftHours.getNightShiftWorker());

		return Optional.of(maxNightShiftHours.getRegularWorker());
	}

	public static interface Require{
		/**
		 * [R-1] 夜勤上限時間を取得する
		 * @return
		 */
		NightShiftUpperLimitTime getNightShiftUpperLimitTime();
		
		/**
		 * [R-2] 社員の医療勤務形態履歴項目を取得する
		 * @param sid 社員ID
		 * @param baseDate 基準日
		 * @return
		 */
		Optional<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkFormHisItem(String sid, GeneralDate baseDate);
	}
}
