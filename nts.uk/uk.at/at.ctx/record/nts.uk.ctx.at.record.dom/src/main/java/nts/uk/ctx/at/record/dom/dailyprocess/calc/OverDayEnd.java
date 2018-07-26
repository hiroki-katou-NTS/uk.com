package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.ot.zerotime.ZeroTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
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
	 * @param beforeWorkType 
	 * @return
	 */
	public static OverDayEnd forOverTime(boolean zeroHStraddCalculateSet,List<OverTimeFrameTimeSheetForCalc> overTimeFrameList,ZeroTime zeroTimeSet, WorkType beforeWorkType) {
		if(!zeroHStraddCalculateSet) return new OverDayEnd(overTimeFrameList, new ArrayList<>());
		List<OverDayEnd> returnValue = new ArrayList<>();
		overTimeFrameList.forEach(tc ->{
			returnValue.add(perTimeSheetForOverTime(tc, beforeWorkType));
		});
		return new OverDayEnd(overTimeFrameList, new ArrayList<>());
	}
	
	public static OverDayEnd perTimeSheetForOverTime(OverTimeFrameTimeSheetForCalc timeSheet, WorkType beforeWorkType){
		//前日分割
		if(decisionStraddleForBeforeDay(timeSheet.getTimeSheet().getTimeSpan())) {
			//前日の0時跨ぎ処理
			return splitFromOverWork(timeSheet,new TimeWithDayAttr(0),beforeWorkType);
		}
		//翌日分割
		if(decisionStraddleForNextDay(timeSheet.getTimeSheet().getTimeSpan())) {
			//翌日の0時跨ぎ処理
			return splitFromOverWork(timeSheet,new TimeWithDayAttr(1440),beforeWorkType);
		}
		return new OverDayEnd(Arrays.asList(timeSheet), new ArrayList<>());
	}
	
	/**
	 * 休出用の0時跨ぎ処理
	 * @return
	 */
	public static OverDayEnd forHolidayWorkTime(boolean zeroHStraddCalculateSet,List<HolidayWorkFrameTimeSheetForCalc> holidayWorkFrameList,ZeroTime zeroTimeSet,WorkType beforeWorkType) {
		List<OverDayEnd> returnValue = new ArrayList<>();
		if(!zeroHStraddCalculateSet) return new OverDayEnd(new ArrayList<>(), holidayWorkFrameList);
		holidayWorkFrameList.forEach(tc -> {
			returnValue.add(perTimeSheetForHolidayWorkTime(tc,beforeWorkType));
		});
		return new OverDayEnd(new ArrayList<>(), holidayWorkFrameList);
	}
	
	public static OverDayEnd perTimeSheetForHolidayWorkTime(HolidayWorkFrameTimeSheetForCalc timeSheet, WorkType beforeWorkType){
		//前日分割
		if(decisionStraddleForBeforeDay(timeSheet.getTimeSheet().getTimeSpan())) {
			//前日の0時跨ぎ処理
			return splitFromHolidayWork(timeSheet,new TimeWithDayAttr(0),beforeWorkType);
			
		}
		//翌日分割
		if(decisionStraddleForNextDay(timeSheet.getTimeSheet().getTimeSpan())) {
			//翌日の0時跨ぎ処理
			return splitFromHolidayWork(timeSheet,new TimeWithDayAttr(1440),beforeWorkType);
		}
		return new OverDayEnd(new ArrayList<>(), Arrays.asList(timeSheet));
	}

	/*-----------------------------------前日系-------------------------------------*/
	/**
	 * 前日0時跨ぎ判断処理
	 * @return
	 */
	public static boolean decisionStraddleForBeforeDay(TimeSpanForCalc timeSpan) {
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
	public static boolean decisionStraddleForNextDay(TimeSpanForCalc timeSpan) {
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
	public static boolean overBaseOclock(TimeSpanForCalc timeSpan,int baseTime) {
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
		private static OverDayEnd splitFromOverWork(OverTimeFrameTimeSheetForCalc overTimeSheet,TimeWithDayAttr baseTime,WorkType beforeWorkType) {
			List<OverTimeFrameTimeSheetForCalc> overTimeFrames = new ArrayList<>();
			List<HolidayWorkFrameTimeSheetForCalc> holidayTimeFrames = new ArrayList<>();
			//当日0時を含んでいない baseTime(当日0時or翌日0時)
			if(!overTimeSheet.getTimeSheet().getTimeSpan().contains(baseTime)) {
				/*残業時間帯から休出時間帯作成*/
				holidayTimeFrames.add(convertHolidayWorkTimeSheet(overTimeSheet));
			}
			//当日0時を含んでいる(残業時間帯と休出時間帯が作成されるパターン)
			//手前が休出時間帯、後ろが残業時間帯になるパティーン
			else {
				/*基準時間で分割*/
				OverTimeFrameTimeSheetForCalc beforeitem = new OverTimeFrameTimeSheetForCalc(
																new TimeZoneRounding(overTimeSheet.getTimeSheet().getStart(), baseTime, overTimeSheet.getTimeSheet().getRounding()),
																new TimeSpanForCalc(overTimeSheet.getTimeSheet().getStart(),baseTime),
																overTimeSheet.recreateDeductionItemBeforeBase(baseTime, true,DeductionAtr.Appropriate),
																overTimeSheet.recreateDeductionItemBeforeBase(baseTime, true,DeductionAtr.Deduction),
																overTimeSheet.recreateBonusPayListBeforeBase(baseTime, true),
																overTimeSheet.recreateSpecifiedBonusPayListBeforeBase(baseTime, true),
																overTimeSheet.recreateMidNightTimeSheetBeforeBase(baseTime, true),
																overTimeSheet.getFrameTime(),
																overTimeSheet.getWithinStatutryAtr(),
																overTimeSheet.isGoEarly(),
																overTimeSheet.getOverTimeWorkSheetNo(),
																overTimeSheet.isAsTreatBindTime(),
																overTimeSheet.getPayOrder(),
																overTimeSheet.getAdjustTime());
				
				HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(10),//0時跨ぎマスタから埋めるべき値を取得する
																		  Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
																		  Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
																		  Finally.of(new AttendanceTime(0)));
				/*開始時間が早い方の時間帯を休日出勤時間帯へ変更*/
				holidayTimeFrames.add(new HolidayWorkFrameTimeSheetForCalc(
										  beforeitem.getTimeSheet(),
										  beforeitem.getTimeSheet().getTimeSpan(),
										  beforeitem.getDeductionTimeSheet(),
										  beforeitem.getRecordedTimeSheet(),
									      beforeitem.getBonusPayTimeSheet(),
										  beforeitem.getSpecBonusPayTimesheet(),
										  beforeitem.getMidNightTimeSheet(),
										  frameTime,
										  false,
										  new EmTimezoneNo(0),
										  //↓残業の法定内区分によって埋める区分を変える
										  Finally.of(StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork)));
				
				
				overTimeFrames.add(new OverTimeFrameTimeSheetForCalc(new TimeZoneRounding(baseTime, overTimeSheet.getTimeSheet().getEnd(), overTimeSheet.getTimeSheet().getRounding()),
																	new TimeSpanForCalc(baseTime,overTimeSheet.getTimeSheet().getEnd()),
																	overTimeSheet.recreateDeductionItemBeforeBase(baseTime, false,DeductionAtr.Appropriate),
																	overTimeSheet.recreateDeductionItemBeforeBase(baseTime, false,DeductionAtr.Deduction),
																	overTimeSheet.recreateBonusPayListBeforeBase(baseTime, false),
																	overTimeSheet.recreateSpecifiedBonusPayListBeforeBase(baseTime, false),
																	overTimeSheet.recreateMidNightTimeSheetBeforeBase(baseTime, false),
																	overTimeSheet.getFrameTime(),
																	overTimeSheet.getWithinStatutryAtr(),
																	overTimeSheet.isGoEarly(),
																	overTimeSheet.getOverTimeWorkSheetNo(),
																	overTimeSheet.isAsTreatBindTime(),
																	overTimeSheet.getPayOrder(),
																	overTimeSheet.getAdjustTime()));
				
			}
			return new OverDayEnd(overTimeFrames, holidayTimeFrames);
		}
		
		
		/**
		 * 残業時間帯へから休日出勤時間帯への変換
		 * @param weekDaySet 平日出勤の0時跨ぎの設定
		 * @param timeSpan 時間帯
		 * @return　休出時間帯
		 */
		private static HolidayWorkFrameTimeSheetForCalc convertHolidayWorkTimeSheet(OverTimeFrameTimeSheetForCalc overTimeSheet) {
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(10), //0時跨ぎマスタから埋めるべき値を取得する
					  												  Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
					  												  Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
					  												  Finally.of(new AttendanceTime(0)));
			
			return new HolidayWorkFrameTimeSheetForCalc(overTimeSheet.getTimeSheet(),
														overTimeSheet.getCalcrange(),
														overTimeSheet.getDeductionTimeSheet(),
														overTimeSheet.getRecordedTimeSheet(),
														overTimeSheet.getBonusPayTimeSheet(),
														overTimeSheet.getSpecBonusPayTimesheet(),
														overTimeSheet.getMidNightTimeSheet(),
														frameTime,
														false,
														new EmTimezoneNo(0),
														//質問後埋めるべきものを埋める
														Finally.of(StaturoryAtrOfHolidayWork.PublicHolidayWork));
		}
		
		/**
		 * 休出の0時またぎ分割処理
		 * @param baseTime　基準時間
		 * @param holidayFrameSet 休日枠の設定
		 * @param holidayWorkTimeSheet 休出時間帯
		 */
		private static OverDayEnd splitFromHolidayWork(HolidayWorkFrameTimeSheetForCalc holidayWorkTimeSheet,TimeWithDayAttr baseTime,WorkType beforeWorkType) {
			List<OverTimeFrameTimeSheetForCalc> overTimeFrames = new ArrayList<>();
			List<HolidayWorkFrameTimeSheetForCalc> holidayTimeFrames = new ArrayList<>();
			//baseTimeを含んでいない
			//全て他の休出・残業時間帯へ変更する
			if(!holidayWorkTimeSheet.getTimeSheet().getTimeSpan().contains(baseTime)) {
				/*休出時間帯から休出時間帯作成*/
				if(beforeWorkType.getDailyWork().isHolidayWork() 
				 ||beforeWorkType.getDailyWork().isOneDayHoliday()) {
					/*休出時間帯へ変換*/
					holidayTimeFrames.add(convertDifferenceHolidayWork(holidayWorkTimeSheet));
				}
				else {
					/*残業時間帯へ変換*/
					overTimeFrames.add(convertOverTimeWork(holidayWorkTimeSheet));
				}
				/*残業時間帯クリア*/
			}
			//休出時間帯が、一部他の休出残業時間帯へ変化するパティーン
			//手前の時間帯を変える
			else {
				/*当日baseTimeで分割*/
				HolidayWorkFrameTimeSheetForCalc beforeitem = new HolidayWorkFrameTimeSheetForCalc(new TimeZoneRounding(holidayWorkTimeSheet.getTimeSheet().getStart(), baseTime, holidayWorkTimeSheet.getTimeSheet().getRounding()),
																								   new TimeSpanForCalc(holidayWorkTimeSheet.getCalcrange().getStart(),baseTime),
																								   holidayWorkTimeSheet.recreateDeductionItemBeforeBase(baseTime, true, DeductionAtr.Appropriate),
																								   holidayWorkTimeSheet.recreateDeductionItemBeforeBase(baseTime, true, DeductionAtr.Deduction),
																								   holidayWorkTimeSheet.recreateBonusPayListBeforeBase(baseTime, true),
																								   holidayWorkTimeSheet.recreateSpecifiedBonusPayListBeforeBase(baseTime, true),
																								   holidayWorkTimeSheet.recreateMidNightTimeSheetBeforeBase(baseTime, true),
																								   holidayWorkTimeSheet.getFrameTime(),
																								   holidayWorkTimeSheet.isTreatAsTimeSpentAtWork(),
																								   holidayWorkTimeSheet.getHolidayWorkTimeSheetNo(),
																								   //↓は質問後変える
																								   Finally.of(StaturoryAtrOfHolidayWork.PublicHolidayWork)
																								   );
				if(beforeWorkType.getDailyWork().isHolidayWork() 
				 ||beforeWorkType.getDailyWork().isOneDayHoliday()) {
					/*休出時間帯へ変換*/
					holidayTimeFrames.add(convertDifferenceHolidayWork(beforeitem));
				}
				else {
					/*残業時間帯へ変換*/
					overTimeFrames.add(convertOverTimeWork(beforeitem));
				}
				
				
				HolidayWorkFrameTimeSheetForCalc afterList = new HolidayWorkFrameTimeSheetForCalc(new TimeZoneRounding(baseTime, holidayWorkTimeSheet.getTimeSheet().getEnd(), holidayWorkTimeSheet.getTimeSheet().getRounding()),
						   																		  new TimeSpanForCalc(baseTime,holidayWorkTimeSheet.getCalcrange().getEnd()),
						   																		  holidayWorkTimeSheet.recreateDeductionItemBeforeBase(baseTime, false, DeductionAtr.Appropriate),
						   																		  holidayWorkTimeSheet.recreateDeductionItemBeforeBase(baseTime, false, DeductionAtr.Deduction),
						   																		  holidayWorkTimeSheet.recreateBonusPayListBeforeBase(baseTime, false),
						   																		  holidayWorkTimeSheet.recreateSpecifiedBonusPayListBeforeBase(baseTime, false),
						   																		  holidayWorkTimeSheet.recreateMidNightTimeSheetBeforeBase(baseTime, false),
						   																		  holidayWorkTimeSheet.getFrameTime(),
						   																		  holidayWorkTimeSheet.isTreatAsTimeSpentAtWork(),
						   																		  holidayWorkTimeSheet.getHolidayWorkTimeSheetNo(),
																								   //↓は質問後変える
						   																		  Finally.of(StaturoryAtrOfHolidayWork.PublicHolidayWork)
						   																		  );
				/*開始時間が早い方の時間帯を休日出勤時間帯へ変更*/
				holidayTimeFrames.add(afterList);

			}
			return new OverDayEnd(overTimeFrames, holidayTimeFrames);
		}
		
		
		
		/**
		 * 休日出勤から残業時間帯への変換
		 * @param holidayWorkTimeSheet 変換する休出時間帯
		 * @return　残業時間帯
		 */
		public static OverTimeFrameTimeSheetForCalc convertOverTimeWork(HolidayWorkFrameTimeSheetForCalc holidayWorkTimeSheet) {
			OverTimeFrameTime frameTime = new OverTimeFrameTime(new OverTimeFrameNo(10),//0時跨ぎマスタの設定を基にここを埋めるように変更する
																TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)),
																TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)),
																new AttendanceTime(0),
																new AttendanceTime(0));
			
			return new OverTimeFrameTimeSheetForCalc(holidayWorkTimeSheet.getTimeSheet()
													,holidayWorkTimeSheet.getCalcrange()
												 	,holidayWorkTimeSheet.getDeductionTimeSheet()
												 	,holidayWorkTimeSheet.getRecordedTimeSheet()
												 	,holidayWorkTimeSheet.getBonusPayTimeSheet()
												 	,holidayWorkTimeSheet.getSpecBonusPayTimesheet()
												 	,holidayWorkTimeSheet.getMidNightTimeSheet()
												 	,frameTime
												 	,StatutoryAtr.Excess
												 	,false
												 	,new EmTimezoneNo(1)
												 	,false
												 	,Optional.of(new SettlementOrder(0))
												 	,Optional.of(new AttendanceTime(0)));
		}
		
		/**
		 * 休日出勤から休日出勤への変換
		 * @param holidayWorkTimeSheet 変換する休出時間帯
		 * @return　休出時間帯
		 */
		public static HolidayWorkFrameTimeSheetForCalc convertDifferenceHolidayWork(HolidayWorkFrameTimeSheetForCalc holidayWorkTimeSheet) {
			
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(10),//0時跨ぎマスタの設定を基にここを埋めるように変更する
					  												  Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
					  												  Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
					  												  Finally.of(new AttendanceTime(0)));
			
			return new HolidayWorkFrameTimeSheetForCalc(holidayWorkTimeSheet.getTimeSheet(),
													    holidayWorkTimeSheet.getCalcrange(),
													    holidayWorkTimeSheet.getDeductionTimeSheet(),
													    holidayWorkTimeSheet.getRecordedTimeSheet(),
													    holidayWorkTimeSheet.getBonusPayTimeSheet(),
													    holidayWorkTimeSheet.getSpecBonusPayTimesheet(),
													    holidayWorkTimeSheet.getMidNightTimeSheet(),
													    frameTime,
													    false,
													    new EmTimezoneNo(0),
													    //↓は
													    Finally.of(StaturoryAtrOfHolidayWork.PublicHolidayWork));
		}
	}



	

