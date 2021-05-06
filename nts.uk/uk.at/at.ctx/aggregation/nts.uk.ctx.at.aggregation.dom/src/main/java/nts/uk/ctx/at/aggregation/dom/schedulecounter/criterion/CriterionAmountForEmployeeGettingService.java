package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 社員の目安金額を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.目安金額.社員の目安金額を取得する
 * @author kumiko_otake
 */
@Stateless
public class CriterionAmountForEmployeeGettingService {

	/**
	 * 取得する
	 * @param require require
	 * @param empId 社員ID
	 * @param date 基準日
	 * @return 目安金額
	 */
	public static CriterionAmount get(Require require, EmployeeId empId, GeneralDate date) {

		// 目安利用区分を取得する
		// ※『目安利用区分』は初期データのため常に存在する
		val usageStg = require.getUsageSetting().get();
		if( usageStg.getEmploymentUse() == NotUseAtr.USE ) {
			// 基準日時点の雇用を取得
			val employmentInfo = require.getEmploymentHistory( empId, date );
			if( !employmentInfo.isPresent() ) {
				throw new BusinessException( "Msg_426" );
			}

			// 雇用に対応する目安金額を取得
			val guideline = require.getCriterionAmountForEmployment( new EmploymentCode(employmentInfo.get().getEmploymentCd()) );
			if( guideline.isPresent() ) {
				return guideline.get().getCriterionAmount();
			}
		}

		// 会社の目安金額を取得する
		// ※『会社の目安金額』は初期データのため常に存在する
		val guideline = require.getCriterionAmountForCompany().get();
		return guideline.getCriterionAmount();

	}



	public static interface Require {

		/**
		 * 目安利用区分を取得する
		 * @return 目安利用区分
		 */
		public Optional<CriterionAmountUsageSetting> getUsageSetting();

		/**
		 * 会社の目安金額を取得する
		 * @return 会社の目安金額
		 */
		public Optional<CriterionAmountForCompany> getCriterionAmountForCompany();

		/**
		 * 雇用の目安金額を取得する
		 * @param employmentCode 雇用コード
		 * @return 雇用の目安金額
		 */
		public Optional<CriterionAmountForEmployment> getCriterionAmountForEmployment(EmploymentCode employmentCode);

		/**
		 * 社員の雇用を取得する
		 * @param empId 社員ID
		 * @param date 年月日
		 * @return
		 */
		Optional<EmploymentPeriodImported> getEmploymentHistory(EmployeeId empId, GeneralDate date);

	}

}
