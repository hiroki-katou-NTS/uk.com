package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.AgreementApprovalComments;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ApprovalStatus;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.TypeAgreementApplication;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.AlarmOneMonth;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.AlarmOneYear;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.ErrorOneMonth;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.ErrorOneYear;

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
	public static AtomTask change(Require require,
						   String applicantId,
						   String approverId,
						   ApprovalStatus approvalStatus,
						   Optional<AgreementApprovalComments> approvalComment) {

		// $申請
		val optApp = require.getApp(applicantId); // [R-1] 申請を取得する
		if (!optApp.isPresent()) {
			throw new BusinessException("Msg_1262");
		}

		val app = optApp.get();
		app.approveApplication(approverId, approvalStatus, approvalComment);

		return AtomTask.of(() -> {
			require.updateApp(app); // R4

			if (approvalStatus == ApprovalStatus.APPROVED) {
				val appTime = app.getApplicationTime();
				String applicantID = app.getApplicantsSID();
				if (appTime.getTypeAgreement() == TypeAgreementApplication.ONE_MONTH) {
					val oneMonthTime = appTime.getOneMonthTime();
					val yearMonth = oneMonthTime.isPresent() ? oneMonthTime.get().getYearMonth() : null;
					val errorTimeInMonth = oneMonthTime.isPresent() ? oneMonthTime.get().getErrorTimeInMonth() : null;
					val existingAgr36MonthSetting = require.getYearMonthSetting(applicantID, yearMonth); // R2
					if (existingAgr36MonthSetting.isPresent()) {
						require.updateYearMonthSetting(existingAgr36MonthSetting.get()); // R6
					} else {
						ErrorOneMonth errorOneMonth = null;
						AlarmOneMonth alarmOneMonth = null;
						if (errorTimeInMonth != null){
							errorOneMonth = new ErrorOneMonth(errorTimeInMonth.getErrorTime().v());
							alarmOneMonth = new AlarmOneMonth(errorTimeInMonth.getAlarmTime().v());
						}

						val newAgr36MonthSetting = new AgreementMonthSetting(
								applicantID,
								yearMonth,
								errorOneMonth,
								alarmOneMonth
						);

						require.addYearMonthSetting(newAgr36MonthSetting); // R5
					}

				} else if (appTime.getTypeAgreement() == TypeAgreementApplication.ONE_YEAR) {
					val oneYearTime = appTime.getOneYearTime();
					val year = oneYearTime.get().getYear();
					val errorTimeInYear = oneYearTime.isPresent() ? oneYearTime.get().getErrorTimeInYear() : null;
					val existingAgr36YearSetting = require.getYearSetting(applicantID, year); // R3
					if (existingAgr36YearSetting.isPresent()) {
						require.updateYearSetting(existingAgr36YearSetting.get());
					} else {
						ErrorOneYear errorOneYear = null;
						AlarmOneYear alarmOneYear = null;
						if (errorTimeInYear != null) {
							errorOneYear = new ErrorOneYear(errorTimeInYear.getErrorTime().v());
							alarmOneYear = new AlarmOneYear(errorTimeInYear.getAlarmTime().v());
						}

						val agr36YearSetting = new AgreementYearSetting(
								applicantID,
								year.v(),
								errorOneYear,
								alarmOneYear);

						require.addYearSetting(agr36YearSetting);
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
