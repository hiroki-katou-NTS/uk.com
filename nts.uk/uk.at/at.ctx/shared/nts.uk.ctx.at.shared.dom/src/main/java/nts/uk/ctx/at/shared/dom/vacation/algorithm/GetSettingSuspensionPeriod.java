package nts.uk.ctx.at.shared.dom.vacation.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmploymentHistShareImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;

/**
 * @author ThanhNX
 *
 *         期間内の振休の設定を取得する
 */
public class GetSettingSuspensionPeriod {

	public static List<TimeLapseVacationSetting> getSetting(Require require, String companyId, String employeeId) {

		List<TimeLapseVacationSetting> result = new ArrayList<>();
		// 期間の雇用履歴を全て取得
		List<EmploymentHistShareImport> lstEmpHist = require.findByEmployeeIdOrderByStartDate(employeeId);

		for (EmploymentHistShareImport empHist : lstEmpHist) {

			// 振休設定を取得する
			getSuspensionSettings(require, companyId, empHist.getEmploymentCode(), empHist.getPeriod())
					.ifPresent(x -> result.add(x));
		}

		// 期間と振休設定を返す
		return result;

	}

	// 振休設定を取得する
	public static Optional<TimeLapseVacationSetting> getSuspensionSettings(Require require, String companyId,
			String employmentCode, DatePeriod period) {

		// ドメインモデル「雇用振休管理設定」を取得する
		EmpSubstVacation empSub = require.findEmpById(companyId, employmentCode).orElse(null);

		if (empSub != null) {
			/*// 逐次発生の休暇設定に雇用振休管理設定を移送する
			return Optional.of(new TimeLapseVacationSetting(period, empSub.getSetting().getIsManage().value == 1,
					empSub.getSetting().getExpirationDate().value,
					empSub.getSetting().getAllowPrepaidLeave().value == 1, Optional.empty(), Optional.empty()));*/

		} else {
			// ドメインモデル「振休管理設定」を取得する
			ComSubstVacation comSub = require.findComById(companyId).orElse(null);

			if (comSub == null)
				return Optional.empty();

			// 逐次発生の休暇設定に振休管理設定を移送する
			/*return Optional.of(new TimeLapseVacationSetting(period, comSub.getSetting().getIsManage().value == 1,
					comSub.getSetting().getExpirationDate().value,
					comSub.getSetting().getAllowPrepaidLeave().value == 1, Optional.empty(), Optional.empty()));*/
		}
		return null;

	}

	public static interface Require {

		// EmploymentHistAdapter
		public List<EmploymentHistShareImport> findByEmployeeIdOrderByStartDate(String employeeId);

		// EmpSubstVacationRepository.findById
		public Optional<EmpSubstVacation> findEmpById(String companyId, String contractTypeCode);

		// ComSubstVacationRepository.findById
		public Optional<ComSubstVacation> findComById(String companyId);
	}
}
