package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggregateChildCareNurseWork.RequireM1;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNurseErrors;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseUsedInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.shr.com.context.AppContexts;


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
	 * @param period 期間(List)
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
	 */
	public List<ChildCareNurseErrors> setErrosInfo(){

		// 全ての期間のエラー情報を取得する
		List<ChildCareNurseErrors> childCareNurseErrors = period.stream()
																										.map(c -> c.getAggrResultOfChildCareNurse().get().getErrorsInfo())
																										.flatMap(List::stream)
																										.collect(Collectors.toList());

		// 「子の看護介護エラー情報（List）」を返す
		return childCareNurseErrors;
	}

	/**
	 * 期間終了日の翌日時点使用数にセットする値を判断する
	 * @return  子の看護介護使用数
	 */
	public ChildCareNurseUsedNumber nextPeriodEndUsedNumber(){

		ChildCareNurseUsedNumber result;

		// 終了日の期間の子の看護介護集計期間WORKを取得する
		AggregateChildCareNurseWork periodEndWork = period.stream().filter(c -> c.getNextDayAfterPeriodEnd().isPeriodEndAtr()).findFirst().get();

		// 終了日の翌日の期間の子の看護介護集計期間WORKを取得する
		AggregateChildCareNurseWork nextPeriodEndWork = period.stream().filter(c -> c.getNextDayAfterPeriodEnd().isNextPeriodEndAtr()).findFirst().get();

		// 終了期間の集計期間と終了日の翌日時点を比較
		//(終了日の期間の「本年か翌年か」と終了日の翌日の期間の「本年か翌年か」を比較
		if (periodEndWork.getYearAtr() == nextPeriodEndWork.getYearAtr()){
			result = periodEndWork.getAggrResultOfChildCareNurse().get().getStartdateInfo().getUsedDays();
		}else {
			// 期間終了日の翌日時点の使用数=0　
			result = ChildCareNurseUsedNumber.of(new DayNumberOfUse(0d),  Optional.of(new TimeOfUse(0)));
		}
		// 「子の看護介護使用数」を返す
		return result;
	}

	/**
	 * 起算日からの休暇情報にセットする値を判断する
	 * @return 起算日からの子の看護介護休暇情報
	 *
	 */
	public ChildCareNurseStartdateDaysInfo getHolidayInfoStartMonthDay() {

		// 本年の期間の「子の看護介護集計期間WORK」を取得する
		Optional<AggregateChildCareNurseWork> thisYearPeriodWorkOpt
			= period.stream().filter(c -> c.isThisYear() && !c.getNextDayAfterPeriodEnd().isNextPeriodEndAtr())
				.findFirst();
		if ( !thisYearPeriodWorkOpt.isPresent() ) {
			return new ChildCareNurseStartdateDaysInfo();
		}

		// 翌年の期間の「子の看護介護集計期間WORK」を取得する
		Optional<AggregateChildCareNurseWork> nextYearPeriodWork = period.stream().filter(c -> c.isNextYear() && !c.getNextDayAfterPeriodEnd().isNextPeriodEndAtr()).findFirst();

		// 取得した値を「起算日からの休暇情報」に設定
		//	===起算日からの休暇情報．本年 = 本年期間の「起算日からの休暇情報」
		//	===			翌年が取得できた場合
		//	===			起算日からの休暇情報．翌年 = 翌年期間の「起算日からの休暇情報」
		ChildCareNurseStartdateInfo thisYear = thisYearPeriodWorkOpt.get().getAggrResultOfChildCareNurse().get().getStartdateInfo();
		Optional<ChildCareNurseStartdateInfo> nextYear = nextYearPeriodWork.map(c-> c.getAggrResultOfChildCareNurse().get().getStartdateInfo());

		ChildCareNurseStartdateDaysInfo result = ChildCareNurseStartdateDaysInfo.of(thisYear, nextYear);

		// 「起算日からの休暇情報」を返す
		return result;
	}

	/**
	 * 起算日を含む期間かどうかを判断する
	 * @return boolean
	 *
	 */
	public boolean judgePeriod() {

		// 翌年の期間の「子の看護介護集計期間WORK」を取得する
		Optional<AggregateChildCareNurseWork> periodWork = period.stream().filter(c -> c.isNextYear()).findFirst();

		return periodWork.isPresent();
	}

	/**
	 * 子の看護介護集計期間を作成
	 * @param period 期間
	 * @param interimDate 暫定管理データ
	 * @param nursingCategory 介護看護区分
	 * @return 子の看護介護集計期間
	 *
	 */
	public static AggregateChildCareNurse createAggregatePeriod(DatePeriod period, List<TempChildCareNurseManagement> interimDate, NursingCategory nursingCategory, Require require){

		// 処理単位分割日
		List<DividedDayEachProcess> dividedDayEachProcessList =  new ArrayList<>();

		// 開始日で期間を区切る
		DividedDayEachProcess splitStart = splitStart(period.start());
		dividedDayEachProcessList.add(splitStart);

		// 次回起算日で期間を区切る
		splitNextStartMonthDay(period, dividedDayEachProcessList, nursingCategory, require);

		// 一番年月日が大きい分割日にフラグを立てる
		 flagSplitEnd(dividedDayEachProcessList, period.end());

		// 終了日翌日の期間を区切る
		// =====処理単位分割日←「処理単位分割日」
		// =====終了日←パラメータ「期間．終了日」
		 List<DividedDayEachProcess> afterSplitNextEndDate = splitEndNextDay(dividedDayEachProcessList, period.end());

		// 暫定データを分割
		List<DividedDayEachProcess> afterSplitInterimData= splitInterimDate(period.end(), afterSplitNextEndDate, interimDate);

		// 子の看護介護休暇集計WORK作成
		List<AggregateChildCareNurseWork> periodWork = periodWork(afterSplitInterimData, period.end());

		// 「子の看護介護集計期間」を返す
		return AggregateChildCareNurse.of(periodWork);
	}

	/**
	 * 開始日で期間を区切る
	 * @param	開始日
	 * @return  処理単位分割日
	 */
	private static DividedDayEachProcess splitStart(GeneralDate start) {

		// 暫定データ＝空白
		List<TempChildCareNurseManagement> interimDate = new ArrayList<>();

		// 開始日で期間を区切る
		DividedDayEachProcess splitStart = DividedDayEachProcess.of(interimDate,
				NextDayAfterPeriodEndWork.of(false, false),
				start, YearAtr.THIS_YEAR);

		// 「処理単位分割日」を返す
		return splitStart;
	}

	/**
	 * 次回起算日で期間を区切る
	 * @param period 期間
	 * @param dividedDayEachProcess 処理単位分割日
	 * @return 処理単位分割日
	 *
	 */
	private static void splitNextStartMonthDay(DatePeriod period, List<DividedDayEachProcess>  dividedDayEachProcess, NursingCategory nursingCategory, RequireM1 require) {

		// 期間を+1日する
		DatePeriod addperiod = new DatePeriod(period.start().addDays(+1),period.end().addDays(+1));

		// INPUT．Require介護看護休暇設定を取得
		NursingLeaveSetting nursingLeaveSetting = require.nursingLeaveSetting(AppContexts.user().companyId(), nursingCategory);

		// 次回起算日を求める
		GeneralDate nextStartMonthDay = nursingLeaveSetting.getNextStartMonthDay(addperiod.start());

		// 期間に次回起算日が含まれているか
		// ===開始日＜＝次回起算日＜＝終了日
		if (period.contains(nextStartMonthDay)) {

			// 起算日で期間を区切る
			DividedDayEachProcess dividedDay = DividedDayEachProcess.of(new ArrayList<>(), //暫定データ＝空白
					NextDayAfterPeriodEndWork.of(false, false),
					nextStartMonthDay,
					YearAtr.NEXT_YEAR);

			dividedDayEachProcess.add(dividedDay);
		}
	}

	/**
	 * 一番年月日が大きい分割日にフラグを立てる
	 * @param dividedDayEachProcess 処理単位分割日（List）
	 * @param end 終了日
	 * @return 処理単位分割日（List）
	 */
	private static void flagSplitEnd(List<DividedDayEachProcess> dividedDayEachProcess, GeneralDate end) {

		// 一番年月日が大きい分割日を取得
		Optional<DividedDayEachProcess> largest = dividedDayEachProcess.stream()
				.sorted((c1, c2) -> c2.getYmd().compareTo(c1.getYmd())).findFirst();
		// 終了日期間として扱う
		largest.ifPresent(x -> x.treatAsPeriodEnd());
	}

	/**
	 * 終了日翌日の期間を区切る
	 * @param end 終了日
	 * @param dividedDayEachProcess 処理単位分割日（List）
	 * @return 処理単位分割日（List）
	 *
	 */
	private static List<DividedDayEachProcess> splitEndNextDay(List<DividedDayEachProcess> dividedDayEachProcess, GeneralDate end) {

        if(dividedDayEachProcess.isEmpty()) {
            return dividedDayEachProcess;
        }

        // 終了日翌日の処理単位分割日を取得
        Optional<DividedDayEachProcess> splitEndNext = dividedDayEachProcess.stream().filter(c -> c.getYmd().equals(end))
                                                                                       .findFirst();

        if(splitEndNext.isPresent()) {
        	splitEndNext.get().getEndDate().setPeriodEndAtr(true);

        	return dividedDayEachProcess;
		}else {
        	// 処理単位分割日に終了日翌日を追加
        	//===処理単位分割日.年月日 = 終了日の翌日
        	//===		本年か翌年か＝取得した分割日の本年か翌年か
        	//===		終了日の翌日情報WORK.終了日の期間かどうか = false
        	//===		終了日の翌日情報WORK.終了日の翌日の期間かどうか = true
        	DividedDayEachProcess splitEndNextAdd = DividedDayEachProcess.of(new ArrayList<>(),
        			NextDayAfterPeriodEndWork.of(false, true),
        			end.addDays(1),
        			dividedDayEachProcess.stream()
        			.sorted((c1, c2) -> c2.getYmd().compareTo(c1.getYmd()))
        			.findFirst().get().getYearAtr()
        			);

        	dividedDayEachProcess.add(splitEndNextAdd);

			// 「処理単位分割日（List）」を返す
			return dividedDayEachProcess;
		}
	}

	/**
	 * 暫定データを分割
	 * @param end 終了日
	 * @param dividedDayEachProcess 処理単位分割日（List）
	 * @param interimDate 暫定子の看護介護管理データ
	 * @return  処理単位分割日（List）
	 *
	 */
	private static List<DividedDayEachProcess> splitInterimDate(GeneralDate end,
			List<DividedDayEachProcess> dividedDayEachProcess,
			List<TempChildCareNurseManagement> interimDate) {

		List<DividedDayEachProcess> currentDayProcessList = new ArrayList<>();
		
		// 対象期間の暫定データを処理単位分割日に設定
		for (int idx = 0; idx < dividedDayEachProcess.size(); idx++) {

			val currentDayProcess = dividedDayEachProcess.get(idx);
			GeneralDate splitInterimDate;
			if (idx + 1 == dividedDayEachProcess.size()) {
				// 次の処理単位分割日がない場合、パラメータ「終了日」の翌日
				splitInterimDate = end.addDays(1);
			} else {
				splitInterimDate = dividedDayEachProcess.get(idx + 1).getYmd().addDays(-1);
			}
			

			// 「処理単位分割日．年月日」＜＝ 対象日 ＜＝ 次の「処理単位分割日．年月日」の前日
			val interimDate2 = interimDate.stream()
					.filter(c -> new DatePeriod(currentDayProcess.getYmd(), splitInterimDate).contains(c.getYmd())).collect(Collectors.toList());
			currentDayProcess.getInterimDate().addAll(interimDate2);
			
			currentDayProcessList.add(currentDayProcess);

		}
		// 「処理単位分割日（List）」を返す
		return currentDayProcessList;
	}

	/**
	 * 子の看護介護休暇集計WORK作成
	 * @param dividedDayEachProcess 処理単位分割日（List）
	 * @param end 終了日
	 * @return 子の看護介護集計期間WORK（List)
	 *
	 */
	private static List<AggregateChildCareNurseWork> periodWork(List<DividedDayEachProcess> dividedDayEachProcess, GeneralDate end) {

		List<AggregateChildCareNurseWork> result = new ArrayList<>();

		// 処理単位分割日でループ
		for (int idx = 0; idx < dividedDayEachProcess.size(); idx++) {
			val currentDayProcess = dividedDayEachProcess.get(idx);

			// 期間の終了日を求める
			GeneralDate periodEnd;
			if (idx + 1 == dividedDayEachProcess.size()) {
				periodEnd = end.addDays(1);	/**次の処理単位分割日がない場合、パラメータ「終了日」の翌日*/
			} else {
				periodEnd = dividedDayEachProcess.get(idx + 1).getYmd().addDays(-1);		/**期間．終了日←次の「処理単位分割日．年月日」の前日*/
			}

			// 子の看護介護休暇集計WORKを作成し、Listに追加
			AggregateChildCareNurseWork periodWork = AggregateChildCareNurseWork.of(
					new DatePeriod(currentDayProcess.getYmd(), periodEnd),		/** 期間．開始日←「処理単位分割日．年月日」 、求めた期間の終了日*/
					currentDayProcess.getInterimDate(),											/** 暫定子の看護介護管理データ←「処理単位分割日．暫定データ」*/
					currentDayProcess.getEndDate(),												/** 終了日←「処理単位分割日.終了日」*/
					currentDayProcess.getYearAtr(),												/** 本年か翌年か←「処理単位分割日.本年か翌年か」	 */
					Finally.empty());																			/** 集計結果＝NULL	 */

			result.add(periodWork);
		}

		// 「子の看護介護集計期間WORK（List)」を返す
		return result;
	}

	/**
	 * 集計期間の休暇情報にセットする値を判断する
	 * @return  集計期間の休暇情報
	 *
	 */
	public ChildCareNurseAggrPeriodDaysInfo getHolidayInfoAggrPeriod() {

		// 本年の期間の「子の看護介護集計期間WORK」を取得する
		Optional<AggregateChildCareNurseWork> thisYearPeriodWorkOpt = period.stream().filter(c -> c.isThisYear() && !c.getNextDayAfterPeriodEnd().isNextPeriodEndAtr())
																										.findFirst();
		if ( !thisYearPeriodWorkOpt.isPresent()) {
			return new ChildCareNurseAggrPeriodDaysInfo();
		}

		// 翌年の期間の「子の看護介護集計期間WORK」を取得する
		Optional<AggregateChildCareNurseWork> nextYearPeriodWork = period.stream().filter(c -> c.isNextYear() && !c.getNextDayAfterPeriodEnd().isNextPeriodEndAtr())
																															.findFirst();

		// 取得した値を「子の看護介護集計結果」に設定
		// ===子の看護介護集計結果．集計期間の休暇情報．本年 = 本年期間の「期間ごとの集計結果」
		// ===	子の看護介護集計結果．集計期間の休暇情報．翌年= 翌年期間の「期間ごとの集計結果」
		ChildCareNurseUsedInfo thisYear = thisYearPeriodWorkOpt.get().getAggrResultOfChildCareNurse().get().getAggrPeriodInfo();
		Optional <ChildCareNurseUsedInfo> nextYear =  nextYearPeriodWork.map(c -> c.getAggrResultOfChildCareNurse().get().getAggrPeriodInfo());

		// 「集計期間の休暇情報」を返す
		return ChildCareNurseAggrPeriodDaysInfo.of(thisYear, nextYear);
	}

	/**
	 * 集計結果を作成する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 集計期間
	 * @param criteriaDate 基準日
	 * @param startUsed 月初時点の使用数
	 * @param Require｛
	 * @param 		介護看護休暇設定を取得する（会社ID、介護看護区分）
	 * @param 		子の看護・介護休暇基本情報を取得する（社員ID）
	 * @param 		会社の年休設定を取得する（会社ID）
	 * @param 		社員の契約時間を取得する（社員ID、基準日）
	 * @param 		年休の契約時間を取得する（会社ID、社員ID、基準日、Require）
	 * @param 		社員IDが一致する家族情報を取得（社員ID）
	 * @param 		介護対象管理データ（家族ID）
	 * @param 		期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分、Require）
	 * @param 		｝
	 * @return  子の看護介護休暇集計結果
	 *
	 */
	public AggrResultOfChildCareNurse createAggrResult(String companyId,
			String employeeId, DatePeriod period, GeneralDate criteriaDate,
			ChildCareNurseUsedNumber startUsed, NursingCategory nursingCategory, Require require) {

			// 子の看護介護集計期間．期間の件数ループ
			for (int idx = 0; idx < this.period.size(); idx++) {

				val currentDayProcess = this.period.get(idx);

				// 残数と使用数を計算する
				currentDayProcess.calcRemainingUsed(companyId, employeeId, period, criteriaDate, startUsed, nursingCategory, require);
			}

			// 子の看護介護休暇集計結果をセットする
			val result =  AggrResultOfChildCareNurse.of(setErrosInfo(), 									/**  エラー情報をセットする */
																					nextPeriodEndUsedNumber(),			/** 期間終了日の翌日時点使用数にセットする値を判断する */
																					getHolidayInfoStartMonthDay(), 	/** 起算日からの休暇情報にセットする値を判断する */
																					judgePeriod(), 								/** 起算日を含む期間かどうかを判断する */
																					getHolidayInfoAggrPeriod());			/** 集計期間の休暇情報にセットする値を判断する */

			// 子の看護介護休暇集計結果を返す
			return result;
		}

	public static interface Require extends AggregateChildCareNurseWork.Require{

	}
}