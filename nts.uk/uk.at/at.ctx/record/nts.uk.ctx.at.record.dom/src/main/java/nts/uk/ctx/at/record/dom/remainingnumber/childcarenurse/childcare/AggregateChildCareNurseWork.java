package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response.Status.Family;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;

/**
 * 子の看護介護休暇 集計期間WORK
  * @author yuri_tamakoshi
 */
@Getter
@Setter
public class AggregateChildCareNurseWork {
	/** 期間 */
	private DatePeriod period;
	/** 暫定子の看護介護管理データ */
	private List<TempChildCareNurseManagement> provisionalDate;
	/** 期間終了後翌日 */
	private NextDayAfterPeriodEndWork nextDayAfterPeriodEnd;
	/** 本年翌年の期間区分 */
	private YearAtr yearAtr;
	/** 集計結果（finally） */
	private Finally<ChildCareNurseCalcResultWithinPeriod> aggrResultOfChildCareNurse;

	/**
	 * コンストラクタ
	 */
	public AggregateChildCareNurseWork(){

		this.period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.provisionalDate = new ArrayList<>();
		this.nextDayAfterPeriodEnd = new NextDayAfterPeriodEndWork();
		this.yearAtr = YearAtr.THIS_YEAR; //一時対応
		this.aggrResultOfChildCareNurse = Finally.empty();
	}

	/**
	 * ファクトリー
	 * @param period 期間
	 * @param provisionalDate 暫定子の看護介護管理データ
	 * @param nextDayAfterPeriodEnd 期間終了後翌日
	 * @param YearAtr 本年翌年の期間区分
	 * @param AggrResultOfChildCareNurse 集計結果（finally）
	 * @return 子の看護介護休暇 集計期間
	 */
	public static AggregateChildCareNurseWork of(
		DatePeriod period,
		 List<TempChildCareNurseManagement> provisionalDate,
		NextDayAfterPeriodEndWork nextDayAfterPeriodEnd,
		YearAtr yearAtr,
		Finally<ChildCareNurseCalcResultWithinPeriod> aggrResultOfChildCareNurse){

	AggregateChildCareNurseWork domain = new AggregateChildCareNurseWork();
	domain.period = period;
	domain.provisionalDate = provisionalDate;
	domain.nextDayAfterPeriodEnd = nextDayAfterPeriodEnd;
	domain.yearAtr = yearAtr;
	domain.aggrResultOfChildCareNurse = aggrResultOfChildCareNurse;
	return domain;
	}

	public boolean isThisYear() {
		return this.yearAtr == YearAtr.THIS_YEAR;
	}

