package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ReasonsForAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

import javax.ejb.Stateless;
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
	 * @param require @Require
	 * @param cid 会社ID
	 * @param applicantId 申請ID
	 * @param agrOneYearTime  36協定1年間時間
	 * @param reason 36協定申請理由
	 * @return 申請作成結果
	 */
	public static AppCreationResult update(Require require,
									String cid,
									String applicantId,
									AgreementOneYearTime agrOneYearTime,
									ReasonsForAgreement reason) {
		// $36協定申請
		Optional<SpecialProvisionsOfAgreement> optApp = require.getApp(applicantId); // R1

		if (!optApp.isPresent()) {
			throw new BusinessException("Msg_1262");
		}

		// $３６協定設定
		val setting = AgreementDomainService.getBasicSet(
				require,
				cid,
				applicantId,
				GeneralDate.today(),
				WorkingSystem.REGULAR_WORK); // TODO Tài liệu mô tả thiếu tham số #32628

		val oneYear = setting.getOneYear();

		// $エラー結果
		val errResult = oneYear.checkErrorTimeExceeded(agrOneYearTime);

		if (errResult.getKey()) {
			return new AppCreationResult(
					applicantId,
					ResultType.YEARLY_LIMIT_EXCEEDED,
					Optional.empty(),
					Optional.empty(),
					Optional.of(errResult.getValue())
					);
		}

		// $年間のアラーム
		val annualAlarm = oneYear.calculateAlarmTime(agrOneYearTime);

		// $エラーアラーム
		val errAlarm = new OneYearErrorAlarmTime(agrOneYearTime, annualAlarm);

		// $36協定申請
		val app = optApp.get();
		app.changeApplicationYear(errAlarm, reason);

		AtomTask at =  AtomTask.of(() -> {
			require.updateApp(app);
		});

		return new AppCreationResult(
				applicantId,
				ResultType.NO_ERROR,
				Optional.of(at),
				Optional.empty(),
				Optional.empty()
		);
	}

	public interface Require extends AgreementDomainService.RequireM3 {
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
