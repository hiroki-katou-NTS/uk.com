package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ScreenDisplayInfo;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

import javax.ejb.Stateless;
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
	 * @param empId             申請者
	 * @param annualAppContent  年間の 申請内容
	 * @param screenDisplayInfo 画面表示情報
	 * @return 申請作成結果
	 */
	public static AppCreationResult create(
			Require require,
			String cid,
			String empId,
			AnnualAppContent annualAppContent,
			ScreenDisplayInfo screenDisplayInfo) {

		// $承認者項目
		val optApproverItem = GettingApproverDomainService.getApprover(require, annualAppContent.getApplicant());
		if (!optApproverItem.isPresent()) {
			return new AppCreationResult(annualAppContent.getApplicant(),
					ResultType.APPROVER_NOT_SET,
					Optional.empty(),
					Optional.empty(),
					Optional.empty()
			);
		}

		// $３６協定設定
		val setting = AgreementDomainService.getBasicSet(
				require,
				cid,
				annualAppContent.getApplicant(),
				GeneralDate.today(),
				WorkingSystem.REGULAR_WORK); // TODO Tài liệu mô tả thiếu tham số #32628

		val oneYear = setting.getOneYear();

		// $エラー結果
		val errResult = oneYear.checkErrorTimeExceeded(annualAppContent.getErrTime());

		if (errResult.getKey()) {
			return new AppCreationResult(
					annualAppContent.getApplicant(),
					ResultType.YEARLY_LIMIT_EXCEEDED,
					Optional.empty(),
					Optional.empty(),
					Optional.of(errResult.getValue())
			);
		}

		// 申請内容.アラーム時間
		annualAppContent.setAlarmTime(oneYear.getBasic().calcAlarmTime(annualAppContent.getErrTime()));

		// $申請
		val app = createAnnualApp(
				empId,
				annualAppContent,
				optApproverItem.get().getApproverList(),
				optApproverItem.get().getConfirmerList(),
				screenDisplayInfo);

		AtomTask at = AtomTask.of(() -> {
			require.addApp(app);
		});

		return new AppCreationResult(
				annualAppContent.getApplicant(),
				ResultType.NO_ERROR,
				Optional.of(at),
				Optional.empty(),
				Optional.empty()
		);
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
		val errorArlarmTime = new OneYearErrorAlarmTime(annualAppContent.getErrTime(), annualAppContent.getAlarmTime());

		// $１年間時間
		val oneYearTime = new OneYearTime(errorArlarmTime, annualAppContent.getYear());

		// $申請時間
		val appTime = new ApplicationTime(
				TypeAgreementApplication.ONE_YEAR,
				Optional.empty(),
				Optional.of(oneYearTime)
		);

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

	public interface Require extends GettingApproverDomainService.Require, AgreementDomainService.RequireM3 {
		/**
		 * [R-1] 申請を追加する
		 * 36協定特別条項の適用申請Repository.Insert(36協定特別条項の適用申請)
		 */
		void addApp(SpecialProvisionsOfAgreement app);
	}
}