package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;

public class SettingSubstituteHolidayProcess {

	/**
	 * 10-2.代休の設定を取得する refactor
	 * 
	 * @param companyID
	 * @param employeeID
	 * @param baseDate
	 * @return
	 */
	public static SubstitutionHolidayOutput getSettingForSubstituteHoliday(Require require, String companyID,
			String employeeID, GeneralDate baseDate) {
		SubstitutionHolidayOutput result = new SubstitutionHolidayOutput();
		// アルゴリズム「社員所属雇用履歴を取得」を実行する
		Optional<BsEmploymentHistoryImport> empHistImport = require.findEmploymentHistory(companyID, employeeID,
				baseDate);
		if (!empHistImport.isPresent() || empHistImport.get().getEmploymentCode() == null) {
			return null;
		}
		int isManageByTime = 0;
		int digestiveUnit = 0;
		// ドメインモデル「雇用の代休管理設定」を取得する(lấy domain 「雇用の代休管理設定」)
		CompensatoryLeaveEmSetting compensatoryLeaveEmSet = require.findComLeavEmpSet(companyID,
				empHistImport.get().getEmploymentCode());

		// １件以上取得できた(lấy được 1 data trở lên)
		if (compensatoryLeaveEmSet != null) {
			// ドメインモデル「雇用の代休管理設定」．管理区分 = 管理しない
			if (compensatoryLeaveEmSet.getIsManaged() != null
					&& (compensatoryLeaveEmSet.getIsManaged().equals(ManageDistinct.NO))) {
				result.setSubstitutionFlg(false);
				result.setTimeOfPeriodFlg(false);
				return result;
			}
		}
		// ０件(0 data) or ドメインモデル「雇用の代休管理設定」．管理区分 = 管理する
		if (compensatoryLeaveEmSet == null || (compensatoryLeaveEmSet.getIsManaged() != null
				&& (compensatoryLeaveEmSet.getIsManaged().equals(ManageDistinct.YES)))) {
			// ドメインモデル「代休管理設定」を取得する
			CompensatoryLeaveComSetting compensatoryLeaveComSet = require.findComLeavComSet(companyID);
			if (compensatoryLeaveComSet == null || !compensatoryLeaveComSet.isManaged()) {
				// ドメインモデル「代休管理設定」．管理区分 = 管理しない
				result.setSubstitutionFlg(false);
				result.setTimeOfPeriodFlg(false);
				return result;
			}
			if (compensatoryLeaveComSet != null && compensatoryLeaveComSet.isManaged()) {
				// ドメインモデル「代休管理設定」．管理区分 = 管理する
				result.setSubstitutionFlg(true);
				result.setExpirationOfsubstiHoliday(
						compensatoryLeaveComSet.getCompensatoryAcquisitionUse().getExpirationTime().value);
				// add refactor RequestList203
				result.setAdvancePayment(
						compensatoryLeaveComSet.getCompensatoryAcquisitionUse().getPreemptionPermit().value);
				isManageByTime = compensatoryLeaveComSet.getTimeVacationDigestUnit().getManage().value;
				digestiveUnit = compensatoryLeaveComSet.getTimeVacationDigestUnit().getDigestUnit().value;

			}
		}
		// 代休管理区分をチェックする(kiểm tra 代休管理区分)
		if (result.isSubstitutionFlg()) {
			// ドメインモデル「時間代休の消化単位」．管理区分をチェックする(kiểm tra domain 「時間代休の消化単位」．管理区分)
			if (isManageByTime == 1) {
				result.setTimeOfPeriodFlg(true);
				result.setDigestiveUnit(digestiveUnit);
			} else {
				result.setTimeOfPeriodFlg(false);
			}
		}
		return result;
	}

	public static interface Require {

		// ShareEmploymentAdapter
		public Optional<BsEmploymentHistoryImport> findEmploymentHistory(String companyId, String employeeId,
				GeneralDate baseDate);

		// CompensLeaveEmSetRepository.find
		public CompensatoryLeaveEmSetting findComLeavEmpSet(String companyId, String employmentCode);

		// CompensLeaveComSetRepository.find
		public CompensatoryLeaveComSetting findComLeavComSet(String companyId);

	}

}
