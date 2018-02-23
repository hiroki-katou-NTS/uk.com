package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedRestCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;


/**
 * 休憩管理
 * @author keisuke_hoshina
 *
 */
@RequiredArgsConstructor
public class BreakTimeManagement {
	private final BreakTimeOfDaily breakTimeOfDaily;
	private final List<BreakTimeOfDailyPerformance> breakTimeSheetOfDaily;
	
	/**
	 * 休憩時間帯を作成する
	 * @return 休憩時間帯
	 */
	
	public static  List<TimeSheetOfDeductionItem> getBreakTimeSheet(WorkTimeDivision workTimeDivision,FixedRestCalculateMethod calcRest,FlowFixedRestSet noStampSet
															,FlowRestCalcMethod calcMethod,List<BreakTimeOfDailyPerformance> breakTimeOfDailyList) {
		List<TimeSheetOfDeductionItem> timeSheets = new ArrayList<>();
		/*流動orフレックスかどうか判定*/
		if(!workTimeDivision.isfluidorFlex()) {
			/*固定休憩時間帯作成*/
		//	timeSheets.add(getFixedBreakTimeSheet(calcRest,breakTimeOfDailyList)); 
		}
		else {
			/*流動休憩時間帯作成*/
		//	timeSheets.addAll(getFluidBreakTimeSheet(calcMethod,true,noStampSet,breakTimeOfDailyList));;
		}
		
		
		List<TimeSheetOfDeductionItem> dedTimeSheet = new ArrayList<TimeSheetOfDeductionItem>();
		//
//		for(Optional<BreakTimeOfDailyPerformance> OptionalTimeSheet : timeSheets) {
//			if(OptionalTimeSheet.isPresent()) {
//				for(BreakTimeSheet timeSheet : OptionalTimeSheet.get().getBreakTimeSheets())
//					dedTimeSheet.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(new TimeZoneRounding(timeSheet.getStartTime().getTimeWithDay(),timeSheet.getEndTime().getTimeWithDay(),null)
//																			, new TimeSpanForCalc(timeSheet.getStartTime().getTimeWithDay(),timeSheet.getEndTime().getTimeWithDay())
//																			, Collections.emptyList()
//																			, Collections.emptyList()
//																			, Collections.emptyList()
//																			, Optional.empty()
//																			, Finally.empty()
//																			, Finally.of(BreakClassification.BREAK)
//																			, DeductionClassification.BREAK));
//			}
//		}
		return dedTimeSheet;
	}
	
	/**
	 * 固定勤務 時に休 時間帯を取得する
	 * @param restCalc 固定給系の計算方法
	 * @return 休  時間帯

 */
	public static List<TimeSheetOfDeductionItem> getFixedBreakTimeSheet(FixedRestCalculateMethod calcRest,List<BreakTimeOfDailyPerformance> breakTimeOfDailyList) {
		//就業時間帯を参照
		if(calcRest.isReferToMaster()) {
			return breakTimeOfDailyList.stream()
										.filter(tc -> tc.getBreakType().isReferWorkTime())
										.findFirst()
										.get()
										.changeAllTimeSheetToDeductionItem();
		}
		//スケを参照
		else {
			return breakTimeOfDailyList.stream()
										.filter(tc -> tc.getBreakType().isReferSchedule())
										.findFirst()
										.get()
										.changeAllTimeSheetToDeductionItem();
		}
	}
	

//	/**
//	 * 流動勤務 休 設定取得
//	 * @param calcMethod 流動休 の計算方
//	 * @param isFixedBreakTime 流動固定休 を使用する区分
//	 * @param noStampSet 休 未打刻時 休設定
//	 * @return 休 時間帯
//	 */
//	public static List<TimeSheetOfDeductionItem> getFluidBreakTimeSheet(FlowRestCalcMethod calcMethod,boolean isFixedBreakTime,FlowFixedRestSet noStampSet,List<BreakTimeOfDailyPerformance> breakTimeOfDailyList,
//																		List<OutingTimeOfDailyPerformance> goOutTimeSheetList) {
////		if(isFixedBreakTime) {
////			switch(noStampSet.getCalculateMethod()) {
////				//予定を参照する
////				case REFER_SCHEDULE:
////					//if(予定から参照するかどうか)
////					//参照する
////					return getReferenceTimeSheet(BreakType.REFER_SCHEDULE);
////					//しない
////					//return getReferenceTimeSheetFromWorkTime();
////				//マスタを参照
////				case REFER_MASTER:
////					return getReferenceTimeSheet(BreakType.REFER_WORK_TIME);
////				//参照せずに打刻をする
////				case STAMP_WHITOUT_REFER:
////					return goOutTimeSheetList.stream()
////											 .filter()
////				}
////			default:
////				throw new RuntimeException("unKnown calcMethod" + calcMethod);
////			}
////		}
//	}
	
	/**
	 * 流動固定休憩の計算方法が指定された休憩種類の日別計算用休憩時間帯クラスを取得する　
	 * @return 日別実績の休　時間帯クラス
	 */
	public List<TimeSheetOfDeductionItem> getReferenceTimeSheet(BreakType breakType){
		return breakTimeSheetOfDaily.stream()
									.filter(tc -> tc.getBreakType().equals(breakType))
									.findFirst()
									.get()
									.changeAllTimeSheetToDeductionItem();
	}
	
	/**
	 * 指定した時間帯に含まれる休憩時間の合計値を返す
	 * @param baseTimeSheet
	 * @return
	 */
	public int sumBreakTimeIn(TimeSpanForCalc baseTimeSheet) {
		return breakTimeSheetOfDaily.stream()
				.collect(Collectors.summingInt(b -> b.sumBreakTimeIn(baseTimeSheet)));
	}
}
