package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 年間申請を登録する
 * 年間の36協定特別条項の適用申請を作成する
 *
 * @author khai.dh
 */
@Stateless
public class AnnualAppCreate {

	/**
	 * [1] 作成する
	 * 年間の36協定特別条項の適用申請を作成する
	 *
	 * @param require           @Require
	 * @param cid               会社ID
	 * @param applicantId       申請者
	 * @param appContent  		年間の 申請内容
	 * @param screenDisplayInfo 画面表示情報
	 * @return 申請作成結果
	 */
	public static AppCreationResult create(
			Require require,
			String cid,
			String applicantId,
			AnnualAppContent appContent,
			ScreenDisplayInfo screenDisplayInfo) {

		// $エラー情報
		val errorInfo = new ArrayList<ExcessErrorContent>();

		// $承認者項目
		val optApproverItem = GettingApproverDomainService.getApprover(require, appContent.getApplicant());

		if (!optApproverItem.isPresent()) {
			// $承認者エラー
			val approverError = ExcessErrorContent.create(
					ErrorClassification.APPROVER_NOT_SET,
					Optional.empty(),
					Optional.empty(),
					Optional.empty());

			errorInfo.add(approverError);
		}

		// $３６協定設定
		val setting = AgreementDomainService.getBasicSet(require, cid, appContent.getApplicant(), GeneralDate.today());

		// $エラー結果
		val errorResult = setting.getOneYear().checkErrorTimeExceeded(appContent.getErrTime());

		if (errorResult.getKey()) {
			// $上限エラー
			val limitError = ExcessErrorContent.create(
					ErrorClassification.OVERTIME_LIMIT_ONE_YEAR,
					Optional.empty(),
					Optional.of(errorResult.getValue()),
					Optional.empty());

			errorInfo.add(limitError);
		}

		AtomTask atomTask = null;
		if (errorInfo.isEmpty()) {
			// set 申請内容.アラーム時間
			appContent.setAlarmTime(setting.getOneYear().calculateAlarmTime(appContent.getErrTime()));

			// $申請
			val app = createAnnualApp(
					applicantId,
					appContent,
					optApproverItem.get().getApproverList(),
					optApproverItem.get().getConfirmerList(),
					screenDisplayInfo);

			// $Atomtask
			atomTask = AtomTask.of(() -> {
				require.addApp(app);
			});
		}

		return new AppCreationResult(appContent.getApplicant(), Optional.ofNullable(atomTask), errorInfo);
	}

	/**
	 * [prv-1] 年間の申請を作成する
	 * 年間の36協定特別条項の適用申請を作成する
	 *
	 * @param applicantId       申請者
	 * @param annualAppContent  年間の 申請内容
	 * @param approverList      承認者リスト
	 * @param confirmerList     確認者リスト
	 * @param screenDisplayInfo 画面表示情報
	 * @return 36協定特別条項の適用申請
	 */
	private static SpecialProvisionsOfAgreement createAnnualApp(
			String applicantId,
			AnnualAppContent annualAppContent,
			List<String> approverList,
			List<String> confirmerList,
			ScreenDisplayInfo screenDisplayInfo) {

		// $エラーアラーム
		val errorAlarmTime = OneYearErrorAlarmTime.of(annualAppContent.getErrTime(), annualAppContent.getAlarmTime());

		// $１年間時間
		val oneYearTime = new OneYearTime(errorAlarmTime, annualAppContent.getYear());

		// $申請時間
		val appTime = new ApplicationTime(
				TypeAgreementApplication.ONE_YEAR,
				Optional.empty(),
				Optional.of(oneYearTime));

		// return 36協定申請作成結果
		return SpecialProvisionsOfAgreement.create(
				applicantId,
				annualAppContent.getApplicant(),
				appTime,
				annualAppContent.getReason(),
				approverList,
				confirmerList,
				screenDisplayInfo
		);
	}

	public interface Require extends GettingApproverDomainService.Require, AgreementDomainService.RequireM4 {
		/**
		 * [R-1] 申請を追加する
		 * 36協定特別条項の適用申請Repository.Insert(36協定特別条項の適用申請)
		 */
		void addApp(SpecialProvisionsOfAgreement app);
	}
}