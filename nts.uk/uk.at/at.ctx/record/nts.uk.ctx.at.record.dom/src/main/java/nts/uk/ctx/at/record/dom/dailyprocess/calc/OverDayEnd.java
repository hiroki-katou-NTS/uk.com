package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 日付をまたいだ時の処理
 * @author keisuke_hoshina
 *
 */
@Getter
public class OverDayEnd {
	
	private List<OverTimeFrameTimeSheetForCalc> overTimeList; 
	
	private List<HolidayWorkFrameTimeSheetForCalc> holList; 

	/**
	 * Constructor 
	 */
	public OverDayEnd(List<OverTimeFrameTimeSheetForCalc> overTimeList, List<HolidayWorkFrameTimeSheetForCalc> holList) {
		super();
		this.overTimeList = overTimeList;
		this.holList = holList;
	}
	
	
	
	/**
	 * 残業用の0時跨ぎ処理
	 * @return
	 */
	public static OverDayEnd forOverTime(boolean zeroHStraddCalculateSet,List<OverTimeFrameTimeSheetForCalc> overTimeFrameList) {
		if(!zeroHStraddCalculateSet) return new OverDayEnd(overTimeFrameList, new ArrayList<>());
//		overTimeFrameList.forEach(tc ->{
//			perTimeSheetForOverTime(tc);
//		});
		return new OverDayEnd(overTimeFrameList, new ArrayList<>());
	}
	
//	public static OverDayEnd perTimeSheetForOverTime(OverTimeFrameTimeSheetForCalc timeSheet){
////		//前日分割
////		if(decisionStraddleForBeforeDay) {
////			//前日の0時跨ぎ処理
////			
////		}
////		//翌日分割
////		if(decisionStraddleForNextDay) {
////			
////		}
//	}
	
	/**
	 * 休出用の0時跨ぎ処理
	 * @return
	 */
	public static OverDayEnd forHolidayWorkTime(boolean zeroHStraddCalculateSet,List<HolidayWorkFrameTimeSheetForCalc> holidayWorkFrameList) {
		if(!zeroHStraddCalculateSet) return new OverDayEnd(new ArrayList<>(), holidayWorkFrameList);
//		holidayWorkFrameList.forEach(tc -> {
//			perTimeSheetForHolidayWorkTime(tc);
//		});
		return new OverDayEnd(new ArrayList<>(), holidayWorkFrameList);
	}
//	
//	public static OverDayEnd perTimeSheetForHolidayWorkTime(HolidayWorkFrameTimeSheetForCalc timeSheet){
////		//前日分割
////		if(decisionStraddleForBeforeDay) {
////			//前日の0時跨ぎ処理
////			
////		}
////		//翌日分割
////		if(decisionStraddleForNextDay) {
////			
////		}
//	}

