package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.ScreenDisplayInfo;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.SpecialProvisionsOfAgreement;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * 年間申請を登録する
 * 年間の36協定特別条項の適用申請を作成する
 *
 * @author khai.dh
 */
@Stateless
public class AnnualAppCreate {
	@Inject
	private ApproverGetDomainService apprService;

	/**
	 * [1] 作成する
	 * 年間の36協定特別条項の適用申請を作成する
	 *
	 * @param require           @Require
	 * @param cid               会社ID
	 * @param empId             社員ID
	 * @param annualAppContent  年間の申請内容
	 * @param screenDisplayInfo 画面表示情報
	 * @return 申請作成結果
	 */
	public AppCreationResult create(Require require,
									String cid,
									String empId,
									AnnualAppContent annualAppContent,
									ScreenDisplayInfo screenDisplayInfo) {

//		$承認者項目 = 承認者を取得する#取得する(require,申請内容.対象者)
//
//		if $承認者項目.isEmpty
//		return 36協定申請作成結果#36協定申請作成結果(申請内容.対象者,結果区分.承認者が未設定
//				,Optional.Empty,Optional.Empty,Optional.Empty)
//
//		$３６協定設定 = 36協定基本設定を取得する#取得する(会社ID,申請内容.対象者,年月日#今日())
//
//		$エラー結果 = $３６協定設定.1年間.特例条項による上限のエラー時間を超えているか(申請内容.エラー時間)
//
//		if $エラー結果.getKey()
//		return 申請作成結果#申請作成結果(申請内容.対象者,結果区分.1年間の上限時間を超過している
//				,Optional.Empty,Optional.Empty,$エラー結果.getValue())
//
//		申請内容.アラーム時間 = $３６協定設定.1年間.アラーム時間を計算する(申請内容.エラー時間)
//
//		$申請 = [prv-1] 1ヶ月の申請を作成する(申請者,申請内容,$承認者項目.承認者リスト
//				,$承認者項目.確認者リスト,画面表示情報)
//		$Atomtask = require.申請を追加する($申請)
//
//		return 36協定申請作成結果#36協定申請作成結果(申請内容.対象者,結果区分.エラーなし,$Atomtask
//				,Optional.Empty,Optional.Empty)


		return null;
	}

	/**
	 * [prv-1] 年間の申請を作成する
	 * 年間の36協定特別条項の適用申請を作成する
	 *
	 * @param empId             社員ID
	 * @param annualAppContent  年間の申請内容
	 * @param approverList      承認者リスト
	 * @param confirmerList     確認者リスト
	 * @param screenDisplayInfo 画面表示情報
	 * @return 36協定特別条項の適用申請
	 */
	private SpecialProvisionsOfAgreement createAnnualApp(String empId,
											  AnnualAppContent annualAppContent,
											  List<String> approverList,
											  List<String> confirmerList,
											  ScreenDisplayInfo screenDisplayInfo) {

//		$エラーアラーム = 1年間のエラーアラーム時間#1年間のエラーアラーム時間(申請内容.エラー時間
//				,申請内容.アラーム時間)
//
//		$１年間時間 = １年間時間#１年間時間(申請内容.年度,$エラーアラーム)
//
//		$申請時間 = 申請時間#申請時間(３６協定申請種類.1年間,Optional.empty,$１年間時間)
//
//		return 36協定特別条項の適用申請#新規申請作成(申請者,申請内容.対象者,$申請時間,申請内容.申請理由
//				,承認者リスト,確認者リスト,画面表示情報)

		return null;
	}


	public static interface Require {
		//[R-1] 申請を追加する
		//	36協定特別条項の適用申請Repository.Insert(36協定特別条項の適用申請)
	}
}