package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 深夜時間帯
 * @author keisuke_hoshina
 *
 */
public class MidNightTimeSheetForCalc extends CalculationTimeSheet {

	public MidNightTimeSheetForCalc(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding, List<TimeSheetOfDeductionItem> recorddeductionSheets,List<TimeSheetOfDeductionItem> deductionSheets) {
		super(timeSheet, rounding, recorddeductionSheets,deductionSheets);
	}
	
	
	public MidNightTimeSheetForCalc replaceTime(TimeSpanForCalc timeSpan) {
		return new MidNightTimeSheetForCalc(new TimeSpanForDailyCalc(timeSpan.getStart(), timeSpan.getEnd()),
											this.rounding,
											this.recordedTimeSheet,
											this.deductionTimeSheet);
	}
	
	
	public boolean contains(TimeWithDayAttr baseTime) {
		return ((CalculationTimeSheet)this).getTimeSheet().contains(baseTime);
	}
	/**
	 * 終了時間と基準時間の早い方の時間を取得する
	 * @param basePoint　基準時間
	 * @return 時刻が早い方
	 */
	public TimeSpanForDailyCalc decisionNewSpan(TimeSpanForDailyCalc timeSpan,TimeWithDayAttr baseTime,boolean isDateBefore) {
		if(isDateBefore) {
			val endOclock = timeSpan.getEnd().lessThan(baseTime) ? timeSpan.getEnd() : baseTime; 
			return new TimeSpanForDailyCalc(timeSpan.getStart(),endOclock);
		}
		else {
			val startOclock = baseTime.lessThan(timeSpan.getStart()) ? timeSpan.getStart() : baseTime ;
			return new TimeSpanForDailyCalc(startOclock,timeSpan.getEnd());
		}
		
	}
	/**
	 * 再帰中に自分自身を作り直す処理
	 * @param baseTime
	 * @return
	 */
	public Optional<MidNightTimeSheetForCalc> reCreateOwn(TimeWithDayAttr baseTime,boolean isDateBefore) {
		List<TimeSheetOfDeductionItem> deductionTimeSheets = this.recreateDeductionItemBeforeBase(baseTime,isDateBefore,DeductionAtr.Appropriate);
		List<TimeSheetOfDeductionItem> recordTimeSheets = this.recreateDeductionItemBeforeBase(baseTime,isDateBefore,DeductionAtr.Deduction);
		TimeSpanForDailyCalc renewSpan = decisionNewSpan(this.timeSheet,baseTime,isDateBefore);
		return Optional.of(new MidNightTimeSheetForCalc(renewSpan,this.rounding,deductionTimeSheets,recordTimeSheets));
	}
	
	/**
	 * 重複範囲の時間帯を持つ計算用深夜時間帯に作り直す
	 * @param timeSpan　重複を調べたい時間帯
	 * @return　重複範囲の時間帯深夜時間帯
	 */
	public Optional<MidNightTimeSheetForCalc> getDuplicateRangeTimeSheet(TimeSpanForDailyCalc timeSpan) {
		
		val dupTimeSpan = timeSpan.getDuplicatedWith(this.timeSheet);
		if(dupTimeSpan.isPresent()) {
			return Optional.of(new MidNightTimeSheetForCalc(dupTimeSpan.get(), 
											this.rounding, 
											this.recordedTimeSheet, 
											this.deductionTimeSheet));
		}
		else {
			return Optional.empty();
		}
	}
	
	/**
	 * 深夜時間帯のリストを作り直す
	 * @param baseTime 基準時間
	 * @param isDateBefore 基準時間より早い時間を切り出す
	 * @return 切り出した深夜時間帯
	 */
	public Optional<MidNightTimeSheetForCalc> recreateMidNightTimeSheetBeforeBase(TimeWithDayAttr baseTime, boolean isDateBefore){
		if(this.getTimeSheet().getTimeSpan().contains(baseTime)) {
			return this.reCreateOwn(baseTime, isDateBefore);
		}
		else if(this.getTimeSheet().getTimeSpan().getEnd().lessThan(baseTime) && isDateBefore) {
			return Optional.of(this);
		}
		else if(this.getTimeSheet().getTimeSpan().getStart().greaterThan(baseTime) && !isDateBefore) {
			return Optional.of(this);
		}
		return Optional.empty();
	}
	
