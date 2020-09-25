package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ReasonsForAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * 1ヶ月申請を更新する
 * 登録した36協定特別条項の適用申請の申請時間を変更する
 *
 * @author khai.dh
 */
@Stateless
public class OneMonthAppUpdate {

	/**
	 * [1] 変更する
	 * 登録した36協定特別条項の適用申請の申請時間を変更する
	 *
	 * @param require      @Require
	 * @param cid          会社ID
	 * @param applicantId  申請ID
	 * @param oneMonthTime ３６協定１ヶ月時間
	 * @param reason       36協定申請理由
	 * @return 申請作成結果
	 */
	public static AppCreationResult update(
			Require require,
			String cid,
			String applicantId,
			AgreementOneMonthTime oneMonthTime,
			ReasonsForAgreement reason) {

		// $36協定申請
		val optApp = require.getApp(applicantId); // R1
		if (!optApp.isPresent()){
			throw new BusinessException("Msg_1262");
		}

		val app = optApp.get();

		// $３６協定設定
		val setting = AgreementDomainService.getBasicSet(
				require,
				cid,
				app.getApplicantsSID(),
				GeneralDate.today(),
				WorkingSystem.REGULAR_WORK); // TODO Tài liệu mô tả thiếu tham số

		val oneMonth = setting.getBasicAgreementSetting().getOneMonth();

		// $エラー結果
		val errResult =  oneMonth.checkErrorTimeExceeded(oneMonthTime);

		if (errResult.getKey()) {
			return new AppCreationResult(
					app.getApplicantsSID(),
					ResultType.MONTHLY_LIMIT_EXCEEDED,
					Optional.empty(),
					Optional.of(errResult.getValue()),
					Optional.empty()
			);
		}

		// $1ヶ月のアラーム
		val oneMonthArlarm = oneMonth.calculateAlarmTime(oneMonthTime);

		// $エラーアラーム
		val errorArlarmTime = new ErrorTimeInMonth(oneMonthTime, oneMonthArlarm);

		// $36協定申請.1ヶ月の申請時間を変更する
		app.changeApplicationOneMonth(errorArlarmTime,reason);

		val at = AtomTask.of(() -> {
			require.updateApp(app);
		});

		return new AppCreationResult(
				app.getApplicantsSID(),
				ResultType.NO_ERROR,
				Optional.of(at),
				Optional.empty(),
				Optional.empty()
		);
	}

	public interface Require extends GettingApproverDomainService.Require, AgreementDomainService.RequireM3 {
		/**
		 * [R-1] 申請を取得する
		 * 36協定特別条項の適用申請Repository.get(申請ID)
		 */
		Optional<SpecialProvisionsOfAgreement> getApp(String applicantId);

		/**
		 * [R-2] 申請を更新する
		 * 36協定特別条項の適用申請Repository.Update(36協定特別条項の適用申請)
		 */
		void updateApp(SpecialProvisionsOfAgreement app);

	}
}
