package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecifiedbonusPayTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.BreakClockOfManageAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * 控除項目の時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class TimeSheetOfDeductionItem extends CalculationTimeSheet{
	private Finally<GoingOutReason> goOutReason;
	private Finally<BreakClassification> breakAtr;
	private final DeductionClassification deductionAtr;
	private final WithinStatutoryAtr withinStatutoryAtr;
	
//	private final DedcutionClassification deductionClassification;
//	private final BreakClassification breakClassification;

	/**
	 * 控除項目の時間帯作成
	 * @param timeSpan
	 * @param goOutReason
	 * @param breakAtr
	 * @param deductionAtr
	 * @param withinStatutoryAtr
	 */
	private TimeSheetOfDeductionItem(TimeSpanWithRounding withRounding
									,TimeSpanForCalc timeSpan
									,List<TimeSheetOfDeductionItem> deductionTimeSheets
									,List<BonusPayTimesheet> bonusPayTimeSheet
									,List<SpecifiedbonusPayTimeSheet> specifiedBonusPayTimeSheet
									,Optional<MidNightTimeSheet> midNighttimeSheet
									,Finally<GoingOutReason> goOutReason
									,Finally<BreakClassification> breakAtr
									,DeductionClassification deductionAtr
									,WithinStatutoryAtr withinStatutoryAtr) {
		super(withRounding,timeSpan,deductionTimeSheets,bonusPayTimeSheet,specifiedBonusPayTimeSheet,midNighttimeSheet);
		this.goOutReason = goOutReason;
		this.breakAtr = breakAtr;
		this.deductionAtr = deductionAtr;
		this.withinStatutoryAtr = withinStatutoryAtr;
	}
	
	/**
	 * 控除項目の時間帯作成　
	 * @param timeSpan
	 * @param goOutReason
	 * @param breakAtr
	 * @param deductionAtr
	 * @param withinStatutoryAtr
	 * @return
	 */
	public static TimeSheetOfDeductionItem createTimeSheetOfDeductionItemAsFixed(TimeSpanWithRounding withRounding
			,TimeSpanForCalc timeSpan
			,List<TimeSheetOfDeductionItem> deductionTimeSheets
			,List<BonusPayTimesheet> bonusPayTimeSheet
			,List<SpecifiedbonusPayTimeSheet> specifiedBonusPayTimeSheet
			,Optional<MidNightTimeSheet> midNighttimeSheet
			,Finally<GoingOutReason> goOutReason
			,Finally<BreakClassification> breakAtr
			,DeductionClassification deductionAtr
			,WithinStatutoryAtr withinStatutoryAtr) {
		
		return new TimeSheetOfDeductionItem(
				withRounding
				,timeSpan
				,deductionTimeSheets
				,bonusPayTimeSheet
				,specifiedBonusPayTimeSheet
				,midNighttimeSheet
				,goOutReason
				,breakAtr
				,deductionAtr
				,withinStatutoryAtr);
	}
	

	/**
	 * 控除項目の時間帯の法定内区分を法定外へ置き換える
	 * @return 法定内区分を法定外に変更した控除項目の時間帯
	 */
	public TimeSheetOfDeductionItem StatutoryAtrFromWithinToExcess() {
		return new TimeSheetOfDeductionItem(this.timeSheet, 
											this.calcrange, 
											this.deductionTimeSheets, 
											this.bonusPayTimeSheet,
											this.specifiedBonusPayTimeSheet, 
											this.midNightTimeSheet, 
											this.goOutReason, 
											this.breakAtr, 
											this.deductionAtr, 
											WithinStatutoryAtr.ExcessOfStatutory);
	}
	
	/**
	 * 休　と外　の重　
	 * @param baseTimeSheet 現ループ中のリス　
	 * @param compareTimeSheet　次のループで取り出すリス　
	 */
	public List<TimeSheetOfDeductionItem> DeplicateBreakGoOut(TimeSheetOfDeductionItem compareTimeSheet,WorkTimeMethodSet setMethod,BreakClockOfManageAtr clockManage
															,boolean useFixedRestTime,FluidFixedAtr fluidFixedAtr) {
		List<TimeSheetOfDeductionItem> map = new ArrayList<TimeSheetOfDeductionItem>();
	
		/*両方とも育児　*/
		if(this.getDeductionAtr().isChildCare() && compareTimeSheet.getDeductionAtr().isChildCare()) {
			map.add(this);
			map.add(new TimeSheetOfDeductionItem(compareTimeSheet.timeSheet
												,compareTimeSheet.calcrange.getNotDuplicationWith(this.calcrange).get()
												,compareTimeSheet.deductionTimeSheets
												,compareTimeSheet.bonusPayTimeSheet
												,compareTimeSheet.specifiedBonusPayTimeSheet
												,compareTimeSheet.midNightTimeSheet
												,compareTimeSheet.goOutReason
												,compareTimeSheet.breakAtr
												,compareTimeSheet.deductionAtr
												,compareTimeSheet.getWithinStatutoryAtr()));
			return map;
		}
		/*前半育児　　後半外出*/
		else if(this.getDeductionAtr().isChildCare() && compareTimeSheet.getDeductionAtr().isGoOut()) {
			map.add(this);
			map.add(new TimeSheetOfDeductionItem(compareTimeSheet.timeSheet
					,compareTimeSheet.calcrange.getNotDuplicationWith(this.calcrange).get()
					,compareTimeSheet.deductionTimeSheets
					,compareTimeSheet.bonusPayTimeSheet
					,compareTimeSheet.specifiedBonusPayTimeSheet
					,compareTimeSheet.midNightTimeSheet
					,compareTimeSheet.goOutReason
					,compareTimeSheet.breakAtr
					,compareTimeSheet.deductionAtr
					,compareTimeSheet.getWithinStatutoryAtr()));
			return map;
		}
		/*前半外出、、後半育児*/
		else if(this.getDeductionAtr().isGoOut() && compareTimeSheet.getDeductionAtr().isChildCare()) {
			map.add(new TimeSheetOfDeductionItem( this.timeSheet
												 ,this.calcrange.getNotDuplicationWith(compareTimeSheet.calcrange).get()
												 ,this.deductionTimeSheets
												 ,this.bonusPayTimeSheet
												 ,this.specifiedBonusPayTimeSheet
												 ,this.midNightTimeSheet
												 ,this.getGoOutReason()
												 ,this.getBreakAtr()
												 ,this.getDeductionAtr()
												 ,this.getWithinStatutoryAtr()));
			map.add(compareTimeSheet);
			return map;
		}
		/*前半休憩、後半外出*/
		else if((this.getDeductionAtr().isBreak() && compareTimeSheet.getDeductionAtr().isGoOut())){
			if(!fluidFixedAtr.isFluidWork()) {
				TimeSpanForCalc duplicationSpan = this.getCalcrange().getDuplicatedWith(compareTimeSheet.getCalcrange()).get();
				map.add(new TimeSheetOfDeductionItem( this.timeSheet,
													  this.calcrange.getNotDuplicationWith(compareTimeSheet.calcrange).get(),
													  this.deductionTimeSheets,
													  this.bonusPayTimeSheet,
													  this.specifiedBonusPayTimeSheet,
													  this.midNightTimeSheet,
													  this.getGoOutReason(),
													  this.getBreakAtr(),
													  this.getDeductionAtr(),
													  this.getWithinStatutoryAtr()));/*休憩は外出と被ってない部分で作成*/
				List<TimeSheetOfDeductionItem> replaceDeductionItemList = this.deductionTimeSheets;
				replaceDeductionItemList.add(new TimeSheetOfDeductionItem(new TimeSpanWithRounding(duplicationSpan.getStart(), duplicationSpan.getEnd(), Finally.empty())
																		 , duplicationSpan
																		 , Collections.emptyList()
																		 , Collections.emptyList()
																		 , Collections.emptyList()
																		 , Optional.empty()
																		 , this.getGoOutReason()
																		 , this.breakAtr
																		 , this.getDeductionAtr()
																		 , this.getWithinStatutoryAtr()));
				map.add(new TimeSheetOfDeductionItem(compareTimeSheet.timeSheet,
													 compareTimeSheet.calcrange,
													 compareTimeSheet.deductionTimeSheets,
													 compareTimeSheet.bonusPayTimeSheet,
													 compareTimeSheet.specifiedBonusPayTimeSheet,
													 compareTimeSheet.midNightTimeSheet,
													 compareTimeSheet.getGoOutReason(),
													 compareTimeSheet.getBreakAtr(),
													 compareTimeSheet.getDeductionAtr(),
													 compareTimeSheet.getWithinStatutoryAtr()));/*外出は今まで通りの期間+外出の控除に変化する領域を保持させる*/
				return map;
			}
			else {
				if(setMethod.isFluidWork()) {
					if(!useFixedRestTime) {
						if(clockManage.isNotClockManage()) {
							return collectionBreakTime(this,compareTimeSheet);
						}
					}
				}
			}
			map.add(this);
			map.add(new TimeSheetOfDeductionItem(compareTimeSheet.timeSheet
												,compareTimeSheet.calcrange.getNotDuplicationWith(this.calcrange).get()
												,compareTimeSheet.deductionTimeSheets
												,compareTimeSheet.bonusPayTimeSheet
												,compareTimeSheet.specifiedBonusPayTimeSheet
												,compareTimeSheet.midNightTimeSheet
												,compareTimeSheet.getGoOutReason()
												,compareTimeSheet.getBreakAtr()
												,compareTimeSheet.getDeductionAtr()
												,compareTimeSheet.getWithinStatutoryAtr()));
			return map;
		}
		/*前半外出、後半休憩*/
		else if(this.getDeductionAtr().isGoOut() && compareTimeSheet.getDeductionAtr().isBreak()){
			if(!fluidFixedAtr.isFluidWork()) {
				TimeSpanForCalc duplicationSpan = compareTimeSheet.getCalcrange().getDuplicatedWith(this.getCalcrange()).get();
				map.add(new TimeSheetOfDeductionItem( compareTimeSheet.timeSheet
													 ,compareTimeSheet.calcrange.getNotDuplicationWith(this.calcrange).get()
													 ,compareTimeSheet.deductionTimeSheets
													 ,compareTimeSheet.bonusPayTimeSheet
													 ,compareTimeSheet.specifiedBonusPayTimeSheet
													 ,compareTimeSheet.midNightTimeSheet
													 ,compareTimeSheet.getGoOutReason()
													 ,compareTimeSheet.getBreakAtr()
													 ,compareTimeSheet.getDeductionAtr()
													 ,compareTimeSheet.getWithinStatutoryAtr()));/*休憩は外出と被ってない部分で作成*/
				List<TimeSheetOfDeductionItem> replaceDeductionItemList = compareTimeSheet.deductionTimeSheets;
				replaceDeductionItemList.add(new TimeSheetOfDeductionItem(new TimeSpanWithRounding(duplicationSpan.getStart(), duplicationSpan.getEnd(), Finally.empty())
																		, duplicationSpan
																		, Collections.emptyList()
																		, Collections.emptyList()
																		, Collections.emptyList()
																		, Optional.empty()
																		, this.getGoOutReason()
																		, this.getBreakAtr()
																		, this.getDeductionAtr()
																		, this.getWithinStatutoryAtr()));
				map.add(new TimeSheetOfDeductionItem( this.timeSheet
													 ,this.calcrange
													 ,this.deductionTimeSheets
													 ,this.bonusPayTimeSheet
													 ,this.specifiedBonusPayTimeSheet
													 ,this.midNightTimeSheet
													 ,this.goOutReason
													 ,this.breakAtr
													 ,this.deductionAtr
													 ,this.withinStatutoryAtr));/*外出は今まで通りの期間+外出の控除に変化する領域を保持させる*/
				return map;
			}
			else {
				if(setMethod.isFluidWork()) {
					if(!useFixedRestTime) {
						if(clockManage.isNotClockManage()) {
							return collectionBreakTime(compareTimeSheet,this);
						}
					}
				}
			}
			map.add(new TimeSheetOfDeductionItem(this.timeSheet
												 ,this.calcrange.getNotDuplicationWith(compareTimeSheet.calcrange).get()
												 ,this.deductionTimeSheets
												 ,this.bonusPayTimeSheet
												 ,this.specifiedBonusPayTimeSheet
												 ,this.midNightTimeSheet
												 ,this.getGoOutReason()
												 ,this.getBreakAtr()
												 ,this.getDeductionAtr()
												 ,this.getWithinStatutoryAtr()));
			map.add(compareTimeSheet);
			return map;
		}
		/*休憩系と休憩系*/
		else if(this.getDeductionAtr().isBreak() && compareTimeSheet.getDeductionAtr().isBreak()) {
			/*前半休憩、後半休憩打刻*/
			if(this.getBreakAtr().get().isBreak() && compareTimeSheet.getBreakAtr().get().isBreakStamp()) {
				map.add(new TimeSheetOfDeductionItem( this.timeSheet
													 ,this.calcrange.getNotDuplicationWith(compareTimeSheet.calcrange).get()
													 ,this.deductionTimeSheets
													 ,this.bonusPayTimeSheet
													 ,this.specifiedBonusPayTimeSheet
													 ,this.midNightTimeSheet
													 ,this.getGoOutReason()
													 ,this.getBreakAtr()
													 ,this.getDeductionAtr()
													 ,this.getWithinStatutoryAtr()));
				map.add(compareTimeSheet);
				return map;
			}
			/*前半休憩打刻、後半休憩*/
			else if((this.getBreakAtr().get().isBreakStamp() && compareTimeSheet.getBreakAtr().get().isBreak())){
				map.add(this);
				map.add(new TimeSheetOfDeductionItem( compareTimeSheet.timeSheet
													 ,compareTimeSheet.calcrange.getNotDuplicationWith(this.calcrange).get()
													 ,compareTimeSheet.deductionTimeSheets
													 ,compareTimeSheet.bonusPayTimeSheet
													 ,compareTimeSheet.specifiedBonusPayTimeSheet
													 ,compareTimeSheet.midNightTimeSheet
													 ,compareTimeSheet.getGoOutReason()
													 ,compareTimeSheet.getBreakAtr()
													 ,compareTimeSheet.getDeductionAtr()
													 ,compareTimeSheet.getWithinStatutoryAtr()));
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
		switch(frontBreakTimeSheet.calcrange.checkDuplication(backGoOutTimeSheet.calcrange)) {
		case CONNOTATE_ENDTIME:
		case SAME_SPAN:
			returnList.add(new TimeSheetOfDeductionItem(frontBreakTimeSheet.timeSheet,
														frontBreakTimeSheet.calcrange.shiftAhead(frontBreakTimeSheet.calcrange.getDuplicatedWith(backGoOutTimeSheet.calcrange).get().lengthAsMinutes()),
														frontBreakTimeSheet.deductionTimeSheets,
														frontBreakTimeSheet.bonusPayTimeSheet,
														frontBreakTimeSheet.specifiedBonusPayTimeSheet,
														frontBreakTimeSheet.midNightTimeSheet,
														frontBreakTimeSheet.getGoOutReason(),
														frontBreakTimeSheet.getBreakAtr(),
														frontBreakTimeSheet.getDeductionAtr(),
														frontBreakTimeSheet.getWithinStatutoryAtr()));
			returnList.add(backGoOutTimeSheet);
			return returnList;
		case CONTAINED:
			/*休憩を外出の後ろにずらす*/
			returnList.add(new TimeSheetOfDeductionItem(frontBreakTimeSheet.timeSheet,
														frontBreakTimeSheet.calcrange.shiftAhead(backGoOutTimeSheet.calcrange.getEnd().valueAsMinutes() - frontBreakTimeSheet.calcrange.getStart().valueAsMinutes()),
														frontBreakTimeSheet.deductionTimeSheets,
														frontBreakTimeSheet.bonusPayTimeSheet,
														frontBreakTimeSheet.specifiedBonusPayTimeSheet,
														frontBreakTimeSheet.midNightTimeSheet,
														frontBreakTimeSheet.getGoOutReason(),
														frontBreakTimeSheet.getBreakAtr(),
														frontBreakTimeSheet.getDeductionAtr(),
														frontBreakTimeSheet.getWithinStatutoryAtr()));
			returnList.add(backGoOutTimeSheet);
		case CONTAINS:
		case CONNOTATE_BEGINTIME:
			returnList.add(new TimeSheetOfDeductionItem(new TimeSpanWithRounding(frontBreakTimeSheet.start(), backGoOutTimeSheet.start(), Finally.empty()),
														new TimeSpanForCalc(frontBreakTimeSheet.start(),backGoOutTimeSheet.start()),
														frontBreakTimeSheet.deductionTimeSheets,
														frontBreakTimeSheet.bonusPayTimeSheet,
														frontBreakTimeSheet.specifiedBonusPayTimeSheet,
														frontBreakTimeSheet.midNightTimeSheet,
														frontBreakTimeSheet.getGoOutReason(),
														frontBreakTimeSheet.getBreakAtr(),
														frontBreakTimeSheet.getDeductionAtr(),
														frontBreakTimeSheet.getWithinStatutoryAtr()));
			returnList.add(backGoOutTimeSheet);
			returnList.add(new TimeSheetOfDeductionItem(new TimeSpanWithRounding(frontBreakTimeSheet.start(), backGoOutTimeSheet.start(), Finally.empty()),
														new TimeSpanForCalc(backGoOutTimeSheet.end(),frontBreakTimeSheet.calcrange.getEnd().backByMinutes(backGoOutTimeSheet.calcrange.lengthAsMinutes())),
														frontBreakTimeSheet.deductionTimeSheets,
														frontBreakTimeSheet.bonusPayTimeSheet,
														frontBreakTimeSheet.specifiedBonusPayTimeSheet,
														frontBreakTimeSheet.midNightTimeSheet,
														frontBreakTimeSheet.getGoOutReason(),
														frontBreakTimeSheet.getBreakAtr(),
														frontBreakTimeSheet.getDeductionAtr(),
														frontBreakTimeSheet.getWithinStatutoryAtr()));
			return returnList;
		case NOT_DUPLICATE:
			returnList.add(frontBreakTimeSheet);
			returnList.add(backGoOutTimeSheet);
			return returnList;
		default:
			throw new RuntimeException("unknown duplicate Atr" + frontBreakTimeSheet.calcrange.checkDuplication(backGoOutTimeSheet.calcrange));
		}
	}
	
	
	public TimeWithDayAttr start() {
		return ((CalculationTimeSheet)this).calcrange.getStart();
		//return this.span.getStart();
	}
	
	public TimeWithDayAttr end() {
		return ((CalculationTimeSheet)this).calcrange.getEnd();
	}
	
//	public List<TimeSheetOfDeductionItem> devideAt(TimeWithDayAttr baseTime) {
//		return ((CalculationTimeSheet)this).calcrange.timeDivide(baseTime).stream()
//				.map(t -> new TimeSheetOfDeductionItem(t))
//				.collect(Collectors.toList());
//	}
	
//	public List<TimeSheetOfDeductionItem> devideIfContains(TimeWithDayAttr baseTime) {
//		if (this.contains(baseTime)) {
//			return this.devideAt(baseTime);
//		} else {
//			return Arrays.asList(this);
//		}
//	}
	
	public boolean contains(TimeWithDayAttr baseTime) {
		return ((CalculationTimeSheet)this).calcrange.contains(baseTime);
	}
	
	
	/**
	 * 終了時間と基準時間の早い方の時間を取得する
	 * @param basePoint　基準時間
	 * @return 時刻が早い方
	 */
	public TimeSpanForCalc decisionNewSpan(TimeSpanForCalc timeSpan,TimeWithDayAttr baseTime,boolean isDateBefore) {
		if(isDateBefore) {
			return new TimeSpanForCalc(timeSpan.getStart(),baseTime);
		}
		else {
			return new TimeSpanForCalc(baseTime,timeSpan.getEnd());
		}
	}
	
	/**
	 * 再帰中に自分自身を作り直す処理
	 * @param baseTime
	 * @return
	 */
	public TimeSheetOfDeductionItem reCreateOwn(TimeWithDayAttr baseTime,boolean isDateBefore) {
			List<TimeSheetOfDeductionItem>  deductionTimeSheets = this.recreateDeductionItemBeforeBase(baseTime,isDateBefore);
			List<BonusPayTimesheet>         bonusPayTimeSheet = this.recreateBonusPayListBeforeBase(baseTime,isDateBefore);
			List<SpecifiedbonusPayTimeSheet>specifiedBonusPayTimeSheet = this.recreateSpecifiedBonusPayListBeforeBase(baseTime, isDateBefore);
			Optional<MidNightTimeSheet>     midNighttimeSheet = this.recreateMidNightTimeSheetBeforeBase(baseTime,isDateBefore);
			TimeSpanForCalc renewSpan = decisionNewSpan(this.calcrange,baseTime,isDateBefore);
			return new TimeSheetOfDeductionItem(this.getTimeSheet(),
												renewSpan,
												deductionTimeSheets,
												bonusPayTimeSheet,
												specifiedBonusPayTimeSheet,
												midNighttimeSheet,this.goOutReason,this.breakAtr,this.deductionAtr,this.withinStatutoryAtr);
	}
	
	/**
	 * 法定内区分を法定外にして自分自身を作り直す
	 */
	public TimeSheetOfDeductionItem createWithExcessAtr(){
		return new TimeSheetOfDeductionItem(this.getTimeSheet(),this.calcrange,this.deductionTimeSheets,this.bonusPayTimeSheet,this.specifiedBonusPayTimeSheet,this.midNightTimeSheet,this.goOutReason,this.breakAtr,this.deductionAtr,WithinStatutoryAtr.WithinStatutory);
	}
}
