package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
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
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.CalcMethodIfLeaveWorkDuringBreakTime;
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
	private final DeductionClassification deductionAtr;
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
									,DeductionClassification deductionAtr
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
			,DeductionClassification deductionAtr
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
	public List<TimeSheetOfDeductionItem> DeplicateBreakGoOut(TimeSheetOfDeductionItem compareTimeSheet,WorkTimeMethodSet setMethod,BreakClockOfManageAtr clockManage,boolean useFixedRestTime) {
		List<TimeSheetOfDeductionItem> map = new ArrayList<TimeSheetOfDeductionItem>();
		/*両方とも育児　*/
		if(this.getDeductionAtr().isChildCare() && compareTimeSheet.getDeductionAtr().isChildCare()) {
			map.add(this);
			map.add(new TimeSheetOfDeductionItem(compareTimeSheet.calculationTimeSheet.getNotDuplicationWith(this.calculationTimeSheet).get(),compareTimeSheet.getGoOutReason(),compareTimeSheet.getBreakAtr(),compareTimeSheet.getDeductionAtr(),compareTimeSheet.getWithinStatutoryAtr()));
			return map;
		}
		/*前半育児　　後半外出*/
		else if(this.getDeductionAtr().isChildCare() && compareTimeSheet.getDeductionAtr().isGoOut()) {
			map.add(this);
			map.add(new TimeSheetOfDeductionItem(compareTimeSheet.calculationTimeSheet.getNotDuplicationWith(this.calculationTimeSheet).get(),compareTimeSheet.getGoOutReason(),compareTimeSheet.getBreakAtr(),compareTimeSheet.getDeductionAtr(),compareTimeSheet.getWithinStatutoryAtr()));
			return map;
		}
		/*前半外出、、後半育児*/
		else if(this.getDeductionAtr().isGoOut() && compareTimeSheet.getDeductionAtr().isChildCare()) {
			map.add(new TimeSheetOfDeductionItem(this.calculationTimeSheet.getNotDuplicationWith(compareTimeSheet.calculationTimeSheet).get(),this.getGoOutReason(),this.getBreakAtr(),this.getDeductionAtr(),this.getWithinStatutoryAtr()));
			map.add(compareTimeSheet);
			return map;
		}
		/*前半休憩、後半外出*/
		else if((this.getDeductionAtr().isBreak() && compareTimeSheet.getDeductionAtr().isGoOut())){
			if(setMethod.isFluidWork()) {
				if(!useFixedRestTime) {
					if(clockManage.isNotClockManage()) {
						return collectionBreakTime(this,compareTimeSheet);
					}
				}
			}			
			map.add(this);
			map.add(new TimeSheetOfDeductionItem(compareTimeSheet.calculationTimeSheet.getNotDuplicationWith(this.calculationTimeSheet).get(),compareTimeSheet.getGoOutReason(),compareTimeSheet.getBreakAtr(),compareTimeSheet.getDeductionAtr(),compareTimeSheet.getWithinStatutoryAtr()));
			return map;
		}
		/*前半外出、後半休憩*/
		else if(this.getDeductionAtr().isGoOut() && compareTimeSheet.getDeductionAtr().isBreak()){
			if(setMethod.isFluidWork()) {
				if(!useFixedRestTime) {
					if(clockManage.isNotClockManage()) {
						return collectionBreakTime(compareTimeSheet,this);
					}
				}
			}
			map.add(new TimeSheetOfDeductionItem(this.calculationTimeSheet.getNotDuplicationWith(compareTimeSheet.calculationTimeSheet).get(),this.getGoOutReason(),this.getBreakAtr(),this.getDeductionAtr(),this.getWithinStatutoryAtr()));
			map.add(compareTimeSheet);
			return map;
		}
		/*休憩系と休憩系*/
		else if(this.getDeductionAtr().isBreak() && compareTimeSheet.getDeductionAtr().isBreak()) {
			/*前半休憩、後半休憩打刻*/
			if(this.getBreakAtr().get().isBreak() && compareTimeSheet.getBreakAtr().get().isBreakStamp()) {
				map.add(new TimeSheetOfDeductionItem(this.calculationTimeSheet.getNotDuplicationWith(compareTimeSheet.calculationTimeSheet).get(),this.getGoOutReason(),this.getBreakAtr(),this.getDeductionAtr(),this.getWithinStatutoryAtr()));
				map.add(compareTimeSheet);
				return map;
			}
			/*前半休憩打刻、後半休憩*/
			else if((this.getBreakAtr().get().isBreakStamp() && compareTimeSheet.getBreakAtr().get().isBreak())){
				map.add(this);
				map.add(new TimeSheetOfDeductionItem(compareTimeSheet.calculationTimeSheet.getNotDuplicationWith(this.calculationTimeSheet).get(),compareTimeSheet.getGoOutReason(),compareTimeSheet.getBreakAtr(),compareTimeSheet.getDeductionAtr(),compareTimeSheet.getWithinStatutoryAtr()));
				return map;
			}
		}
		map.add(this);
		map.add(compareTimeSheet);
		return map; 
	}
	
	
	
	/**
	 * 休憩と外出時間帯の重複部分を補正する
	 * @param breakTimeSheet 休憩時間帯
	 * @param goOutTimeSheet　外出時間帯
	 * @return 補正後の休憩、外出時間帯
	 */
	public List<TimeSheetOfDeductionItem> collectionBreakTime(TimeSheetOfDeductionItem frontBreakTimeSheet, TimeSheetOfDeductionItem backGoOutTimeSheet){
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		switch(frontBreakTimeSheet.calculationTimeSheet.checkDuplication(backGoOutTimeSheet.calculationTimeSheet)) {
		case CONNOTATE_ENDTIME:
		case SAME_SPAN:
			returnList.add(new TimeSheetOfDeductionItem(frontBreakTimeSheet.calculationTimeSheet.shiftAhead(frontBreakTimeSheet.calculationTimeSheet.getDuplicatedWith(backGoOutTimeSheet.calculationTimeSheet).get().lengthAsMinutes()),frontBreakTimeSheet.getGoOutReason(),frontBreakTimeSheet.getBreakAtr(),frontBreakTimeSheet.getDeductionAtr(),frontBreakTimeSheet.getWithinStatutoryAtr()));
			returnList.add(backGoOutTimeSheet);
			return returnList;
		case CONTAINED:
			/*休憩を外出の後ろにずらす*/
			returnList.add(new TimeSheetOfDeductionItem(frontBreakTimeSheet.calculationTimeSheet.shiftAhead(backGoOutTimeSheet.calculationTimeSheet.getEnd().valueAsMinutes() - frontBreakTimeSheet.calculationTimeSheet.getStart().valueAsMinutes()),frontBreakTimeSheet.getGoOutReason(),frontBreakTimeSheet.getBreakAtr(),frontBreakTimeSheet.getDeductionAtr(),frontBreakTimeSheet.getWithinStatutoryAtr()));
			returnList.add(backGoOutTimeSheet);
		case CONTAINS:
		case CONNOTATE_BEGINTIME:
			returnList.add(new TimeSheetOfDeductionItem(new TimeSpanForCalc(frontBreakTimeSheet.start(),backGoOutTimeSheet.start()),frontBreakTimeSheet.getGoOutReason(),frontBreakTimeSheet.getBreakAtr(),frontBreakTimeSheet.getDeductionAtr(),frontBreakTimeSheet.getWithinStatutoryAtr()));
			returnList.add(backGoOutTimeSheet);
			returnList.add(new TimeSheetOfDeductionItem(new TimeSpanForCalc(backGoOutTimeSheet.end(),frontBreakTimeSheet.calculationTimeSheet.getEnd().backByMinutes(backGoOutTimeSheet.calculationTimeSheet.lengthAsMinutes())),frontBreakTimeSheet.getGoOutReason(),frontBreakTimeSheet.getBreakAtr(),frontBreakTimeSheet.getDeductionAtr(),frontBreakTimeSheet.getWithinStatutoryAtr()));
			return returnList;
		case NOT_DUPLICATE:
			returnList.add(frontBreakTimeSheet);
			returnList.add(backGoOutTimeSheet);
			return returnList;
		default:
			throw new RuntimeException("unknown duplicate Atr" + frontBreakTimeSheet.calculationTimeSheet.checkDuplication(backGoOutTimeSheet.calculationTimeSheet));
		}
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