	/*-----------------------------------前日系-------------------------------------*/
	/**
	 * 前日0時跨ぎ判断処理
	 * @return
	 */
	public boolean decisionStraddleForBeforeDay(TimeSpanForCalc timeSpan) {
		//当日0:00よりも過去かどうか
		if(!overBaseOclock(timeSpan,0)) return false;
		//前日の実績が存在するか
		
		//前日の勤種から跨ぎ計算するか判定
		
		return true;
	}
	/*-----------------------------------前日系-------------------------------------*/
	/*-----------------------------------翌日系-------------------------------------*/
	/**
	 * 翌日0時跨ぎ判断処理
	 * @param timeSpan
	 * @return 
	 */
	public boolean decisionStraddleForNextDay(TimeSpanForCalc timeSpan) {
		//翌日24:00を跨いでいるか
		if(!overBaseOclock(timeSpan,1440)) return false;
		
		//翌日の実績が存在しているか
		
		//翌日の勤務種類から跨ぎ計算するか判定
		
		
		return true;
	}
	/*-----------------------------------翌日系-------------------------------------*/
	/*-------------------------------前日・翌日両対応---------------------------------*/
	/**
	 * 前日・翌日の0時跨ぎ判定
	 * @param timeSpan
	 * @return　当日の0時を跨いでいる
	 */
	public boolean overBaseOclock(TimeSpanForCalc timeSpan,int baseTime) {
		return timeSpan.getStart().lessThan(baseTime) && timeSpan.getEnd().lessThan(baseTime); 
	}
	/*-------------------------------前日・翌日両対応---------------------------------*/
	

//	
//	/**
//	 * 該当日の計算するしない設定を取得する
//	 * @param beforeDay 前の日の勤務種類
//	 * @param afterDay　　後の日の勤務種類
//	 * @param overDaySet　0時跨ぎ計算設定
//	 * @return
//	 */
//	private UseAtr getIsUse(DailyWork beforeDay,DailyWork afterDay, OverDayEndCalcSet overDaySet) {
//		
////		if(afterDay.) {
////			/*休日*/
////			switch() {
////			case:/*法定内休日*/
////			case:/*法定外休日*/
////			case:/*祝日*/
////			}
////		}
////		else {
////			/*平日*/
////		}
//		
//		/*↓一時的措置*/
//		return UseAtr.NOT_USE;
//	}
	
	
		/**
		 * 0時またぎ分割処理
		 */
		private OverDayEnd daySplit(OverTimeFrameTimeSheetForCalc item,TimeWithDayAttr baseTime,WorkType notTargetDay) {
			List<OverTimeFrameTimeSheetForCalc> overTimeFrames = new ArrayList<>();
			List<HolidayWorkFrameTimeSheetForCalc> holidayTimeFrames = new ArrayList<>();
			//当日0時を含んでいない baseTime(当日0時or翌日0時)
			if(!item.getTimeSheet().getTimeSpan().contains(baseTime)) {
				/*残業時間帯から休出時間帯作成*/
				//holidayTimeFrames.add(convertHolidayWorkTimeSheet(notTargetDay,item.calcrange,item,notTargetDay));
			}
			//当日0時を含んでいる(残業時間帯と休出時間帯が作成されるパターン)
			else {
//				/*基準時間で分割*/
//				OverTimeFrameTimeSheetForCalc beforeitem = new OverTimeFrameTimeSheetForCalc(
//																item.getTimeSheet(),
//																new TimeSpanForCalc(item.getTimeSheet().getStart(),baseTime),
//																item.recreateDeductionItemBeforeBase(baseTime, true,DeductionAtr.Appropriate),
//																item.recreateDeductionItemBeforeBase(baseTime, true,DeductionAtr.Deduction),
//																item.recreateBonusPayListBeforeBase(baseTime, true),
//																item.recreateSpecifiedBonusPayListBeforeBase(baseTime, true),
//																item.recreateMidNightTimeSheetBeforeBase(baseTime, true),
//																item.getFrameTime(),
//																item.getWithinStatutryAtr(),
//																item.isGoEarly(),
//																item.getOverTimeWorkSheetNo(),
//																item.isAsTreatBindTime(),
//																item.getPayOrder(),
//																item.getAdjustTime());
//																								//0時跨ぎマスタと残業枠Noによって埋めるNoを変える
//				HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(item.getFrameNo().v()),
//																		  Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
//																		  Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
//																		  Finally.of(new AttendanceTime(0)));
//				/*開始時間が早い方の時間帯を休日出勤時間帯へ変更*/
//				HolidayWorkFrameTimeSheetForCalc holidaybeforeitem = new HolidayWorkFrameTimeSheetForCalc(
//																beforeitem.getTimeSheet(),
//																beforeitem.getTimeSheet().getTimeSpan(),
//																beforeitem.getDeductionTimeSheet(),
//																beforeitem.getBonusPayTimeSheet(),
//																beforeitem.getSpecBonusPayTimesheet(),
//																beforeitem.getMidNightTimeSheet(),
//																frameTime,
//																false,
//																new EmTimezoneNo(0),
//																//↓残業の法定内区分によって埋める区分を変える
//																Finally.of(StaturoryAtrOfHolidayWork));
//				
//				
//				OverTimeFrameTimeSheetForCalc afterList = new OverTimeFrameTimeSheetForCalc(
//																item.getTimeSheet(),
//																new TimeSpanForCalc(baseTime,item.getTimeSheet().getEnd()),
//																item.recreateDeductionItemBeforeBase(baseTime, false,DeductionAtr.Appropriate),
//																item.recreateDeductionItemBeforeBase(baseTime, false,DeductionAtr.Deduction),
//																item.recreateBonusPayListBeforeBase(baseTime, false),
//																item.recreateSpecifiedBonusPayListBeforeBase(baseTime, false),
//																item.recreateMidNightTimeSheetBeforeBase(baseTime, false),
//																item.getFrameTime(),
//																item.getWithinStatutryAtr(),
//																item.isGoEarly(),
//																item.getOverTimeWorkSheetNo(),
//																item.isAsTreatBindTime(),
//																item.getPayOrder(),
//																item.getAdjustTime());
				
			}
			return new OverDayEnd(overTimeFrames, holidayTimeFrames);
		}
		
		
//		/**
//		 * 残業時間帯へから休日出勤時間帯への変換
//		 * @param weekDaySet 平日出勤の0時跨ぎの設定
//		 * @param timeSpan 時間帯
//		 * @return　休出時間帯
//		 */
//		private HolidayWorkFrameTimeSheet convertHolidayWorkTimeSheet(OverDayEndSetOfWeekDayHoliday weekDaySet,TimeSpanForCalc timeSpan,OverTimeFrameTimeSheet overTimeSheet
//																	  ,WorkType notTargetDay) {
//			HolidayWorkFrameNo no = weekDaySet.getHolidayWorkNo(notTargetDay.getWorkTypeSetList().get(0).getHolidayAtr())/*0時跨ぎ計算設定に対して休日区分を投げ、休出NOをここで取得する*/;
//			TimeWithCalculation time = TimeWithCalculation.sameTime(new AttendanceTime(0));
//			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(1),
//					  							Finally.of(TimeWithCalculation.sameTime(new AttendanceTime(0))),
//					  							Finally.of(TimeWithCalculation.sameTime(new AttendanceTime(0))),
//					  							Finally.of(new AttendanceTime(0)));
//			//return new HolidayWorkFrameTimeSheet(timeSpan , HolidayWorkFrameTime(no,time,time,new AttendanceTime(0)),false);
//			return new HolidayWorkFrameTimeSheet(
//					overTimeSheet.getTimeSheet(),
//					overTimeSheet.getCalcrange(),
//					overTimeSheet.getDeductionTimeSheet(),
//					overTimeSheet.getBonusPayTimeSheet(),
//					overTimeSheet.getSpecBonusPayTimesheet(),
//					overTimeSheet.getMidNightTimeSheet(),
//					frameTime,
//					false,
//					new WorkTimeNo(no.v().intValue()));
//		}
//	}
		
//		/**
//		 * 休出の0時またぎ分割処理
//		 * @param baseTime　基準時間
//		 * @param holidayFrameSet 休日枠の設定
//		 * @param holidayWorkTimeSheet 休出時間帯
//		 */
//		private void daySplit(HolidayWorkFrameTimeSheetForCalc holidayWorkTimeSheet,TimeWithDayAttr baseTime) {
//			//baseTimeを含んでいない
//			if(!holidayWorkTimeSheet.calcrange.contains(baseTime)) {
//				/*休出時間帯から休出時間帯作成*/
//				if()) {
//					/*休出時間帯へ変換*/
//					holList.add(convertDifferenceHolidayWork(holidayholidayFrameSet));
//				}
//				else {
//					/*残業時間帯へ変換*/
//					dedList.add(convertOverTimeWork(holidayWorkTimeSheet,holidayFrameSet.getTransferFrameNoOfOverWork()));
//				}
//				/*残業時間帯クリア*/
//			}
//			//baseTimeを含んでいる
//			else {
//				/*当日baseTimeで分割*/
//				HolidayWorkFrameTimeSheet beforeitem = new HolidayWorkFrameTimeSheet(
//																holidayWorkTimeSheet.timeSheet,
//																new TimeSpanForCalc(holidayWorkTimeSheet.calculationTimeSheet.getStart(),baseTime),
//																holidayWorkTimeSheet.recreateDeductionItemBeforeBase(baseTime, true),
//																holidayWorkTimeSheet.recreateBonusPayListBeforeBase(baseTime, true),
//																holidayWorkTimeSheet.recreateMidNightTimeSheetBeforeBase(baseTime, true),
//																holidayWorkTimeSheet.getFrameTime(),
//																holidayWorkTimeSheet.isTreatAsTimeSpentAtWork()
//																);
//				if(!notToDay.getWorkTypeSetList().get(0).getDigestPublicHd().equals(null)
//					&&!notToDay.getWorkTypeSetList().get(0).getDigestPublicHd().equals(null)) {
//					/*休出時間帯へ変換*/
//					holList.add(convertDifferenceHolidayWork(beforeitem));
//				}
//				else {
//					/*残業時間帯へ変換*/
//					dedList.add(convertOverTimeWork(beforeitem));
//				}
//				
//				
//				HolidayWorkFrameTimeSheet afterList = new HolidayWorkFrameTimeSheet(
//																holidayWorkTimeSheet.timeSheet,
//																new TimeSpanForCalc(baseTime,holidayWorkTimeSheet.getCalculationTimeSheet().getEnd()),
//																holidayWorkTimeSheet.recreateDeductionItemBeforeBase(baseTime, false),
//																holidayWorkTimeSheet.recreateBonusPayListBeforeBase(baseTime, false),
//																holidayWorkTimeSheet.recreateMidNightTimeSheetBeforeBase(baseTime, false),
//																holidayWorkTimeSheet.getFrameTime(),
//																holidayWorkTimeSheet.isTreatAsTimeSpentAtWork(),
//																holidayWorkTimeSheet.);
//				/*開始時間が早い方の時間帯を休日出勤時間帯へ変更*/
//				holList.add(afterList);
//
//			}
//		}
//		
//		/**
//		 * 休日出勤から残業時間帯への変換
//		 * @param holidayWorkTimeSheet
//		 * @return　残業時間帯
//		 */
//		public OverTimeFrameTimeSheet convertOverTimeWork(HolidayWorkFrameTimeSheet holidayWorkTimeSheet,OverTimeFrameNo frameNo) {
//			OverTimeFrameTime frameTime = new OverTimeFrameTime(frameNo,
//																	TimeWithCalculation.sameTime(new AttendanceTime(0)),
//																	TimeWithCalculation.sameTime(new AttendanceTime(0)),
//																	new AttendanceTime(0));
//			return new OverTimeFrameTimeSheet(holidayWorkTimeSheet.getTimeSheet()
//												 ,holidayWorkTimeSheet.calcrange
//												 ,holidayWorkTimeSheet.getDeductionTimeSheet()
//												 ,holidayWorkTimeSheet.getBonusPayTimeSheet()
//												 ,holidayWorkTimeSheet.getSpecBonusPayTimesheet()
//												 ,holidayWorkTimeSheet.getMidNightTimeSheet()
//												 ,new WorkTimeNo(0)
//												 ,frameTime
//												 ,false
//												 ,StatutoryAtr.Excess);
//		}
//		
//		/**
//		 * 休日出勤から休日出勤への変換
//		 * @param holidayAtr
//		 * @param holidayWorkTimeSheet
//		 * @return　休出時間帯
//		 */
//		public HolidayWorkFrameTimeSheet convertDifferenceHolidayWork(HolidayAtr holidayAtr, HolidayWorkFrameTimeSheet holidayWorkTimeSheet,OverDayEndSetOfHolidayHoliday endSet) {//, /*休日出勤の0時跨ぎ設定*/) {
//			
//			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(endSet.getFrameNoByHolidayAtr(holidayAtr),
//					  Finally.of(TimeWithCalculation.sameTime(new AttendanceTime(0))),
//					  Finally.of(TimeWithCalculation.sameTime(new AttendanceTime(0))),
//					  Finally.of(new AttendanceTime(0)));
//			
//			return new HolidayWorkFrameTimeSheet(holidayWorkTimeSheet.getTimeSheet(),holidayWorkTimeSheet.calcrange,holidayWorkTimeSheet.getDeductionTimeSheet(),
//												 holidayWorkTimeSheet.getBonusPayTimeSheet(),holidayWorkTimeSheet.getSpecBonusPayTimesheet(),
//												 holidayWorkTimeSheet.getMidNightTimeSheet(),frameTime,false,new WorkTimeNo(0));
//		}
//	}

//
//
//	
}