	/**
	 * 計算用深夜時間帯を作成する
	 * @param calcRange 計算範囲
	 * @param midNightTimeSheet 深夜時間帯
	 * @param dedTimeSheet 控除時間帯
	 * @param recordTimeSheet 計上用控除時間帯
	 * @param roundSetting 深夜時間丸め設定
	 * @return 計算範囲と重複した計算用深夜時間帯
	 */
	public static Optional<MidNightTimeSheetForCalc> create(
			TimeSpanForDailyCalc calcRange,
			TimeSpanForDailyCalc midNightTimeSheet,
			List<TimeSheetOfDeductionItem> dedTimeSheet,
			List<TimeSheetOfDeductionItem> recordTimeSheet,
			TimeRoundingSetting roundSetting){
		//計算範囲と深夜時間帯の重複した時間帯を取得
		Optional<TimeSpanForDailyCalc> duplicate = calcRange.getDuplicatedWith(midNightTimeSheet);
		if(duplicate.isPresent()) {
			return Optional.empty();
		}
		
		//計算用深夜時間帯を作成
		MidNightTimeSheetForCalc duplicatedMidNight = new MidNightTimeSheetForCalc(
				duplicate.get(),
				roundSetting,
				recordTimeSheet,
				dedTimeSheet);
		
		//計上用の控除時間帯を重複した範囲でセットする
		duplicatedMidNight.addDuplicatedDeductionTimeSheet(
				dedTimeSheet,
				DeductionAtr.Deduction,
				Optional.of(new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
		
		//控除用の控除時間帯を重複した範囲でセットする
		duplicatedMidNight.addDuplicatedDeductionTimeSheet(
				recordTimeSheet,
				DeductionAtr.Appropriate,
				Optional.of(new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
		
		return Optional.of(duplicatedMidNight);
	}
	
	/**
	 * 指定された時間帯と重複している深夜時間帯を取得
	 * @param midNightTimeSheet
	 * @param duplicateTimeSheet
	 * @return
	 */
	public static Optional<MidNightTimeSheetForCalc> getDuplicateMidNight(MidNightTimeSheetForCalc midNightTimeSheet, TimeSpanForDailyCalc timeSpan){ 
		val duplicateMidNightSpan = timeSpan.getDuplicatedWith(midNightTimeSheet.getTimeSheet());
		if(duplicateMidNightSpan.isPresent()) {
			return Optional.of(midNightTimeSheet.replaceTime(duplicateMidNightSpan.get().getTimeSpan()));
		}
		return Optional.empty();
	}
	
	/**
	 * 所定内と所定外に分けて取得する
	 * @param outSideStart 所定外開始時刻
	 * @return 所定内・外の深夜時間帯
	 */
	public InOutMidNightTimeSheet getWithinAndOutSide(TimeWithDayAttr outSideStart) {
		InOutMidNightTimeSheet inOut = new InOutMidNightTimeSheet(new ArrayList<>(), new ArrayList<>());
		if(this.timeSheet.getEnd().lessThanOrEqualTo(outSideStart)) {
			//深夜時間帯を所定内に追加する
			inOut.within.add(this);
			return inOut;
		}
		if(this.timeSheet.getStart().lessThan(outSideStart)
				&& outSideStart.lessThan(this.timeSheet.getEnd())) {
			//計算用深夜時間帯（所定内）の作成
			MidNightTimeSheetForCalc within = new MidNightTimeSheetForCalc(
					new TimeSpanForDailyCalc(this.timeSheet.getStart(), outSideStart),
					this.rounding.clone(),
					this.recordedTimeSheet.stream().map(r -> r.clone()).collect(Collectors.toList()),
					this.deductionTimeSheet.stream().map(d -> d.clone()).collect(Collectors.toList()));
			within.trimRecordedAndDeductionToSelfRange();
			
			//計算用深夜時間帯（所定外）の作成
			MidNightTimeSheetForCalc outside = new MidNightTimeSheetForCalc(
					new TimeSpanForDailyCalc(outSideStart, this.timeSheet.getEnd()),
					this.rounding.clone(),
					this.recordedTimeSheet.stream().map(r -> r.clone()).collect(Collectors.toList()),
					this.deductionTimeSheet.stream().map(d -> d.clone()).collect(Collectors.toList()));
			outside.trimRecordedAndDeductionToSelfRange();
			
			//作成した計算用深夜時間帯を所定内、所定外にそれぞれ追加する
			inOut.within.add(within);
			inOut.outside.add(outside);
			return inOut;
		}
		if(outSideStart.lessThanOrEqualTo(this.timeSheet.getStart())) {
			//深夜時間帯を所定外に追加する
			inOut.outside.add(this);
			return inOut;
		}
		return inOut;
	}
}
