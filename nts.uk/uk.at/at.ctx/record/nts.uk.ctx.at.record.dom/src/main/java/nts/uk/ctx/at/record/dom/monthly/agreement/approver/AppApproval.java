package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.AgreementApprovalComments;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ApprovalStatus;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.TypeAgreementApplication;
import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.record.dom.standardtime.AgreementYearSetting;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneYear;
import nts.uk.ctx.at.shared.dom.common.Year;

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
	public AtomTask change(Require require,
						   String applicantId,
						   String approverId,
						   ApprovalStatus approvalStatus,
						   Optional<AgreementApprovalComments> approvalComment) {

		val optApp = require.getApp(applicantId);
		if (!optApp.isPresent()) {
			throw new BusinessException("Msg_1262");
		}

		val app = optApp.get();
		app.approveApplication(approverId, approvalStatus, approvalComment);

		if (approvalStatus == ApprovalStatus.APPROVED) {

			val appTime = app.getApplicationTime();
			String applicantID = app.getApplicantsSID();
			if (appTime.getTypeAgreement() == TypeAgreementApplication.ONE_MONTH) {
				val oneMonthTime = appTime.getOneMonthTime();
				val yearMonth = oneMonthTime.isPresent() ? oneMonthTime.get().getYearMonth() : null;
				val errorTimeInMonth = oneMonthTime.isPresent() ? oneMonthTime.get().getErrorTimeInMonth() : null; // TODO để làm gì?

				ErrorOneMonth errorOneMonth = new ErrorOneMonth(0); // TODO truyền gì?
				AlarmOneMonth alarmOneMonth = new AlarmOneMonth(0); // TODO truyền gì?

 				val agr36MonthSetting = Optional.of(new AgreementMonthSetting(
						applicantID,
						yearMonth,
						errorOneMonth,
						alarmOneMonth));

				val existingAgr36MonthSetting = require.getYearMonthSetting(applicantID, yearMonth);
			}

			if (appTime.getTypeAgreement() == TypeAgreementApplication.ONE_YEAR) {
				val oneYearTime = appTime.getOneYearTime();
				val year = oneYearTime.get().getYear();
				val errorTimeInYear = oneYearTime.get().getErrorTimeInYear(); // TODO không tương thích kiểu
				val errorOneYear = new ErrorOneYear(0); // TODO DUMMY
				val alarmOneYear = new AlarmOneYear(0); // TODO DUMMY
				val agr36YearSetting = Optional.of(new AgreementYearSetting(applicantID, year.v(), errorOneYear, alarmOneYear));
				val existingAgr36YearSetting = require.getYearSetting(applicantID, year);
			}
		}

		return AtomTask.of(() -> {
			require.updateApp(app);
			if (agr36MonthSetting.isPresent()) {
				if (existingAgr36MonthSetting.isPresent()) {
					require.updateYearMonthSetting(existingAgr36MonthSetting.get());
				} else {
					require.addYearMonthSetting(agr36MonthSetting.get());
				}
			}

			if (agr36YearSetting.isPresent()) {
				if (existingAgr36YearSetting.isPresent()) {
					require.updateYearSetting(existingAgr36YearSetting.get());
				} else {
					require.addYearSetting(agr36YearSetting.get());
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
//
//[R-7] 年設定を追加する
//	３６協定年度設定Repository.Insert(３６協定年度設定)
//

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
