package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import javax.ejb.Stateless;
import java.util.Optional;

/**
 * 申請を承認する
 * 36協定特別条項の適用申請を承認または否認をする
 *
 * @author khai.dh
 */
@Stateless
public class AppApproval {

	/**
	 * [1] 変更する
	 * 対象申請を承認また否認に変更する。
	 * 申請を承認する場合、３６協定年月設定または３６協定年度設定を作成する。
	 *
	 * @param require         @Require
	 * @param applicantId     申請ID
	 * @param approverId      承認者 (社員ID)
	 * @param approvalStatus  承認状態
	 * @param approvalComment 承認コメント
	 * @return AtomTask
	 */
	public static AtomTask change(
			Require require,
			String applicantId,
			String approverId,
			ApprovalStatus approvalStatus,
			Optional<AgreementApprovalComments> approvalComment) {

		// $申請
		val optApp = require.getApp(applicantId); // [R-1] 申請を取得する

		// if $申請.isEmpty
		if (!optApp.isPresent()) {
			throw new BusinessException("Msg_1262");
		}

		val app = optApp.get();

		// $申請 = $申請.申請を承認する(承認者,承認状態,承認コメント)
		app.approveApplication(approverId, approvalStatus, approvalComment);

		val empId = app.getApplicantsSID();
		val agrType = app.getApplicationTime().getTypeAgreement();

		Optional<AgreementMonthSetting> exMonthSetting = Optional.empty();
		AgreementMonthSetting monthSetting =null;
		Optional<AgreementYearSetting> exYearSetting = Optional.empty();
		AgreementYearSetting yearSetting = null;

		if (approvalStatus == ApprovalStatus.APPROVED){
			if (agrType == TypeAgreementApplication.ONE_MONTH) {
				val monthTime = app.getApplicationTime().getOneMonthTime().get();
				// $３６協定年月
				monthSetting = new AgreementMonthSetting(empId, monthTime.getYearMonth(), monthTime.getErrorTimeInMonth());

				// $既存の３６協定年月
				exMonthSetting = require.getYearMonthSetting(empId, monthTime.getYearMonth()); // R2

			}
			if (agrType == TypeAgreementApplication.ONE_YEAR){
				val yearTime = app.getApplicationTime().getOneYearTime().get();
				// $３６協定年
				yearSetting = new AgreementYearSetting(empId, yearTime.getYear().v(), yearTime.getErrorTimeInYear());

				// $既存の３６協定年
				exYearSetting = require.getYearSetting(empId, yearTime.getYear()); // R3
			}
		}

		Optional<AgreementMonthSetting> finalExMonthSetting = exMonthSetting;
		AgreementMonthSetting finalMonthSetting = monthSetting;
		Optional<AgreementYearSetting> finalExYearSetting = exYearSetting;
		AgreementYearSetting finalYearSetting = yearSetting;

		return AtomTask.of(() -> {
			require.updateApp(app); // R4
			if (approvalStatus == ApprovalStatus.APPROVED){
				if (agrType == TypeAgreementApplication.ONE_MONTH) {
					if (finalExMonthSetting.isPresent()) {
						require.updateYearMonthSetting(finalExMonthSetting.get()); // R6
					} else {
						require.addYearMonthSetting(finalMonthSetting); // R5
					}
				}
				if (agrType == TypeAgreementApplication.ONE_YEAR){
					if (finalExYearSetting.isPresent()) {
						require.updateYearSetting(finalExYearSetting.get());
					} else {
						require.addYearSetting(finalYearSetting); // R7
					}
				}
			}
		});
	}

	public interface Require {
		/**
		 * [R-1] 申請を取得する
		 * 36協定特別条項の適用申請Repository.get(申請ID)
		 */
		Optional<SpecialProvisionsOfAgreement> getApp(String applicantId);

		/**
		 * [R-2] 年月設定を取得する
		 * ３６協定年月設定Repository.get(社員ID,年月)
		 */
		Optional<AgreementMonthSetting> getYearMonthSetting(String empId, YearMonth yearMonth);

		/**
		 * [R-3] 年設定を取得する
		 * ３６協定年度設定Repository.get(社員ID,年度)
		 */
		Optional<AgreementYearSetting> getYearSetting(String empId, Year year);

		/**
		 * [R-4] 申請を更新する
		 * 36協定特別条項の適用申請Repository.Update(36協定特別条項の適用申請)
		 */
		void updateApp(SpecialProvisionsOfAgreement app);

		/**
		 * [R-5] 年月設定を追加する
		 * ３６協定年月設定Repository.Insert(３６協定年月設定)
		 */
		void addYearMonthSetting(AgreementMonthSetting setting);

		/**
		 * [R-6] 年月設定を更新する
		 * ３６協定年月設定Repository.Update(３６協定年月設定)
		 */
		void updateYearMonthSetting(AgreementMonthSetting setting);

		/**
		 * [R-7] 年設定を追加する
		 * ３６協定年度設定Repository.Insert(３６協定年度設定)
		 */
		void addYearSetting(AgreementYearSetting setting);

		/**
		 * [R-8] 年設定を更新する
		 * ３６協定年度設定Repository.Update(３６協定年度設定)
		 */
		void updateYearSetting(AgreementYearSetting setting);
	}
}
