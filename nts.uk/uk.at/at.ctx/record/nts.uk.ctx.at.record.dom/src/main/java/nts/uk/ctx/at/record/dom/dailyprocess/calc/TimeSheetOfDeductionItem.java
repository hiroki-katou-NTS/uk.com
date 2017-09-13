package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.StampGoOutReason;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.CalcMethodIfLeaveWorkDuringBreakTime;
import nts.uk.ctx.at.shared.dom.worktime.basicinformation.SettingMethod;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.set.FixRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.BreakClockOfManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidBreakTimeOfCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidPrefixBreakTimeOfCalcMethod;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * 控除　　の時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class TimeSheetOfDeductionItem extends CalculationTimeSheet{
	private Finally<StampGoOutReason> goOutReason;
	private Finally<BreakClassification> breakAtr;
	private final DedcutionClassification deductionAtr;
	private final WithinStatutoryAtr withinStatutoryAtr;
	
//	private final DedcutionClassification deductionClassification;
//	private final BreakClassification breakClassification;

	/**
	 * 休　時間帯取得時格納用
	 * @param timeSpan
	 * @param goOutReason
	 * @param breakAtr
	 * @param deductionAtr
	 * @param withinStatutoryAtr
	 */
	private TimeSheetOfDeductionItem(TimeSpanForCalc timeSpan
									,Finally<StampGoOutReason> goOutReason
									,Finally<BreakClassification> breakAtr
									,DedcutionClassification deductionAtr
									,WithinStatutoryAtr withinStatutoryAtr) {
		super(new TimeSpanWithRounding(timeSpan.getStart(),timeSpan.getEnd(),null),timeSpan);
		this.goOutReason = goOutReason;
		this.breakAtr = breakAtr;
		this.deductionAtr = deductionAtr;
		this.withinStatutoryAtr = withinStatutoryAtr;
	}
	
	/**
	 * 固定勤務　休　時間帯取　
	 * @param timeSpan
	 * @param goOutReason
	 * @param breakAtr
	 * @param deductionAtr
	 * @param withinStatutoryAtr
	 * @return
	 */
	public static TimeSheetOfDeductionItem reateBreakTimeSheetAsFixed(TimeSpanForCalc timeSpan
			,Finally<StampGoOutReason> goOutReason
			,Finally<BreakClassification> breakAtr
			,DedcutionClassification deductionAtr
			,WithinStatutoryAtr withinStatutoryAtr) {
		
		return new TimeSheetOfDeductionItem(
				timeSpan,
				goOutReason,
				breakAtr,
				deductionAtr,
				withinStatutoryAtr
				);
	}
	

	/**
	 * 休　と外　の重　
	 * @param baseTimeSheet 現ループ中のリス　
	 * @param compareTimeSheet　次のループで取り出すリス　
	 */
	public Map<TimeSpanForCalc,Boolean> DeplicateBreakGoOut(TimeSheetOfDeductionItem compareTimeSheet,SettingMethod setMethod,BreakClockOfManageAtr clockManage) {
		Map<TimeSpanForCalc, Boolean> map = new HashMap<TimeSpanForCalc,Boolean>();
		/*両方とも外　*/
		if(this.getDeductionAtr().isGoOut() && compareTimeSheet.getDeductionAtr().isGoOut()) {
			map.put(compareTimeSheet.calculationTimeSheet.getNotDuplicationWith(this.calculationTimeSheet).get(),Boolean.FALSE);
			return map;
		}
		/*両方とも育　*/
		else if(this.getDeductionAtr().isChildCare() && compareTimeSheet.getDeductionAtr().isChildCare()) {
			map.put(compareTimeSheet.calculationTimeSheet.getNotDuplicationWith(this.calculationTimeSheet).get(),Boolean.FALSE);
			return map;
		}
		/*前半育児　　後半外　*/
		else if(this.getDeductionAtr().isChildCare() && compareTimeSheet.getDeductionAtr().isGoOut()) {
			map.put(compareTimeSheet.calculationTimeSheet.getNotDuplicationWith(this.calculationTimeSheet).get(),Boolean.FALSE);
			return map;
		}
		/*前半外　、後半育　*/
		else if(this.getDeductionAtr().isGoOut() && compareTimeSheet.getDeductionAtr().isChildCare()) {
			map.put(this.calculationTimeSheet.getNotDuplicationWith(compareTimeSheet.calculationTimeSheet).get(),Boolean.FALSE);
			return map;
		}
		/*前半休　、後半外　*/
		else if((this.getDeductionAtr().isBreak() && compareTimeSheet.getDeductionAtr().isGoOut())){
			if(setMethod.isFluidWork()) {
				if(clockManage.isNotClockManage()) {
					map.put(compareTimeSheet.calculationTimeSheet.getNotDuplicationWith(this.calculationTimeSheet).get(),Boolean.FALSE);
					return map;
				}
				else {
					/*アルゴリズ　のパターン不足を発見したため　　いったん保留*/
					map.put(compareTimeSheet.calculationTimeSheet.getNotDuplicationWith(this.calculationTimeSheet).get(),Boolean.FALSE);
					return map;
				}
			}
		}
		/*前半外　、後半休　*/
		else if(this.getDeductionAtr().isGoOut() && compareTimeSheet.getDeductionAtr().isBreak()){
			if(setMethod.isFluidWork()) {
				if(clockManage.isNotClockManage()) {
					map.put(compareTimeSheet.calculationTimeSheet.getNotDuplicationWith(this.calculationTimeSheet).get(),Boolean.FALSE);
					return map;
				}
				else
				{
					map.put(this.calculationTimeSheet.getNotDuplicationWith(compareTimeSheet.calculationTimeSheet).get(),Boolean.FALSE);
					return map;
				}
			}
			
		}
		else if(this.getDeductionAtr().isBreak() && compareTimeSheet.getDeductionAtr().isBreak()) {
			/*前半休　、後半休　打刻*/
			if(this.getBreakAtr().isBreak() && compareTimeSheet.getBreakAtr().isBreakStamp()) {
				
			}
			/*前半休　打刻、後半休　*/
			else if((this.getBreakAtr().isBreakStamp() && compareTimeSheet.getBreakAtr().isBreak())){
					
			}
			/*両方とも休　打刻*/
			else if(this.getBreakAtr().isBreakStamp() && compareTimeSheet.getBreakAtr().isBreakStamp()) {
				
			}
		}
		map.put(this.calculationTimeSheet,Boolean.FALSE);
		return map; 
	}
	
	
	public TimeWithDayAttr start() {
		return ((CalculationTimeSheet)this).calculationTimeSheet.getStart();
		//return this.span.getStart();
	}
	
	public TimeWithDayAttr end() {
		return ((CalculationTimeSheet)this).calculationTimeSheet.getEnd();
	}
	
	public List<TimeSheetOfDeductionItem> devideAt(TimeWithDayAttr baseTime) {
		return ((CalculationTimeSheet)this).calculationTimeSheet.timeDivide(baseTime).stream()
				.map(t -> new TimeSheetOfDeductionItem(t))
				.collect(Collectors.toList());
	}
	
	public List<TimeSheetOfDeductionItem> devideIfContains(TimeWithDayAttr baseTime) {
		if (this.contains(baseTime)) {
			return this.devideAt(baseTime);
		} else {
			return Arrays.asList(this);
		}
	}
	
	public boolean contains(TimeWithDayAttr baseTime) {
		return ((CalculationTimeSheet)this).calculationTimeSheet.contains(baseTime);
	}
}
