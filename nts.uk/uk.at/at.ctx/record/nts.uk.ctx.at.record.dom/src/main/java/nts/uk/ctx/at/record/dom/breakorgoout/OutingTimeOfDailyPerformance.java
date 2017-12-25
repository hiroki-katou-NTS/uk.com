package nts.uk.ctx.at.record.dom.breakorgoout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AcquisitionConditionsAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluRestTime;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluidPrefixBreakTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;

/**
 * 
 * @author nampt
 * 日別実績の外出時間帯 - root
 *
 */
@Getter
@AllArgsConstructor
public class OutingTimeOfDailyPerformance extends AggregateRoot {
	
	private String employeeId;
	
	private GeneralDate ymd;
	
	private List<OutingTimeSheet> outingTimeSheets;
	

	
	
	/**
	 * 勤務形態を見て外出時間帯の取得
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> getGoOutTimeSheet(AcquisitionConditionsAtr acqAtr ,WorkTimeMethodSet workTimeMethodSet
												 		  ,Optional<FluRestTime> fluRestTime,FluidPrefixBreakTimeSet fluidprefixBreakTimeSet){
		val goOutTimeSheetList = removeUnuseItemBaseOnAtr(acqAtr,workTimeMethodSet,fluRestTime,fluidprefixBreakTimeSet);
		switch(workTimeMethodSet) {
		case FLOW_WORK:
			if(fluRestTime.isPresent()) {
				if(fluRestTime.get().getUseFixedRestTime()) {
					return convertFromgoOutTimeToBreakTime(fluidprefixBreakTimeSet, goOutTimeSheetList);
				}
			}
			else {
				throw new RuntimeException("faild get FluidSet");
			}
		case FIXED_WORK:
		case DIFFTIME_WORK:
//		case Enum_Overtime_Work:
			return goOutTimeSheetList;
		default:
			throw new RuntimeException("unknown workTimeMethodSet:"+workTimeMethodSet);
		}
	}
	
	
	/**
	 * 取得条件区分を基に外出時間の不要項目の削除
	 * (区分 = 控除なら　外出理由 = 私用or組合のみのリストへ)
	 * @param 取得条件区分 
	 * @return 不要な項目を削除した時間帯
	 */
	public List<TimeSheetOfDeductionItem> removeUnuseItemBaseOnAtr(AcquisitionConditionsAtr acqAtr ,WorkTimeMethodSet workTimeMethodSet,Optional<FluRestTime> fluRestTime,FluidPrefixBreakTimeSet fluidprefixBreakTimeSet) {
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
//		if(/*流動勤務の場合*/) {
//			if((fluidprefixBreakTimeSet.getCalcMethod().isStampWithoutReference() && fluRestTime.get().getUseFixedRestTime())
//					||(fluRestTime.get()  && fluRestTime.get().getUseFixedRestTime())) {
//					for(OutingTimeSheet goOutTimeSheet:
//						outingTimeSheets.stream().filter(tg -> tg.getReasonForGoOut().isPrivateOrUnion()).collect(Collectors.toList())){
//						returnList.add(getToOutTimeSheet(acqAtr ,workTimeMethodSet, fluRestTime, fluidprefixBreakTimeSet));
//				}
//			}
//		}
			
		/*ここに外出から休憩に変化する↓のロジックを呼ぶ*/
		return returnList;
	}
	
	/**
	 * 外出時間帯から休憩時間帯への変換
	 * @return 控除項目の時間帯
	 */
	public List<TimeSheetOfDeductionItem> convertFromgoOutTimeToBreakTime(FluidPrefixBreakTimeSet fluidprefixBreakTimeSet,List<TimeSheetOfDeductionItem> deductionList){
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		
		for(TimeSheetOfDeductionItem deductionItem : deductionList) {
			//休憩へ変換する
//			if((fluidprefixBreakTimeSet.isPrivateGoOutTreatBreakTime() && deductionItem.getGoOutReason().get().isPrivate())
//				||(fluidprefixBreakTimeSet.isUnionGoOutTreatBreakTime() && deductionItem.getGoOutReason().get().isUnion())) {
//				returnList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(deductionItem.getTimeSheet(),deductionItem.getCalcrange(),deductionItem.getDeductionTimeSheet()
//						       ,deductionItem.getBonusPayTimeSheet(),deductionItem.getSpecBonusPayTimesheet(),deductionItem.getMidNightTimeSheet(),deductionItem.getGoOutReason()
//						       ,Optional.of(BreakClassification.BREAK_STAMP),DeductionClassification.BREAK));
//			}
//			//外出のまま
//			else {
//				returnList.add(deductionItem);
//			}
		}
		
		return returnList;
	}
}
