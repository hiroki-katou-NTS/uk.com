package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.monthly.remain.AnnualLeaveGrantRemaining;
import nts.uk.ctx.at.shared.dom.monthly.vacation.annualleave.AnnualLeaveGrant;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;

/**
 * 子の看護介護使用数
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseUsedNumber implements Cloneable{

	/** 子の看護介護休暇（使用日数） */
	private DayNumberOfUse usedDay;
	/** 子の看護介護休暇（使用時間） */
	private Optional<TimeOfUse>usedTimes;

	/**
	 * コンストラクタ　ChildCareNurseUsedNumber
	 */
	public ChildCareNurseUsedNumber(){
		this.usedDay = new DayNumberOfUse(0.0);
		this.usedTimes = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedDay　子の看護介護休暇（使用日数）
	 * @param usedTimes　子の看護介護休暇（使用時間）
	 * @return 子の看護介護使用数
	*/
	public static ChildCareNurseUsedNumber of(
			DayNumberOfUse usedDay,
			Optional<TimeOfUse> usedTimes){

		ChildCareNurseUsedNumber domain = new ChildCareNurseUsedNumber();
		domain.usedDay = usedDay;
		domain.usedTimes = usedTimes;
		return domain;
	}


	/**
	 * 集計結果を作成する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 期間
	 * @param criteriaDate 基準日
	 * @param Require｛
	 * @param 　介護看護休暇設定を取得する（会社ID、介護看護区分）
	 * @param 　子の看護・介護休暇基本情報を取得する（社員ID）
	 * @param 　年休の契約時間を取得する（社員ID、基準日）
	 * @param 　個人IDが一致する家族情報を取得（個人ID）
	 * @param 　介護対象管理データ（家族ID）
	 * @param 　期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分、Require）
	 * 				｝
	 * @return 子の看護介護休暇集計結果 AggrResultOfChildCareNurse
	 */
	public AggrResultOfChildCareNurse aggrResultOfChildCareNurse() {

		// 残数と使用数を計算する
		B

		// 集計期間の休暇情報にセットする値を判断する
		F

		// 子の看護介護休暇集計結果を返す

		// 起算日からの休暇情報にセットする値を判断する

		// 期間終了日の翌日時点使用数にセットする値を判断する

		// 起算日を含む期間かどうかを判断する

		// エラー情報をセットする

		//子の看護介護休暇集計結果を返す
}

	/**
	 * 残数と使用数を計算する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 期間
	 * @param criteriaDate 基準日
	 * @param Require｛
	 * @param 　介護看護休暇設定を取得する（会社ID、介護看護区分）
	 * @param 　子の看護・介護休暇基本情報を取得する（社員ID）
	 * @param 　年休の契約時間を取得する（社員ID、基準日）
	 * @param 　個人IDが一致する家族情報を取得（個人ID）
	 * @param 　介護対象管理データ（家族ID）
	 * @param 　期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分、Require）
	 * 				｝
	 * @return 子の看護介護集計期間WORK
	 */
	public AggrResultOfChildCareNurse B() {

		// 使用数計算
		C

		// エラーチェック
		G

		// 残数計算

		// 期間ごとの計算結果を作成
//		期間ごとの計算結果．集計期間の休暇情報．集計期間の使用数 = 子の看護介護計算使用数．集計期間の使用数
//				期間ごとの計算結果．集計期間の休暇情報．時間休暇使用回数 = 子の看護介護計算使用数．時間休暇使用回数
//				期間ごとの計算結果．集計期間の休暇情報．時間休暇使用日数 = 子の看護介護計算使用数．時間休暇使用日数
//				期間ごとの計算結果．エラー情報　＝子の看護介護エラー情報
//				期間ごとの計算結果．起算日からの休暇情報．起算日からの使用数 = 子の看護介護計算使用数．起算日からの使用数
//				期間ごとの計算結果．起算日からの休暇情報．上限日数　＝　子の看護計算残数．上限日数
//				期間ごとの計算結果．起算日からの休暇情報．残数　＝　子の看護計算残数．残数

		// 期間ごとの計算結果を子の看護介護集計期間WORKを入れる
		// === 子の看護介護集計期間WORK．集計結果　＝　期間ごとの計算結果
		result AggregateChildCareNurseWork =

		// 子の看護介護集計期間WORKを返す　return AggregateChildCareNurseWork;



	}


	/**
	 * 使用数計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 期間
	 * @param criteriaDate 基準日
	 * @param Require｛
	 * @param 　年休の契約時間を取得する（社員ID、基準日）
	 * }
	 * @return 子の看護介護計算使用数
	 */
	public AggrResultOfChildCareNurse C() {

		// 集計期間の翌日を集計する時は、処理は行わない

		// 暫定子の看護管理データの合計を求める

		// 子の看護介護計算使用数に合計を設定

		// 起算日からの使用数に加算
		D

		// 時間休暇使用回数を求める

		// 時間休暇使用日数を求める

		// return 子の看護介護計算使用数を返す

	}

	/**
	 * 起算日からの使用数に加算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param 子の看護介護計算使用数
	 * @param 月初時点の使用数
	 * @param Require｛
	 * @param 年休の契約時間を取得する（社員ID、基準日）
	 * @return 子の看護介護計算使用数
	 */
	public AggrResultOfChildCareNurse D() {

		// 処理期間が本年か翌年か

		// 本年
		// 月初時点の使用数を起算日からの使用数に加算

		// 翌年（子の看護介護計算使用数．起算日からの使用数 ．日数=0、時間＝0で作成）
		// 起算日からの使用数を作成

		// 期間の使用数に集計期間の使用数を加算

		// 時間使用数を日数に積み上げ
		E

		// return 子の看護介護計算使用数を返す

	}


	/**
	 * 時間使用数を日数に積み上げ
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 *  Require
	 * @param 年休の契約時間（社員ID、基準日）
	 * @return 子の看護介護使用数
	 */
	public AggrResultOfChildCareNurse E(require, companyId, employeeId, criteriaDate) {

		// INPUT．Require．年休の契約時間を取得する
		// === UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.残数管理.付与せず上限で管理する休暇.子の看護・介護休暇管理.アルゴリズム.起算日からの使用数に加算.起算日からの使用数に加算
		// === LaborContractTime の労働契約時間ではない※
		// ＝＝＝時間年休一日の時間.全社一律の時間（年休1日に相当する時間年休時間のこと）
		LaborContractTime contractTime = require.contractTime(companyId, employeeId, criteriaDate);

		// 使用時間を契約時間分だけ日数に変換する

		// return 子の看護介護使用数を返す

	}

	/**
	 * 集計期間の休暇情報にセットする値を判断する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID

	 * @return 集計期間の休暇情報 ChildCareNurseAggrPeriodDaysInfo
	 */
	public AggrResultOfChildCareNurse F() {

		// 本年の期間の「子の看護介護集計期間WORK」を取得する
//---本年か翌年か =本年
//	---			and
//	---			終了日．終了日の翌日の期間でない = false
// ---
//	---			必ず1件のみ取得できる

		// 翌年の期間の「子の看護介護集計期間WORK」を取得する



		// 取得した値を「子の看護介護集計結果」に設定
//		子の看護介護集計結果．集計期間の休暇情報．本年 = 本年期間の「期間ごとの集計結果」
//
//				翌年が取得できた場合
//				子の看護介護集計結果．集計期間の休暇情報．翌年= 翌年期間の「期間ごとの集計結果」

		return aggrResult;

	}


	/**
	 * エラーチェック
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 集計期間
	 * @param 月初時点の使用数 ===============????
	 * @param criteriaDate 基準日
	 * Require｛
		介護看護休暇設定を取得する（会社ID、介護看護区分）
		子の看護・介護休暇基本情報を取得する（社員ID）
		年休の契約時間を取得する（社員ID、基準日）
		個人IDが一致する家族情報を取得（個人ID）
		介護対象管理データ（家族ID）
		期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分、Require）
		｝
	 * @return 子の看護介護エラー情報 ChildCareNurseErrors
	 */
	public AggrResultOfChildCareNurse G() {

		// 集計期間の翌日を集計する時は、処理は行わない

		// 暫定子の看護介護管理データを取得する

		// 本年か翌年か確認
		// ＝＝＝＝＝＝本年　月初時点の使用数を超過確認用使用数に設定
		// ＝＝＝＝＝＝＝＝＝超過確認用使用数．日数　＝　INPUT．月初時点の使用数．使用日数
		// ＝＝＝＝＝＝＝＝＝超過確認用使用数．時間　＝　INPUT．月初時点の使用数．使用時間
		// ＝＝＝＝＝＝翌年　超過確認用使用数を作成
		// ＝＝＝＝＝＝＝＝＝超過確認用使用数．日数　＝　0
		// ＝＝＝＝＝＝＝＝＝超過確認用使用数．時間　＝　0

		// 上限超過チェック
		// ↑暫定子の看護管理データの件数ループ
		// 会社ID←パラメータ「会社ID」
//		社員ID←パラメータ「社員ID」
//		期間←パラメータ「期間」
//		基準日←パラメータ「基準日」
//		暫定子の看護介護管理データ←処理中の「暫定子の看護介護管理データ」
//		Require
		H

		// return 子の看護介護エラー情報を返す


	}
	/**
	 * 上限超過チェック
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 集計期間
	 * @param criteriaDate 基準日
	 * @param 処理中の「暫定子の看護介護管理データ」
	 * @param Require｛
	介護看護休暇設定を取得する（会社ID、介護看護区分）
	子の看護・介護休暇基本情報を取得する（社員ID）
	年休の契約時間を取得する（社員ID、基準日）
	個人IDが一致する家族情報を取得（個人ID）
	介護対象管理データ（家族ID）
	期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分、Require）
	｝
	 * @return ChildCareNurseErrors 子の看護介護エラー情報
	 */
	public AggrResultOfChildCareNurse H(require, companyId, employeeId, aggrPeriod, criteriaDate, 暫定子の看護介護管理データ) {

		// 暫定管理データを加算

		// 時間使用数を日数に繰り上げ

		// INPUT．Require．子の看護・介護休暇基本情報を取得する

		// 期間ごとの上限日数を求める


	}



	// Require
	public static interface Require {
		// 年休の契約時間を取得する（会社ID、社員ID、基準日）
		LaborContractTime contractTime(String companyId, String employeeId, GeneralDate criteriaDate);
	}

}