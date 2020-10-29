package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ErrorClassification;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ExcessErrorContent;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ReasonsForAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 年間申請を更新する
 * 登録した36協定特別条項の適用申請の申請時間を変更する
 *
 * @author khai.dh
 */
@Stateless
public class AnnualAppUpdate {

	/**
	 * [1] 変更する
	 * 登録した36協定特別条項の適用申請の申請時間を変更する
	 * @param require 			@Require
	 * @param cid 				会社ID
	 * @param applicantId 		申請ID
	 * @param agrOneYearTime	36協定1年間時間
	 * @param reason 			36協定申請理由
	 * @return 					申請作成結果
	 */
	public static AppCreationResult update(
			Require require,
			String cid,
			String applicantId,
			AgreementOneYearTime agrOneYearTime,
			ReasonsForAgreement reason) {

		// $36協定申請
		Optional<SpecialProvisionsOfAgreement> optApp = require.getApp(applicantId); // R1

		if (!optApp.isPresent()) {
			throw new BusinessException("Msg_1262");
		}

		// $36協定申請.年間の申請時間を変更する
		val app = optApp.get();

		// $３６協定設定
		val setting = AgreementDomainService.getBasicSet(require, cid, applicantId, GeneralDate.today());
		val oneYear = setting.getOneYear();

		// $エラー結果
		val errorResult = oneYear.checkErrorTimeExceeded(agrOneYearTime);

		// $上限エラー
		List<ExcessErrorContent> limitError = new ArrayList();
		if (errorResult.getKey()) {
			// $上限エラー
			limitError.add(ExcessErrorContent.create(
					ErrorClassification.OVERTIME_LIMIT_ONE_YEAR,
					Optional.empty(),
					Optional.of(errorResult.getValue()),
					Optional.empty()));
		}

		AtomTask atomTask = null;
		if(limitError.isEmpty()) {
			// $年間のアラーム
			val annualAlarm = setting.getOneYear().calculateAlarmTime(agrOneYearTime);

			// $エラーアラーム
			val errAlarm = OneYearErrorAlarmTime.of(agrOneYearTime, annualAlarm);

			app.changeApplicationYear(errAlarm, reason);

			// AtomTask
			atomTask =  AtomTask.of(() -> { require.updateApp(app); });
		}

		return new AppCreationResult(
				app.getApplicantsSID(),
				Optional.ofNullable(atomTask),
				limitError
		);
	}

	public interface Require extends AgreementDomainService.RequireM4 {
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
