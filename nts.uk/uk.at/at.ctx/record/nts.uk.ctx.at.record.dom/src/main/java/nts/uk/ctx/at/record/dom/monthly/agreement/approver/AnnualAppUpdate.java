package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.OneYearTime;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;

import javax.ejb.Stateless;
import javax.inject.Inject;

// import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.OneMonthTime;

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
	 * @param oneYearTime  36協定1年間時間
	 * @param reason 36協定申請理由
	 * @return 申請作成結果
	 */
	public AppCreationResult create(Require require,
									String cid,
									String applicantId,
									OneYearTime oneYearTime,
									SpecialProvisionsOfAgreement reason) {
//		$36協定申請 = require.申請を取得する(申請ID)
//
//		if $36協定申請.isEmpty
//		BusinessException: Msg_1262
//
//				$３６協定設定 = 36協定基本設定を取得する#取得する(会社ID,対象者,年月日#今日())
//
//		$エラー結果 = $３６協定設定.1年間.特例条項による上限のエラー時間を超えているか(年間時間)
//
//		if $エラー結果.getKey()
//		return 申請作成結果#申請作成結果(対象者,結果区分.1年間の上限時間を超過している
//				,Optional.Empty,Optional.Empty,$エラー結果.getValue())
//
//		$年間のアラーム = $３６協定設定.1年間.アラーム時間を計算する(年間時間)
//
//		$エラーアラーム = 1年間のエラーアラーム時間#1年間のエラーアラーム時間(年間時間,$年間のアラーム)
//
//		$36協定申請.年間の申請時間を変更する($エラーアラーム,申請理由)
//
//		$Atomtask = require.申請を更新する($36協定申請)
//
//		return 36協定申請作成結果#36協定申請作成結果(対象者,結果区分.エラーなし,$Atomtask
//				,Optional.Empty,Optional.Empty)

		return null;
	}

	public static interface Require {
//		[R-1] 申請を取得する
//		36協定特別条項の適用申請Repository.get(申請ID)
//
//		[R-2] 申請を更新する
//		36協定特別条項の適用申請Repository.Update(36協定特別条項の適用申請)
	}
}
