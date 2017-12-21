package nts.uk.ctx.at.record.dom.daily.holidayworktime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTimeSheetWork;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationOfOverTimeWork;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalcSetOfHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.primitive.WorkTimeNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.HolidayWorkTimeSheetSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidworktimesheet.FluWorkHolidayTimeSheet;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

/**
 * 休出枠時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class HolidayWorkFrameTimeSheetWORK extends CalculationTimeSheet{
	
	private HolidayWorkFrameTime frameTime;
	
	private boolean treatAsTimeSpentAtWork;
	
	private WorkTimeNo holidayWorkTimeSheetNo; 
	
	private StaturoryAtrOfHolidayWork statutoryAtr;
	
	/**
	 * constructor
	 * @param timeSheet 時間帯(丸め付き)
	 * @param calculationTimeSheet 計算範囲
	 * @param deductionTimeSheets 控除項目の時間帯
	 * @param bonusPayTimeSheet 加給時間帯
	 * @param midNighttimeSheet 
	 * @param frameTime
	 * @param treatAsTimeSpentAtWork
	 * @param holidayWorkTimeSheetNo
	 */
	public HolidayWorkFrameTimeSheetWORK(
			TimeSpanWithRounding timeSheet,
			TimeSpanForCalc calculationTimeSheet,
			List<TimeSheetOfDeductionItem> deductionTimeSheets,
			List<BonusPayTimesheet> bonusPayTimeSheet, 
			List<SpecBonusPayTimesheet> specifiedbonusPayTimeSheet,
			Optional<MidNightTimeSheet> midNighttimeSheet, 
			HolidayWorkFrameTime frameTime,
			boolean treatAsTimeSpentAtWork,
			WorkTimeNo holidayWorkTimeSheetNo) {
		super(timeSheet, calculationTimeSheet, deductionTimeSheets, bonusPayTimeSheet,specifiedbonusPayTimeSheet, midNighttimeSheet);
		this.frameTime = frameTime;
		this.treatAsTimeSpentAtWork = treatAsTimeSpentAtWork;
		this.holidayWorkTimeSheetNo = holidayWorkTimeSheetNo;
	}
	
	
	/**
	 * 残業時間帯時間枠に残業時間を埋める
	 * @param autoCalcSet 時間外の自動計算区分
	 * @return 残業時間枠時間帯クラス
	 */
	public HolidayWorkFrameTime calcOverTimeWorkTime(AutoCalcSetOfHolidayWorkTime autoCalcSet) {
		int holidayWorkTime;
		if(autoCalcSet.getLateNightTime().getCalculationClassification().isCalculateEmbossing()) {
			holidayWorkTime = 0;
		}
		else {
			holidayWorkTime = this.calcTotalTime().valueAsMinutes();
		}
		return  new HolidayWorkFrameTime(this.frameTime.getHolidayFrameNo()
				,this.frameTime.getTransferTime()
				,Finally.of(TimeWithCalculation.sameTime(new AttendanceTime(holidayWorkTime)))
				,this.frameTime.getBeforeApplicationTime());
	}
	
	/**
	 * 休出時間帯の作成と時間計算
	 * @param attendanceTime
	 * @param oneDayRange　１日の計算範囲.１日の範囲
	 * @return
	 */
	public static List<HolidayWorkFrameTimeSheetWORK> getHolidayWorkTimeOfDaily(List<HolidayWorkTimeSheetSet> fixTimeSet ,TimeLeavingWork attendanceLeave,
			OverDayEndCalcSet dayEndSet,WorkTimeCommonSet overDayEndSet ,List<HolidayWorkFrameTimeSheetWORK> holidayTimeWorkItem,
			WorkType beforeDay,WorkType toDay,WorkType afterDay) {
		
		List<HolidayWorkFrameTimeSheetWORK> holidayWorkTimeSheetList = new ArrayList<HolidayWorkFrameTimeSheetWORK>();
		List<HolidayWorkFrameTime> holidayWorkFrameTimeList = new ArrayList<HolidayWorkFrameTime>();
		Optional<TimeSpanForCalc> timeSpan;
		TimeSpanForCalc attendanceLeaveSheet = new TimeSpanForCalc(attendanceLeave.getAttendanceStamp().getStamp().get().getTimeWithDay(),attendanceLeave.getLeaveStamp().getStamp().get().getTimeWithDay());
		for(HolidayWorkTimeSheetSet holidayTimeSheet : fixTimeSet) {
			/*計算範囲の判断*/
			timeSpan = attendanceLeaveSheet.getDuplicatedWith(holidayTimeSheet.getHours().getSpan());
			/*控除時間帯を分割*/
			/*控除時間帯を保持(控除時間帯を分割で取得したListに対してmapを使う)*/
			
			/*遅刻早退処理*/
		//	if(/*遅刻早退どっちの設定を見てもOKなので、どちらかの休出の場合でも計算するを見る*/) {
		//	}
			/*法定区分をセット↓は仮置きです 2017.11.13*/
			HolidayAtr holidayAtr = HolidayAtr.STATUTORY_HOLIDAYS;
			
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(holidayTimeSheet.getFrameNoByHolidayAtr(holidayAtr),Finally.empty(), Finally.empty(),Finally.empty() );
			
			/*休出時間帯の作成*/
			holidayWorkTimeSheetList.add(new HolidayWorkFrameTimeSheetWORK(new TimeSpanWithRounding(timeSpan.get().getStart(),timeSpan.get().getEnd(),Finally.empty()),
																	   timeSpan.get(),
																	   Collections.emptyList(),
																	   Collections.emptyList(),
																	   Collections.emptyList(),
																	   Optional.empty(),
																	   frameTime,
																	   false,
																	   holidayTimeSheet.getWorkTimeNo())
																	   );
		}
		return holidayWorkTimeSheetList;
	}
	
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	
//	/**
//	 * 控除時間を考慮して終了時刻を求める
//	 * @return
//	 */
//	public HolidayWorkFrameTimeSheet collectHolidayWorkFrameTimeSheet(
//			FluWorkHolidayTimeSheet fluWorkHolidayTimeSheet,
//			WorkType workType,
//			DeductionTimeSheet deductionTimeSheet,/*事前処理で作成した控除時間帯で良い？*/
//			TimeSpanForCalc collectCalcRange,/*計算範囲*/
//			AttendanceTime previousElapsedTime/*前回の経過時間*/
//			) {
//		//今回の処理の経過時間
//		AttendanceTime elapsedTime = fluWorkHolidayTimeSheet.getFluidTimeSetting().getElapsedTime();
//		//休出枠の時間を計算する (今回処理する経過時間-前回の経過時間)
//		AttendanceTime holidayWorkFrameTime =  new AttendanceTime(elapsedTime.valueAsMinutes()-previousElapsedTime.valueAsMinutes());
//		//休出枠時間から終了時刻を計算する
//		TimeWithDayAttr endClock = collectCalcRange.getStart().backByMinutes(elapsedTime.valueAsMinutes());
//		//休出枠時間帯　（一時的に作成）
//		TimeSpanForCalc holidayWorkFrameTimeSheet = new TimeSpanForCalc(collectCalcRange.getStart(),endClock);
//		//控除時間帯分ループ
//		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : deductionTimeSheet.getForDeductionTimeZoneList()) {
//			TimeSpanForCalc duplicateTime = holidayWorkFrameTimeSheet.getDuplicatedWith(timeSheetOfDeductionItem.getTimeSheet().getSpan()).orElse(null);
//			if(duplicateTime!=null) {//重複している場合の処理
//				//控除項目の時間帯に法定内区分をセット
//				timeSheetOfDeductionItem = timeSheetOfDeductionItem.reateBreakTimeSheetAsFixed(
//						timeSheetOfDeductionItem.getTimeSheet().getSpan(),
//						timeSheetOfDeductionItem.getGoOutReason(),
//						timeSheetOfDeductionItem.getBreakAtr(),
//						timeSheetOfDeductionItem.getDeductionAtr(),
//						WithinStatutoryAtr.WithinStatutory);
//				//控除時間分、終了時刻を遅くする
//				TimeSpanForCalc collectTimeSheet = this.timeSheet.shiftEndBack(duplicateTime.lengthAsMinutes());
//				TimeSpanWithRounding newTimeSheet = this.timeSheet;
//				newTimeSheet.newTimeSpan(collectTimeSheet);
//			}	
//		}
//		
//		//休出枠NOを判断
//		
//		//控除時間帯に丸め設定を付与
//
//		//加給時間帯を作成
//		
//		//深夜時間帯を作成
//		
//		HolidayWorkFrameTimeSheet holidayWorkFrameTimeSheet =  new HolidayWorkFrameTimeSheet();
//		return holidayWorkFrameTimeSheet;
//	}
//	
	
	
}