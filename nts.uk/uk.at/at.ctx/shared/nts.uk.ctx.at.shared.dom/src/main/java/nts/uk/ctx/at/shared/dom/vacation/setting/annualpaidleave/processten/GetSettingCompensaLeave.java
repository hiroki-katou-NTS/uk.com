package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;

/**
 * @author ThanhNX
 *
 *         10-3.振休の設定を取得する refactor
 */
public class GetSettingCompensaLeave {

	// 10-3.振休の設定を取得する
	public static LeaveSetOutput process(Require require, String companyID, String employeeID, GeneralDate baseDate) {

		boolean subManageFlag = false;
		int expirationOfLeave = 0;
		ApplyPermission applyPermission = null;
		// アルゴリズム「社員所属雇用履歴を取得」を実行する
		Optional<BsEmploymentHistoryImport> empHistImport = require.findEmploymentHistory(companyID, employeeID,
				baseDate);
		if (empHistImport.isPresent()) {
			// ドメインモデル「雇用振休管理設定」を取得する(láy dữ liệu domain 「雇用振休管理設定」)
			Optional<EmpSubstVacation> optEmpSubData = require.findEmpById(companyID,
					empHistImport.get().getEmploymentCode());
			if (optEmpSubData.isPresent()) {// １件以上取得できた(1data trở lên)
				// ドメインモデル「雇用振休管理設定」．管理区分をチェックする(kiểm tra domain 「雇用振休管理設定」．管理区分)
				EmpSubstVacation empSubData = optEmpSubData.get();
			/*	if (empSubData.getSetting().getIsManage().equals(ManageDistinct.YES)) {
					subManageFlag = true;
					// 振休使用期限=ドメインモデル「振休管理設定」．「振休取得・使用方法」．休暇使用期限
					expirationOfLeave = empSubData.getSetting().getExpirationDate().value;
					// refactor RQ204
					applyPermission = empSubData.getSetting().getAllowPrepaidLeave();
				}*/
			} else {// ０件(0 data)
					// ドメインモデル「振休管理設定」を取得する(lấy dữ liệu domain 「振休管理設定」)
				Optional<ComSubstVacation> comSub = require.findComById(companyID);
				if (comSub.isPresent()) {
					ComSubstVacation comSubSet = comSub.get();
					/*if (comSubSet.isManaged()) {
						subManageFlag = true;
						// 振休使用期限=ドメインモデル「振休管理設定」．「振休取得・使用方法」．休暇使用期限
						expirationOfLeave = comSubSet.getSetting().getExpirationDate().value;
						// refactor RQ204
						applyPermission = comSubSet.getSetting().getAllowPrepaidLeave();
					}*/
				}
			}
		}
		return new LeaveSetOutput(subManageFlag, expirationOfLeave, applyPermission);
	}

	public static interface Require {

		// ShareEmploymentAdapter
		public Optional<BsEmploymentHistoryImport> findEmploymentHistory(String companyId, String employeeId,
				GeneralDate baseDate);

		// EmpSubstVacationRepository.findById
		public Optional<EmpSubstVacation> findEmpById(String companyId, String contractTypeCode);

		// ComSubstVacationRepository.findById
		public Optional<ComSubstVacation> findComById(String companyId);

	}

}