	public boolean isNextYear() {
		return this.yearAtr == YearAtr.NEXT_YEAR;
	}

//	/**
//	 * エラーチェック
//	 * @param companyId 会社ID
//	 * @param employeeId 社員ID
//	 * @param aggrPeriod 集計期間
//	 * @param startUsed 月初時点の使用数 //一時対応DayNumberOfUseを確認
//	 * @param criteriaDate 基準日
//	 * Require｛
//		介護看護休暇設定を取得する（会社ID、介護看護区分）
//		子の看護・介護休暇基本情報を取得する（社員ID）
//		年休の契約時間を取得する（社員ID、基準日）
//		個人IDが一致する家族情報を取得（個人ID）
//		介護対象管理データ（家族ID）
//		期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分、Require）
//		｝
//	 * @return 子の看護介護エラー情報 ChildCareNurseErrors
//	 */
//	public AggrResultOfChildCareNurse errorInfo(String companyId, String employeeId, DatePeriod period, DayNumberOfUse startUsed,GeneralDate criteriaDate, Require require) {
//
//		残数と使用数を計算する
//
//		// 集計期間の翌日を集計する時は、処理は行わない
//		if(NextDayAfterPeriodEndWork.nextPeriodEndAtr.isPresent()) {
//		}else {
//			// 暫定子の看護介護管理データを取得する
//			AggregateChildCareNurseWork aggregateChildCareNurseWork = new AggregateChildCareNurseWork();
//		}
//
//		// 本年か翌年か確認
//		Optional<AggregateChildCareNurseWork> PeriodWork = period.stream().filter(c -> c.isNextYear()).findFirst();
//		if (PeriodWork.isPresent()) {
//			// ＝＝＝＝＝＝本年　月初時点の使用数を超過確認用使用数に設定
//			// ＝＝＝＝＝＝＝＝＝超過確認用使用数．日数　＝　INPUT．月初時点の使用数．使用日数
//			// ＝＝＝＝＝＝＝＝＝超過確認用使用数．時間　＝　INPUT．月初時点の使用数．使用時間
//			ChildCareCheckOverUsedNumberWork.days = startUsed.usedDays;
//			ChildCareCheckOverUsedNumberWork.times = startUsed.usedTimes;
//		}else {
//			// ＝＝＝＝＝＝翌年　超過確認用使用数を作成
//			// ＝＝＝＝＝＝＝＝＝超過確認用使用数．日数　＝　0
//			// ＝＝＝＝＝＝＝＝＝超過確認用使用数．時間　＝　0
//			ChildCareCheckOverUsedNumberWork.days = 0;
//			ChildCareCheckOverUsedNumberWork.times = 0;
//		}
//
//
//		// 上限超過チェック
//		// ↑暫定子の看護管理データの件数ループ
//		for (provisionalDate) {
//			ChildCareCheckOverUsedNumberWork checkOverUsedNumberWork(companyId, employeeId, period, criteriaDate, provisionalDate, require);
//		}
//
//		// 暫定管理データを加算
//
//		// 時間使用数を日数に繰り上げ
//
//		// INPUT．Require．子の看護・介護休暇基本情報を取得する
//
//		// 期間ごとの上限日数を求める
//
//		// 取得日の上限日数を確認
//
//		// 子の看護介護残数が残っているか
//
//		// 暫定管理データを加算
//
//		// return 子の看護介護エラー情報を返す
//
//
//	}
//
//	/**
//	 * 残数と使用数を計算する
//	 * @param companyId 会社ID
//	 * @param employeeId 社員ID
//	 * @param aggrPeriod 集計期間
//	 * @param criteriaDate 基準日
//	 * @return  子の看護介護集計期間WORK
//	 *
//	 */
//	public AggregateChildCareNurseWork calcRemainingUsed(String companyId, String employeeId, DatePeriod aggrPeriod, GeneralDate criteriaDate, Require require) {
//
//
//		// 使用数計算
//		AggregateChildCareNurseWork calcUsed = calcUsed(companyId, employeeId, aggrPeriod, criteriaDate, require);
//
//		// エラーチェック
//
//		// 残数計算
//		 calccalcRemaining(companyId, employeeId, aggrPeriod, criteriaDate, require);
//
//		// 期間ごとの計算結果を作成
////		1.期間ごとの計算結果．集計期間の休暇情報．集計期間の使用数 = 子の看護介護計算使用数．集計期間の使用数
////				2.期間ごとの計算結果．集計期間の休暇情報．時間休暇使用回数 = 子の看護介護計算使用数．時間休暇使用回数
////				3.期間ごとの計算結果．集計期間の休暇情報．時間休暇使用日数 = 子の看護介護計算使用数．時間休暇使用日数
////				4.期間ごとの計算結果．エラー情報　＝子の看護介護エラー情報
////				5.期間ごとの計算結果．起算日からの休暇情報．起算日からの使用数 = 子の看護介護計算使用数．起算日からの使用数
////				6.期間ごとの計算結果．起算日からの休暇情報．上限日数　＝　子の看護計算残数．上限日数
////				7.期間ごとの計算結果．起算日からの休暇情報．残数　＝　子の看護計算残数．残数
//		ChildCareNurseCalcResultWithinPeriod.ChildCareNurseAggrPeriodInfo.ChildCareNurseUsedNumber = ChildCareNurseUsedNumber.DayNumberOfUse;
//		ChildCareNurseCalcResultWithinPeriod.errorsInfo = ChildCareNurseErrors;
//		ChildCareNurseCalcResultWithinPeriod.
//		ChildCareNurseCalcResultWithinPeriod.
//		ChildCareNurseCalcResultWithinPeriod.
//
//
//		// 期間ごとの計算結果を子の看護介護集計期間WORKを入れる
//		Optional<AggregateChildCareNurseWork> calcResultWithinPeriod = Optional.empty();
//		// 子の看護介護集計期間WORK．集計結果　＝　期間ごとの計算結果
//		AggregateChildCareNurseWork.aggrResultOfChildCareNurse = calcResultWithinPeriod;
//
//	}
//
//	/**
//	 * 残数計算
//	 * @param companyId 会社ID
//	 * @param employeeId 社員ID
//	 * @param aggrPeriod 集計期間
//	 * @param criteriaDate 基準日
//	 * @return  子の看護介護計算残数
//	 *
//	 */
//	public AggregateChildCareNurseWork calcRemaining(String companyId, String employeeId, DatePeriod aggrPeriod, GeneralDate criteriaDate, Require require) {
//
//		// 集計期間の翌日を集計する時は、処理は行わない
//		if(NextDayAfterPeriodEndWork.nextPeriodEndAtr.isPresent()) {
//		}else {
//			// 暫定子の看護管理データの合計を求める
////			使用合計．使用日数←暫定子の看護介護管理データ．使用日数を全て加算
////			使用合計．使用時間←暫定子の看護介護管理データ．使用時間を全て加算
//			int totalUsedNumber = ChildCareNurseUsedNumber.usedDay + ChildCareNurseUsedNumber.usedTimes;// 子の看護介護使用数の合計
//		}
//	}
//
//	/**
//	 * 使用数計算
//	 * @param companyId 会社ID
//	 * @param employeeId 社員ID
//	 * @param aggrPeriod 集計期間
//	 * @param criteriaDate 基準日
//	 * @return  子の看護介護計算使用数
//	 *
//	 */
//	public AggregateChildCareNurseWork calcUsed(String companyId, String employeeId, DatePeriod aggrPeriod, GeneralDate criteriaDate, Require require) {
//
//		// 集計期間の翌日を集計する時は、処理は行わない
//		if(NextDayAfterPeriodEndWork.nextPeriodEndAtr.isPresent()) {
//		}else {
//			// 暫定子の看護管理データの合計を求める
//
//		}
////		使用合計．使用日数←暫定子の看護介護管理データ．使用日数を全て加算
////		使用合計．使用時間←暫定子の看護介護管理データ．使用時間を全て加算
//	}
//
//	/**
//	 * 子の看護介護残数を求める
//	 * @param
//	 * @return  子の看護介護残数、boolean
//	 *
//	 */
//
//
//	public static interface Require {
//
//		// 介護看護休暇設定を取得する（会社ID、介護看護区分）
//		NursingLeaveSetting nursingLeaveSetting(String companyId, NursingCategory nursingCategory);
//
//		// 子の看護・介護休暇基本情報を取得する（社員ID）
//		NursingCareLeaveRemainingInfo employeeInfo(String employeeId);
//
//		// 年休の契約時間を取得する（社員ID、基準日）
//		LaborContractTime contractTime(String employeeId, GeneralDate criteriaDate);
//
//		// 個人IDが一致する家族情報を取得（個人ID）
//		FamilyMember personInfo(String personId);
//
//		// 介護対象管理データ（家族ID）
//		FamilyMember careData(String FamilyID);
//
//		// 期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分、Require）
//		NursingCareLeaveRemainingInfo UpperLimitPeriod (String companyId, String employeeId, DatePeriod period, NursingCategory nursingCategory, Require require);
//
//	}
}