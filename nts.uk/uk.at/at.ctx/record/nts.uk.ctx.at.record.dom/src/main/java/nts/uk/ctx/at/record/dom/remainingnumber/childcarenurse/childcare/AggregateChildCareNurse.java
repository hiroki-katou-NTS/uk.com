package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.endsWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AggregatePeriodWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.MonthDay;


/**
 * 子の看護介護休暇 集計期間
  * @author yuri_tamakoshi
 */
@Getter
@Setter
public class AggregateChildCareNurse {
	/** 期間(List) */
	private List<AggregateChildCareNurseWork> period;

	/**
	 * コンストラクタ
	 */
	public AggregateChildCareNurse() {
		this.period =  new ArrayList<>();
	}
	/**
	 * ファクトリー
	 * @param period 集計期間
	 * @return 子の看護介護休暇 集計期間
	 */
	public static AggregateChildCareNurse of(
			 List<AggregateChildCareNurseWork> period){

		AggregateChildCareNurse domain = new AggregateChildCareNurse();
		domain.period = period;
		return domain;
	}

	/**
	 * エラー情報をセットする
	 * @return  子の看護介護エラー情報（List）
	 *
	 */
	public List<ChildCareNurseErrors> setErrosInfo(){

		// 全ての期間のエラー情報を取得する
		// 取得したエラー情報を「子の看護介護集計結果」に設定
		List<ChildCareNurseErrors> childCareNurseErrors = period.stream()
																										.map(c -> c.getAggrResultOfChildCareNurse().get().getErrorsInfo())
																										.collect(Collectors.toList());

		// 「子の看護介護エラー情報（List）」を返す
		return childCareNurseErrors;
	}


//	/**
//	 * 期間終了日の翌日時点使用数にセットする値を判断する
//	 * @return  子の看護介護使用数
//	 *
//	 */
//	public ChildCareNurseUsedNumber nextPeriodEndUsedNumber(){
//
//		// 終了日の期間の子の看護介護集計期間WORKを取得する
//		Optional<AggregateChildCareNurseWork> periodEndWork = period.stream().filter(c -> c.getNextDayAfterPeriodEnd().isPeriodEndAtr()).findFirst();
//
//		// 終了日の翌日の期間の子の看護介護集計期間WORKを取得する
//		Optional<AggregateChildCareNurseWork> nextPeriodEndWork = period.stream().filter(c -> c.getNextDayAfterPeriodEnd().isNextPeriodEndAtr()).findFirst();
//
//		// 終了期間の集計期間と終了日の翌日時点を比較
//		//(終了日の期間の「本年か翌年か」と終了日の翌日の期間の「本年か翌年か」を比較
//		if (periodEndWork.get().isThisYear() == nextPeriodEndWork.get().isThisYear()){
//				// 同じ場合
////				/終了日の期間の「子の看護介護集計期間WORK．本年か翌年か」が本年の場合
////				子の看護介護使用数＝終了日の期間の「起算日からの休暇情報．本年．起算日からの使用数」
//
//				if(periodEndWork.get().isThisYear()) {
//					//childCareNurseUsedNumber = getChildCareNurseStartdateDaysInfo.
//				}else {
////					終了日の期間の「子の看護介護集計期間WORK．本年か翌年か」が翌年の場合
////					子の看護看護使用数＝終了日の期間の「起算日からの休暇情報．翌年．起算日からの使用数」
//					childCareNurseUsedNumber = periodEndWork.of(ChildCareNurseStartdateDaysInfo.);
//				}
//				//nextPeriodEndUsedNumber = periodEndWork
//		}else {
//				// 違う場合
//				// 期間終了日の翌日時点の使用数=0
//				ChildCareNurseUsedNumber childCareNurseUsedNumber = ChildCareNurseUsedNumber.of(0, 0);
//		}
//		// 「子の看護介護使用数」を返す
//		return childCareNurseUsedNumber;
//	}
//
//	/**
//	 * 起算日からの休暇情報にセットする値を判断する
//	 * @return 起算日からの子の看護介護休暇情報
//	 *
//	 */
//	public ChildCareNurseStartdateDaysInfo getHolidayInfoStartMonthDay() {
//
//		// 本年の期間の「子の看護介護集計期間WORK」を取得する
//		Optional<AggregateChildCareNurseWork > thisYearPeriodWork = period.stream().filter(c -> c.isThisYear() && c.getNextDayAfterPeriodEnd().isNextPeriodEndAtr()).findFirst();
//
//
//		// 翌年の期間の「子の看護介護集計期間WORK」を取得する
//		Optional<AggregateChildCareNurseWork> nextYearPeriodWork = period.stream().filter(c -> c.isNextYear() && c.getNextDayAfterPeriodEnd().isNextPeriodEndAtr()).findFirst();
//
//		// 取得した値を「起算日からの休暇情報」に設定
//		//			起算日からの休暇情報．本年 = 本年期間の「起算日からの休暇情報」
//		//				翌年が取得できた場合
//		//				起算日からの休暇情報．翌年 = 翌年期間の「起算日からの休暇情報」
//
////		ChildCareNurseStartdateInfo thisYear = thisYearPeriodWork.map(c-> null).get();
////		Optional<ChildCareNurseStartdateInfo> nextYear = thisYearPeriodWork.map(c-> null);
//
//		// 型の不一致: AggregateChildCareNurseWork から ChildCareNurseStartdateInfo には変換できません
////		ChildCareNurseStartdateInfo thisYear = thisYearPeriodWork.map(c-> AggregateChildCareNurseWork.of(
////				new DatePeriod(),
////				new TempChildCareNurseManagement(),
////				NextDayAfterPeriodEndWork(),
////				yearAtr,
////				new ChildCareNurseCalcResultWithinPeriod()).get());
//		Optional<ChildCareNurseStartdateInfo> thisYear = thisYearPeriodWork.map(c-> AggregateChildCareNurseWork.of(
//				getDatePeriod(GeneralDate.today(), GeneralDate.today()),
//				getTempChildCareNurseManagement(),
//				getNextDayAfterPeriodEndWork,
//				YearAtr.THIS_YEAR,
//				new ChildCareNurseCalcResultWithinPeriod()).get());
//		//).get();
//		Optional<ChildCareNurseStartdateInfo> nextYear = nextYearPeriodWork.map(c-> null);
//
//		ChildCareNurseStartdateDaysInfo result = ChildCareNurseStartdateDaysInfo.of(thisYear, nextYear);
//
//		// 「起算日からの休暇情報」を返す
//		return result;
//	}
//
//	/**
//	 * 起算日を含む期間かどうかを判断する
//	 * @return boolean
//	 *
//	 */
//	public boolean judgePeriod() {
//
//		// 翌年の期間の「子の看護介護集計期間WORK」を取得する
//		Optional<AggregateChildCareNurseWork> PeriodWork = period.stream().filter(c -> c.isNextYear()).findFirst();
//
//		if(PeriodWork.isPresent()) {
//			return true;
//		}else {
//			return false;
//		}
//	}
//
//	/**
//	 * 子の看護集計期間を作成
//	 * @param aggrPeriod 期間
//	 * @param tempChildCareData 暫定管理データ
//	 * @param nursingLeaveSet 介護看護休暇設定を取得する（会社ID、介護看護区分）
//	 * @return 子の看護介護集計期間
//	 *
//	 */
//	private AggregateChildCareNurseWork createAggregatePeriod(DatePeriod aggrPeriod, TempChildCareNurseManagement tempChildCareData, Require require){
//
//		List<AggregateChildCareNurseWork> aggregateChildCareNurseWork = new ArrayList<>();
//
//		// 処理単位分割日リスト
//		Map<GeneralDate, DividedDayEachProcess> dividedDayMap = new HashMap<>();
//
//		// 処理単位分割日:DividedDayEachProcess
//		DividedDayEachProcess
//
//		// 開始日で期間を区切る
//	//	年月日←パラメータ「開始日」
//	//	本年か翌年か←本年
//	//	終了日の期間かどうか＝false
//	//	終了日の翌日の期間かどうか＝false
//		DividedDayEachProcess splitStart(period.start(),YearAtr.THIS_YEAR, NextDayAfterPeriodEndWork.of(false, false));
//
//		// 次回起算日で期間を区切る
//		DividedDayEachProcess splitNextStartMonthDay = splitNextStartMonthDay(period, require);
//
//		// 終了日で期間を区切る
//		List<DividedDayEachProcess> splitEnd = splitEnd(dividedDayMap, aggrPeriod);
//
//		// 終了日翌日の期間を区切る
//		// =====処理単位分割日←「処理単位分割日」
//		// =====終了日←パラメータ「期間．終了日」
//		List<DividedDayEachProcess> splitEndNextDay = splitEndNextDay(dividedDayEachProcess, period.end());
//
//		// 暫定データを分割
//		List<DividedDayEachProcess> splitInterimDate = splitInterimDate(aggrPeriod.end(), dividedDayMap, tempChildCareData);
//
//		// 子の看護介護休暇集計WORK作成
//		List<AggregateChildCareNurseWork> startWork = new AggregateChildCareNurseWork();
//		val startWorkEnd = dividedDayList.get(0).getYmd().addDays(-1);
//		startWork.setPeriod(new DatePeriod(aggrPeriod.start(), startWorkEnd));
//		aggregatePeriodWorks.add(startWork);
//
//		// 子の看護介護集計期間を作成
//		AggregateChildCareNurse aggregateChildCareNurse = createAggregatePeriod(period, tempChildCareData, require);
//
//		// 「子の看護介護集計期間」を返す
//		return aggregateChildCareNurseWork;
//	}
//
//	/**
//	 * 開始日で期間を区切る
//	 * @param	期間.開始日
//	 * @return  処理単位分割日
//	 */
//	private DividedDayEachProcess splitStart(DatePeriod period) {
//
//		List<TempChildCareNurseManagement> interimDate = Collections.emptyList();
//
//		// 開始日で期間を区切る
//		DividedDayEachProcess splitStart = DividedDayEachProcess.of(interimDate,
//				NextDayAfterPeriodEndWork.of(false, false),
//				period.start(), YearAtr.THIS_YEAR);
//
//		// 「処理単位分割日」を返す
//		return splitStart;
//	}
//
//
//
//	/**
//	 * 次回起算日で期間を区切る
//	 * @param 処理単位分割日
//	 * @param 期間
//	 * @param 介護看護休暇設定を取得する（会社ID、介護看護区分）
//	 * @return 処理単位分割日
//	 *
//	 */
//	private DividedDayEachProcess splitNextStartMonthDay(DividedDayEachProcess dividedDayEachProcess, DatePeriod period, Require require) {
//
//		// 期間を+1日する
//		GeneralDate addperiod = period.start().addDays(+1),period.end().addDays(+1);
//
//		// INPUT．Require介護看護休暇設定を取得
//		NursingLeaveSetting nursingLeaveSetting = require.nursingLeaveSetting(AppContexts.user().companyId(), NursingCategory.Nursing);
//
//		// 次回起算日を求める
//		GeneralDate nextStartMonthDay = nursingLeaveSetting.getNextStartMonthDay(period.start());
//
//		List<TempChildCareNurseManagement> interimDate = Collections.emptyList();
//
//		// 期間に次回起算日が含まれているか
//		// 開始日＜＝次回起算日＜＝終了日
//		if (period.start().beforeOrEquals(nextStartMonthDay) && nextStartMonthDay.beforeOrEquals(period.end())) {
//
//			DividedDayEachProcess splitNext = DividedDayEachProcess.of(interimDate,
//					NextDayAfterPeriodEndWork.of(false, false),
//					nextStartMonthDay, YearAtr.NEXT_YEAR);
//
//		}else {
//			DividedDayEachProcess splitNext = DividedDayEachProcess.of(interimDate,
//					NextDayAfterPeriodEndWork.of(false, false),
//					period.start(), YearAtr.THIS_YEAR);
//		}
//
//		// 起算日で期間を区切る
//		return splitNext;
//	}
//
//
//	/**
//	 * 終了日で期間を区切る
//	 * @param 処理単位分割日（List）
//	 * @param 終了日
//	 * @return 処理単位分割日（List）
//	 *
//	 */
//	private List<DividedDayEachProcess> splitEnd(List<DividedDayEachProcess> dividedDayEachProcess, DatePeriod period) {
//
//		// 終了日の処理単位分割日を取得
//		Optional<DividedDayEachProcess> splitEnd = DividedDayEachProcess.of(interimDate, endDate, period.end(), yearAtr)();
//
//		if(splitEnd.isPresent()) {
//			NextDayAfterPeriodEndWork.of(true, false);
//		}else {
//			// 一番年月日が大きい分割日を取得
//			if (getYmd.before(GeneralDate.max())) {
//				splitEnd = nextDayOfPeriodEnd.addDays(-1);
//			}
//
//			// 取得した分割日の終了日の翌日の期間かどうか=true
//			List<DividedDayEachProcess> splitEndList = splitEnd.of(DividedDayEachProcess.of(interimDate, NextDayAfterPeriodEndWork.of(false, true), ymd, yearAtr))
//		}
//		// 「処理単位分割日（List）」を返す
//		return splitEndList;
//	}
//
//	/**
//	 * 終了日翌日の期間を区切る
//	 * @param 終了日
//	 * @param 処理単位分割日（List）
//	 * @return  処理単位分割日（List）
//	 *
//	 */
//	private DividedDayEachProcess splitEndNextDay(List<DividedDayEachProcess> dividedDayEachProcess, DatePeriod period) {
//		// 終了日翌日の処理単位分割日を取得
//		DividedDayEachProcess dividedDayEachProcess =
//
//				// 一番年月日が大きい分割日を取得
//				// =====処理単位分割日.年月日 = 終了日の翌日
//				// =====本年か翌年か＝取得した分割日の本年か翌年か
//				// =====終了日の翌日情報WORK.終了日の期間かどうか = false
//				// =====終了日の翌日情報WORK.終了日の翌日の期間かどうか = true
//
//				// 終了日の翌日かどうか←trueに変更
//	}
//
//
//	/**
//	 * 暫定データを分割
//	 * @param period 終了日
//	 * @param dividedDayEachProcess 処理単位分割日（List）
//	 * @param interimDate 暫定子の看護介護管理データ
//	 * @return  処理単位分割日（List）
//	 *
//	 */
//	private List<DividedDayEachProcess> splitInterimDate(DatePeriod period,
//			List<DividedDayEachProcess> dividedDayEachProcess,
//			TempChildCareNurseManagement interimDate) {
//
//		// 対象期間の暫定データを処理単位分割日に設定
//		//====「処理単位分割日．年月日」＜＝ 対象日 ＜＝ 次の「処理単位分割日．年月日」の前日
//		//====※次の処理単位分割日がない場合、パラメータ「終了日」の翌日
//		if(ymd.b)
//		List<DividedDayEachProcess> splitInterimDateList = 0;
//
//		return splitInterimDateList;
//	}
//
//	/**
//	 * 子の看護介護休暇集計WORK作成
//	 * @param 処理単位分割日（List）
//	 * @param 終了日
//	 * @return  子の看護介護集計期間WORK（List)
//	 *
//	 */
//	private List<AggregateChildCareNurseWork> periodWork(List<DividedDayEachProcess> dividedDayEachProcess, DatePeriod period) {
//
//	}
//
//
//
//	/**
//	 * 集計期間の休暇情報にセットする値を判断する
//	 * @return  集計期間の休暇情報
//	 *
//	 */
//	private AggregateChildCareNurseWork getHolidayInfoAggrPeriod() {
//		// 本年の期間の「子の看護介護集計期間WORK」を取得する
////		if(YearAtr.THIS_YEAR && NextDayAfterPeriodEndWork.getNextPeriodEndAtr() == false) {
////			AggregateChildCareNurseWork thisyearPeriodWork = new AggregateChildCareNurseWork();
////		}
//
////		List<ChildCareNurseErrors> childCareNurseErrors = period.stream()
////																										.map(c -> c.getAggrResultOfChildCareNurse().get().getErrorsInfo())
////																										.collect(Collectors.toList());
//		AggregateChildCareNurseWork thisyearPeriodWork = period.stream().filter(c -> c.isThisYear() && c.getNextDayAfterPeriodEnd().isNextPeriodEndAtr());
//
//
//		// 翌年の期間の「子の看護介護集計期間WORK」を取得する
////		if(YearAtr.NEXT_YEAR && NextDayAfterPeriodEndWork.getNextPeriodEndAtr() == false) {
////			Optional<AggregateChildCareNurseWork> nextyearPeriodWork = new AggregateChildCareNurseWork();
////		}
//		Optional<AggregateChildCareNurseWork> nextyearPeriodWork = period.stream().filter(c -> c.isNextYear() && c.getNextDayAfterPeriodEnd().isNextPeriodEndAtr()).findFirst();
//
//		// 取得した値を「子の看護介護集計結果」に設定
//		return aggregateChildCareNurseWork;
//	}
//
//	/**
//	 * 集計結果を作成する
//	 * @param companyId 会社ID
//	 * @param employeeId 社員ID
//	 * @param aggrPeriod 集計期間
//	 * @param criteriaDate 基準日
//	 * @param Require｛
//	 * @param 		介護看護休暇設定を取得する（会社ID、介護看護区分）
//	 * @param 		子の看護・介護休暇基本情報を取得する（社員ID）
//	 * @param 		年休の契約時間を取得する（社員ID、基準日）
//	 * @param 		個人IDが一致する家族情報を取得（個人ID）
//	 * @param 		介護対象管理データ（家族ID）
//	 * @param 		期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分、Require）
//	 * @param 		｝
//	 * @return  子の看護介護休暇集計結果
//	 *
//	 */
//	public AggrResultOfChildCareNurse createAggrResult(String companyId,
//			String employeeId, DatePeriod aggrPeriod, GeneralDate criteriaDate, Require require) {
//
//
//
//			// 残数と使用数を計算する
//			ChildCareNurseCalcResultWithinPeriod calcResult =  calcRemainingUsed(companyId, employeeId, aggrPeriod, criteriaDate, Require);
//
//			// 集計期間の休暇情報にセットする値を判断する
//			// 子の看護介護休暇集計結果．集計期間の休暇情報 = 受け取った「集計期間の休暇情報」
//			AggrResultOfChildCareNurse aggrperiodinfo = ChildCareNurseCalcResultWithinPeriod.getAggrPeriodInfo();
//
//			// 起算日からの休暇情報にセットする値を判断する
//
//
//			// 期間終了日の翌日時点使用数にセットする値を判断する
//
//			// 起算日を含む期間かどうかを判断する
//
//			// エラー情報をセットする
//
//			// 子の看護介護休暇集計結果を返す
//
//		}
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
////		// 個人IDが一致する家族情報を取得（個人ID）
////		FamilyMember personInfo(String personId);
//
////		// 介護対象管理データ（家族ID）
////		FamilyMember careData(String FamilyID);
//
//		// 期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分、Require）
//		NursingCareLeaveRemainingInfo UpperLimitPeriod (String companyId, String employeeId, DatePeriod period, NursingCategory nursingCategory, Require require);
//
//	}
}
