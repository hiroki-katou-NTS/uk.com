package nts.uk.ctx.at.record.dom.daily.breaktimegoout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AcquisitionConditionsAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.BreakClassification;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionClassification;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.WithinStatutoryAtr;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluRestTime;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluidPrefixBreakTimeSet;

/**
 * 日別実績の外出時間帯
 * @author keisuke_hoshina
 *
 */
@Value
@EqualsAndHashCode(callSuper=false)
public class GoOutTimeSheetOfDailyWork extends AggregateRoot{

	private List<GoOutTimeSheet> timeSheet;

	
	/**
	 * 勤務形態を見て外出時間帯の取得
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> getGoOutTimeSheet(AcquisitionConditionsAtr acqAtr ,WorkTimeMethodSet workTimeMethodSet
												 		  ,Optional<FluRestTime> fluRestTime,FluidPrefixBreakTimeSet fluidprefixBreakTimeSet){
		val goOutTimeSheetList = removeUnuseItemBaseOnAtr(acqAtr);
		switch(workTimeMethodSet) {
		case Enum_Fluid_Work:
			if(fluRestTime.isPresent()) {
				if(fluRestTime.get().getUseFixedRestTime()) {
					return convertFromConvertToBreakTime(fluidprefixBreakTimeSet, goOutTimeSheetList);
				}
			}
			else {
				throw new RuntimeException("faild get FluidSet");
			}
		case Enum_Fixed_Work:
		case Enum_Jogging_Time:
		case Enum_Overtime_Work:
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
	public List<TimeSheetOfDeductionItem> removeUnuseItemBaseOnAtr(AcquisitionConditionsAtr acqAtr) {
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		if(acqAtr.isForDeduction()) {
			for(GoOutTimeSheet goOutTimeSheet:timeSheet.stream()
					 .filter(tg -> tg.getGoOutReason().isPrivateOrUnion())
					 .collect(Collectors.toList())){
				returnList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(
																							, 
																							, Optional.empty()
																							, Optional.empty()
																							, Optional.empty()
																							, Optional.empty()
																							, goOutTimeSheet.getGoOutReason()
																							, breakAtr
																							, DeductionClassification.GO_OUT
																							, WithinStatutoryAtr.WithinStatutory));
			}
		}
		return returnList;
	}
	
	/**
	 * 外出時間帯から休憩時間帯への変換
	 * @return 控除項目の時間帯
	 */
	public List<TimeSheetOfDeductionItem> convertFromConvertToBreakTime(FluidPrefixBreakTimeSet fluidprefixBreakTimeSet,List<TimeSheetOfDeductionItem> deductionList){
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		for(TimeSheetOfDeductionItem deductionItem : deductionList) {
			if(fluidprefixBreakTimeSet.isPrivateGoOutTreatBreakTime()
				&& deductionItem.getGoOutReason().get().isPrivate()) {
				returnList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(deductionItem.getTimeSheet(),deductionItem.getCalcrange(),deductionItem.getDeductionTimeSheets()
						       ,deductionItem.getBonusPayTimeSheet(),deductionItem.getSpecifiedBonusPayTimeSheet(),deductionItem.getMidNightTimeSheet(),deductionItem.getGoOutReason()
						       ,Finally.of(BreakClassification.BREAK_STAMP),DeductionClassification.BREAK,deductionItem.getWithinStatutoryAtr()));
			}
			else if(fluidprefixBreakTimeSet.isUnionGoOutTreatBreakTime()
					&& deductionItem.getGoOutReason().get().isUnion()) {
				returnList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(deductionItem.getTimeSheet(),deductionItem.getCalcrange(),deductionItem.getDeductionTimeSheets()
					       ,deductionItem.getBonusPayTimeSheet(),deductionItem.getSpecifiedBonusPayTimeSheet(),deductionItem.getMidNightTimeSheet(),deductionItem.getGoOutReason()
					       ,Finally.of(BreakClassification.BREAK_STAMP),DeductionClassification.BREAK,deductionItem.getWithinStatutoryAtr()));
			}
			else {
				returnList.add(deductionItem);
			}
		}
		return returnList;
	}
}
