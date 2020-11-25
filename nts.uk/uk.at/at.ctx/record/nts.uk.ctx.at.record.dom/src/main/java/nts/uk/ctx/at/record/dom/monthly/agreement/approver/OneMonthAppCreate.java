package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 1ヶ月申請を登録する
 * 1ヶ月の36協定特別条項の適用申請を作成する
 *
 * @author khai.dh
 */
@Stateless
public class OneMonthAppCreate {
	/**
	 * [1] 作成する
	 * 1ヶ月の36協定特別条項の適用申請を作成する
	 *
	 * @param require 		Require
	 * @param cid			会社ID
	 * @param applicantId	申請者
	 * @param appContent	申請内容: 月間の申請内容
	 * @param displayInfo	画面表示情報
	 */
	public static AppCreationResult create(
			Require require,
			String cid,
			String applicantId,
			MonthlyAppContent appContent,
			ScreenDisplayInfo displayInfo) {

		// $エラー情報
		val errorInfo = new ArrayList<ExcessErrorContent>();

		// $承認者項目
		val optApprItem = GettingApproverDomainService.getApprover(require, appContent.getApplicant());
		if (!optApprItem.isPresent()) {
			// $承認者エラー
			val approverError = ExcessErrorContent.create(
					ErrorClassification.APPROVER_NOT_SET,
					Optional.empty(),
					Optional.empty(),
					Optional.empty());

			errorInfo.add(approverError);
		}

		// $超過エラー
		val excessError = CheckErrorApplicationMonthService.check(require, appContent);
		errorInfo.addAll(excessError);

		AtomTask atomTask = null;
		if (errorInfo.isEmpty()) {
			// $３６協定設定
			val setting = AgreementDomainService.getBasicSet(require, cid, appContent.getApplicant(), GeneralDate.today());

			// 申請内容.アラーム時間
			appContent.setAlarmTime(Optional.of(setting.getOneMonth().calculateAlarmTime(appContent.getAlarmTime().get())));

			// $申請
			SpecialProvisionsOfAgreement app = createOneMonthApp(
					applicantId,
					appContent,
					optApprItem.get().getApproverList(),
					optApprItem.get().getConfirmerList(),
					displayInfo);

			// $Atomtask
			atomTask = AtomTask.of(() -> {
				require.addApp(app);
			});
		}

		// return 申請作成結果
		return new AppCreationResult(appContent.getApplicant(), Optional.ofNullable(atomTask), errorInfo);
	}

	/**
	 * 1ヶ月の申請を作成する
	 *
	 * @param applicantId     申請者
	 * @param appContent    申請内容
	 * @param approverList  承認者リスト
	 * @param confirmerList 確認者リスト
	 * @param displayInfo   画面表示情報
	 * @return 36協定特別条項の適用申請
	 */
	private static SpecialProvisionsOfAgreement createOneMonthApp(
			String applicantId,
			MonthlyAppContent appContent,
			List<String> approverList,
			List<String> confirmerList,
			ScreenDisplayInfo displayInfo) {

		val errorAlarm = OneMonthErrorAlarmTime.of(appContent.getErrTime(), appContent.getAlarmTime().get());

		// $1ヶ月時間
		val oneMonthTime = new OneMonthTime(errorAlarm, appContent.getYm());

		// $申請時間
		val appTime = new ApplicationTime(
				TypeAgreementApplication.ONE_MONTH,
				Optional.of(oneMonthTime),
				Optional.empty());

		//	return 36協定特別条項の適用申請
		return SpecialProvisionsOfAgreement.create(
				applicantId,
				appContent.getApplicant(),
				appTime,
				appContent.getReason(),
				approverList,
				confirmerList,
				displayInfo
		);
	}

	/**
	 * [R-1] 申請を追加する
	 * 36協定特別条項の適用申請Repository.Insert(36協定特別条項の適用申請)
	 */
	public interface Require
			extends GettingApproverDomainService.Require,
			AgreementDomainService.RequireM4,
			CheckErrorApplicationMonthService.Require {

		void addApp(SpecialProvisionsOfAgreement app);
	}
}
