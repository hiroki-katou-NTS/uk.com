package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;

import javax.ejb.Stateless;

// import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.OneMonthTime;

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
	 * @param require @Require
	 * @param cid 会社ID
	 * @param applicantId 申請ID
	 * @param oneMonthTime  ３６協定１ヶ月時間
	 * @param reason 36協定申請理由
	 * @return 申請作成結果
	 */
	public AppCreationResult update(Require require,
									String cid,
									String applicantId,
									AgreementOneMonthTime oneMonthTime,
									SpecialProvisionsOfAgreement reason) {
//		$36協定申請 = require.申請を取得する(申請ID)

		//				[R-1] 申請を取得する
		//
		//		36協定特別条項の適用申請Repository.get(申請ID)

//
//		if $36協定申請.isEmpty
//		BusinessException: Msg_1262
//
//				$３６協定設定 = 36協定基本設定を取得する#取得する(会社ID,$36協定申請.申請対象者,年月日#今日())
//
//		$エラー結果 = $３６協定設定.1ヶ月.特例条項による上限のエラー時間を超えているか(１ヶ月時間)
//
//		if $エラー結果.getKey()
//		return 申請作成結果#申請作成結果($36協定申請.申請対象者,結果区分.1ヶ月の上限時間を超過している
//				,Optional.Empty,$エラー結果.getValue(),Optional.Empty)
//
//		$1ヶ月のアラーム = $３６協定設定.1ヶ月.アラーム時間を計算する(１ヶ月時間)
//
//		$エラーアラーム = 1ヶ月のエラーアラーム時間#1ヶ月のエラーアラーム時間(１ヶ月時間,$1ヶ月のアラーム)
//
//		$36協定申請.1ヶ月の申請時間を変更する($エラーアラーム,申請理由)
//
//		$Atomtask = require.申請を更新する($36協定申請)
//
//		return 36協定申請作成結果#36協定申請作成結果($36協定申請.申請対象者,結果区分.エラーなし,$Atomtask
//				,Optional.Empty,Optional.Empty)


		return null;
	}

	public static interface Require extends GettingApproverDomainService.Require, AgreementDomainService.RequireM3 {
//		[R-1] 申請を取得する
//	36協定特別条項の適用申請Repository.get(申請ID)
//
//
//				[R-2] 申請を更新する
//	36協定特別条項の適用申請Repository.Update(36協定特別条項の適用申請)

	}
}
