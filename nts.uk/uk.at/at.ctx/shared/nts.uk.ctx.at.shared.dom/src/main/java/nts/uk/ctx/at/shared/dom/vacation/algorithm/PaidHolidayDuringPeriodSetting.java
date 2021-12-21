package nts.uk.ctx.at.shared.dom.vacation.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.adapter.employment.EmploymentHistShareImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;

/**
 * @author ThanhNX
 *
 *         期間内の代休の設定を取得する
 */
public class PaidHolidayDuringPeriodSetting {

	private PaidHolidayDuringPeriodSetting() {
	};

	public static List<TimeLapseVacationSetting> getSetting(Require require, String companyId, String employeeId) {

		// 期間の雇用履歴を全て取得
		List<EmploymentHistShareImport> lstEmpHist = require.findByEmployeeIdOrderByStartDate(employeeId);

		// 代休の設定を取得する
		List<TimeLapseVacationSetting> lstTimeLapVacation = new ArrayList<>();
		lstEmpHist.forEach(x -> {
			lstTimeLapVacation.add(getSettingHoliday(require, companyId, x));
		});

		// 逐次発生の休暇設定（List）を返す
		return lstTimeLapVacation;
	}

	// 代休の設定を取得する
	public static TimeLapseVacationSetting getSettingHoliday(Require require, String companyId,
			EmploymentHistShareImport empHist) {

		// ドメイン「雇用の代休管理設定」を取得する
		CompensatoryLeaveEmSetting comLeavEmp = require.findComLeavEmpSet(companyId, empHist.getEmploymentCode());

		if (comLeavEmp == null) {
			// ドメインモデル「代休管理設定」を取得する
			CompensatoryLeaveComSetting compLeavCom = require.findComLeavComSet(companyId);

			// 逐次発生の休暇設定に代休管理設定を移送する
			return new TimeLapseVacationSetting(empHist.getPeriod(), compLeavCom.getIsManaged().value == 1,
					compLeavCom.getCompensatoryAcquisitionUse().getExpirationTime().value,
					compLeavCom.getCompensatoryAcquisitionUse().getPreemptionPermit().value == 1,
					Optional.of(compLeavCom.getTimeVacationDigestUnit().getManage().value == 1),
					Optional.of(compLeavCom.getTimeVacationDigestUnit().getDigestUnit().value));
		} else {

			//ドメインモデル「代休管理設定」を取得する
			CompensatoryLeaveComSetting compLeavCom = require.findComLeavComSet(companyId);

			// 逐次発生の休暇設定に雇用の代休管理設定を移送する
			// 逐次発生の休暇設定に代休管理設定を移送する
			return new TimeLapseVacationSetting(empHist.getPeriod(), comLeavEmp.getIsManaged().value == 1,
					compLeavCom.getCompensatoryAcquisitionUse().getExpirationTime().value,
					compLeavCom.getCompensatoryAcquisitionUse().getPreemptionPermit().value == 1,
					Optional.of(compLeavCom.getTimeVacationDigestUnit().getManage().value == 1),
					Optional.of(compLeavCom.getTimeVacationDigestUnit().getDigestUnit().value));
			
			
		}
	}

	public static interface Require {

		// EmploymentHistAdapter
		public List<EmploymentHistShareImport> findByEmployeeIdOrderByStartDate(String employeeId);

		// CompensLeaveEmSetRepository.find
		public CompensatoryLeaveEmSetting findComLeavEmpSet(String companyId, String employmentCode);

		// CompensLeaveComSetRepository.find
		public CompensatoryLeaveComSetting findComLeavComSet(String companyId);
	}
}
