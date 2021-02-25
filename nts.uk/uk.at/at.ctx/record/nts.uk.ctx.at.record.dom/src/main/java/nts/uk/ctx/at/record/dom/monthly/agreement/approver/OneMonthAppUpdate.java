package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.CheckErrorApplicationMonthService;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ExcessErrorContent;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ReasonsForAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;

import javax.ejb.Stateless;
import java.util.ArrayList;
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

		// エラー情報
		val errorInfo = new ArrayList<ExcessErrorContent>();

		// $36協定申請 (Optional)
		val optApp = require.getApp(applicantId); // R1
		if (!optApp.isPresent()) {
			throw new BusinessException("Msg_1262");
		}

		// $36協定申請
		val app = optApp.get();

		// $申請内容
		val monthlyAppContent = new MonthlyAppContent(
				app.getApplicantsSID(),
				app.getApplicationTime().getOneMonthTime().get().getYearMonth(),
				oneMonthTime,
				Optional.empty(),
				reason
		);

		// 超過エラー
		val excessError = CheckErrorApplicationMonthService.check(require, monthlyAppContent);
		errorInfo.addAll(excessError);

		AtomTask atomTask = null;
		if (errorInfo.isEmpty()) {
			// $３６協定設定
			val setting = AgreementDomainService.getBasicSet(require, cid, app.getApplicantsSID(), GeneralDate.today());

			// $1ヶ月のアラーム
			val oneMonthAlarm = setting.getOneMonth().calculateAlarmTime(oneMonthTime);

			// $エラーアラーム
			val errorAlarm = OneMonthErrorAlarmTime.of(oneMonthTime, oneMonthAlarm);

			// $36協定申請.1ヶ月の申請時間を変更する
			app.changeApplicationOneMonth(errorAlarm, reason);

			//$Atomtask
			atomTask = AtomTask.of(() -> { require.updateApp(app); }); // R2
		}

		// return 申請作成結果
		return new AppCreationResult(app.getApplicantsSID(), Optional.ofNullable(atomTask), errorInfo);
	}

	public interface Require extends
			GettingApproverDomainService.Require,
			AgreementDomainService.RequireM4,
			CheckErrorApplicationMonthService.Require {
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
