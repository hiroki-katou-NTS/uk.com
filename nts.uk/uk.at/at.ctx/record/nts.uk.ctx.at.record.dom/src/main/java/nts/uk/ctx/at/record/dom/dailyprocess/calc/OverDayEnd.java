package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.ot.zerotime.WeekdayHoliday;
import nts.uk.ctx.at.shared.dom.ot.zerotime.ZeroTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 日付をまたいだ時の処理
 * @author keisuke_hoshina
 *
 */
@Getter
public class OverDayEnd {
	
	//日跨ぎ処理後に存在している残業時間帯
	private List<OverTimeFrameTimeSheetForCalc> overTimeList; 
	
	//日跨ぎ処理後に存在している休出時間帯
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
	 * @param zeroTime 
	 * @return
	 */
	public static OverDayEnd forOverTime(boolean zeroHStraddCalculateSet,List<OverTimeFrameTimeSheetForCalc> overTimeFrameList, WorkType beforeWorkType,WorkType nowWorkType,WorkType afterWorkType,
										 Optional<WorkInformation> beforeInfo, Optional<WorkInformation> afterInfo, ZeroTime zeroTime) {
		//会社共通のマスタ参照して跨ぎ計算判定
		if(zeroTime.getCalcFromZeroTime() == 0) return new OverDayEnd(overTimeFrameList, new ArrayList<>());
		//就業時間帯を見て跨ぎ計算判定
		if(!zeroHStraddCalculateSet) return new OverDayEnd(overTimeFrameList, new ArrayList<>());
		List<OverDayEnd> returnValue = new ArrayList<>();
		overTimeFrameList.forEach(tc ->{
			returnValue.add(perTimeSheetForOverTime(tc, beforeWorkType, nowWorkType, afterWorkType,beforeInfo,afterInfo,zeroTime));
		});
		List<OverTimeFrameTimeSheetForCalc> returnOverList = returnValue.stream().flatMap(tc -> tc.getOverTimeList().stream()).collect(Collectors.toList());
		List<HolidayWorkFrameTimeSheetForCalc> returnHolWorkList = returnValue.stream().flatMap(tc -> tc.getHolList().stream()).collect(Collectors.toList());
		return new OverDayEnd(returnOverList, returnHolWorkList);
	}
	
	public static OverDayEnd perTimeSheetForOverTime(OverTimeFrameTimeSheetForCalc timeSheet, WorkType beforeWorkType, WorkType toDayWorkType, WorkType afterWorkType, 
													 Optional<WorkInformation> beforeInfo, Optional<WorkInformation> afterInfo, ZeroTime zeroTime){
		//前日分割
		if(decisionStraddleForBeforeDay(timeSheet.getTimeSheet().getTimeSpan(),beforeInfo,zeroTime, beforeWorkType, toDayWorkType)) {
			//前日の0時跨ぎ処理
			return splitFromOverWork(timeSheet,new TimeWithDayAttr(0),beforeWorkType,true,zeroTime);
		}
		//翌日分割
		if(decisionStraddleForNextDay(timeSheet.getTimeSheet().getTimeSpan(),afterInfo,zeroTime, toDayWorkType, afterWorkType)) {
			//翌日の0時跨ぎ処理
			return splitFromOverWork(timeSheet,new TimeWithDayAttr(1440),afterWorkType,false,zeroTime);
		}
		return new OverDayEnd(Arrays.asList(timeSheet), new ArrayList<>());
	}
	
	/**
	 * 休出用の0時跨ぎ処理
	 * @param zeroTime 
	 * @return
	 */
	public static OverDayEnd forHolidayWorkTime(boolean zeroHStraddCalculateSet,List<HolidayWorkFrameTimeSheetForCalc> holidayWorkFrameList,WorkType beforeWorkType, WorkType nowWorkType ,WorkType afterWorkType,
												Optional<WorkInformation> beforeInfo, Optional<WorkInformation> afterInfo, ZeroTime zeroTime) {
		List<OverDayEnd> returnValue = new ArrayList<>();
		if(!zeroHStraddCalculateSet) return new OverDayEnd(new ArrayList<>(), holidayWorkFrameList);
		holidayWorkFrameList.forEach(tc -> {
			returnValue.add(perTimeSheetForHolidayWorkTime(tc,beforeWorkType,nowWorkType,afterWorkType,beforeInfo,afterInfo,zeroTime));
		});
		List<OverTimeFrameTimeSheetForCalc> returnOverList = returnValue.stream().flatMap(tc -> tc.getOverTimeList().stream()).collect(Collectors.toList());
		List<HolidayWorkFrameTimeSheetForCalc> returnHolWorkList = returnValue.stream().flatMap(tc -> tc.getHolList().stream()).collect(Collectors.toList());
		return new OverDayEnd(returnOverList, returnHolWorkList);
	}
	
	public static OverDayEnd perTimeSheetForHolidayWorkTime(HolidayWorkFrameTimeSheetForCalc timeSheet, WorkType beforeWorkType, WorkType toDayWorkType,
															WorkType afterWorkType,
															Optional<WorkInformation> beforeInfo, Optional<WorkInformation> afterInfo, ZeroTime zeroTime){
		//前日分割
		if(decisionStraddleForBeforeDay(timeSheet.getTimeSheet().getTimeSpan(),beforeInfo,zeroTime, beforeWorkType, toDayWorkType)) {
			//前日の0時跨ぎ処理
			return splitFromHolidayWork(timeSheet,new TimeWithDayAttr(0),beforeWorkType,true,zeroTime);
		}
		//翌日分割
		if(decisionStraddleForNextDay(timeSheet.getTimeSheet().getTimeSpan(),afterInfo,zeroTime, toDayWorkType, afterWorkType)) {
			//翌日の0時跨ぎ処理
			return splitFromHolidayWork(timeSheet,new TimeWithDayAttr(1440),afterWorkType,false,zeroTime);
		}
		return new OverDayEnd(new ArrayList<>(), Arrays.asList(timeSheet));
	}

	/*-----------------------------------前日系-------------------------------------*/
	/**
	 * 前日0時跨ぎ判断処理
	 * @param zeroTime 
	 * @return
	 */
	public static boolean decisionStraddleForBeforeDay(TimeSpanForCalc timeSpan,Optional<WorkInformation> beforeInfo, ZeroTime zeroTime,
													   WorkType beforeWorkType, WorkType nowWorkType) {
		//当日0:00よりも過去かどうか
		if(overBaseOclock(timeSpan,0)) return false;
		//前日の実績が存在するか
		if(!beforeInfo.isPresent()) return false;
		//前日の勤種から跨ぎ計算するか判定 マスタ必須
		if(!zeroTime.isCalcOneOverYesterDayToDay(beforeWorkType, nowWorkType)) return false;
		return true;
	}
	
	/**
	 * 当日0時跨ぎ判定
	 * @param timeSpan
	 * @return　当日の0時を跨いでいる
	 */
	public static boolean overBaseOclock(TimeSpanForCalc timeSpan,int baseTime) {
		return timeSpan.getStart().greaterThan(baseTime) && timeSpan.getEnd().greaterThan(baseTime); 
	}
	/*-----------------------------------前日系-------------------------------------*/
	/*-----------------------------------翌日系-------------------------------------*/
	/**
	 * 翌日0時跨ぎ判断処理
	 * @param timeSpan
	 * @param zeroTime 
	 * @return 
	 */
	public static boolean decisionStraddleForNextDay(TimeSpanForCalc timeSpan,Optional<WorkInformation> afterInfo, ZeroTime zeroTime,
													 WorkType nowWorkType, WorkType afterWorkType) {
		//翌日0:00より手前に存在するか
		if(lessBaseOclock(timeSpan,1440)) return false;
		//翌日の実績が存在しているか
		if(!afterInfo.isPresent()) return false;
		//翌日の勤務種類から跨ぎ計算するか判定　マスタ必須
		if(!zeroTime.isCalcOneOverToDayTomorrow(afterWorkType, nowWorkType)) return false;		
		return true;
	}
	/**
	 * 前日・翌日の0時跨ぎ判定
	 * @param timeSpan
	 * @return　当日の0時を跨いでいる
	 */
	public static boolean lessBaseOclock(TimeSpanForCalc timeSpan,int baseTime) {
		return timeSpan.getStart().lessThan(baseTime) && timeSpan.getEnd().lessThan(baseTime); 
	}
	/*-----------------------------------翌日系-------------------------------------*/
	/*-------------------------------前日・翌日両対応---------------------------------*/

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
		 * 0時またぎ分割処理(当日平日)
		 * @param isBefore 前日～当日の跨ぎである
		 * @param zeroTime 
		 */
		private static OverDayEnd splitFromOverWork(OverTimeFrameTimeSheetForCalc overTimeSheet,TimeWithDayAttr baseTime,WorkType notTargetDayWorkType,boolean isBefore, 
													ZeroTime zeroTime) {
			List<OverTimeFrameTimeSheetForCalc> overTimeFrames = new ArrayList<>();
			List<HolidayWorkFrameTimeSheetForCalc> holidayTimeFrames = new ArrayList<>();
			Optional<HolidayAtr> atr = notTargetDayWorkType.getHolidayAtr();
			//当日0時を含んでいない baseTime(当日0時or翌日0時)
			if(!overTimeSheet.getTimeSheet().getTimeSpan().contains(baseTime)) {
				val frameNo = zeroTime.getWeekdayHoliday().stream().filter(tc -> tc.getOverworkFrameNo().intValue() == overTimeSheet.getFrameTime().getOverWorkFrameNo().v().intValue()).findFirst();
				if(frameNo.get().useFrameNo(atr.get()) == 0) {
					//振替しない
					overTimeFrames.add(overTimeSheet);
				}
				else {
					/*残業時間帯から休出時間帯作成*/
					holidayTimeFrames.add(convertHolidayWorkTimeSheet(overTimeSheet,atr,frameNo));
				}

			}
			//当日0時を含んでいる(残業時間帯と休出時間帯が作成されるパターン)
			else {
				val frameNo = zeroTime.getWeekdayHoliday().stream().filter(tc -> tc.getOverworkFrameNo().intValue() == overTimeSheet.getFrameTime().getOverWorkFrameNo().v().intValue()).findFirst();
				if(frameNo.get().useFrameNo(atr.get()) == 0) {
					//振替ない
					overTimeFrames.add(overTimeSheet);
				}
				else {
					//手前が休出時間帯、後ろが残業時間帯になるパティーン(前日～当日の跨ぎ)
					if(isBefore) {
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
					
					
						HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(frameNo.get().useFrameNo(atr.get())),//0時跨ぎマスタから埋めるべき値を取得する
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
											  Finally.of(StaturoryAtrOfHolidayWork.deicisionAtrByHolidayAtr(atr.get()))));
					
					
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
				//手前が残業時間帯、後ろが休出時間帯になるパティーン(当日～翌日の跨ぎ)
				else {
					//手前(残業)はそのまま残業リストへ
					overTimeFrames.add(new OverTimeFrameTimeSheetForCalc(new TimeZoneRounding(overTimeSheet.getTimeSheet().getStart(),baseTime, overTimeSheet.getTimeSheet().getRounding()),
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
																		 overTimeSheet.getAdjustTime()));
					
						//翌日０時以降の残業時間帯を一度作成
						OverTimeFrameTimeSheetForCalc afteritem = new OverTimeFrameTimeSheetForCalc(new TimeZoneRounding(baseTime,overTimeSheet.getTimeSheet().getEnd(),overTimeSheet.getTimeSheet().getRounding()),
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
																								 overTimeSheet.getAdjustTime());

						HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(frameNo.get().useFrameNo(atr.get())),//0時跨ぎマスタから埋めるべき値を取得する
																  Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
																  Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
																  Finally.of(new AttendanceTime(0)));
						/*0時～の残業時間帯を休日出勤時間帯へ変更*/
						holidayTimeFrames.add(new HolidayWorkFrameTimeSheetForCalc(afteritem.getTimeSheet(),
								  											   afteritem.getTimeSheet().getTimeSpan(),
								  											   afteritem.getDeductionTimeSheet(),
								  											   afteritem.getRecordedTimeSheet(),
								  											   afteritem.getBonusPayTimeSheet(),
								  											   afteritem.getSpecBonusPayTimesheet(),
								  											   afteritem.getMidNightTimeSheet(),
								  											   frameTime,
								  											   false,
								  											   new EmTimezoneNo(0),
								  											   Finally.of(StaturoryAtrOfHolidayWork.deicisionAtrByHolidayAtr(atr.get()))));
					}
				}
			}
			return new OverDayEnd(overTimeFrames, holidayTimeFrames);
		}
		
		
		/**
		 * 残業時間帯へから休日出勤時間帯への変換
		 * @param atr 
		 * @param frameNo 
		 * @param weekDaySet 平日出勤の0時跨ぎの設定
		 * @param timeSpan 時間帯
		 * @return　休出時間帯
		 */
		private static HolidayWorkFrameTimeSheetForCalc convertHolidayWorkTimeSheet(OverTimeFrameTimeSheetForCalc overTimeSheet, Optional<HolidayAtr> atr, Optional<WeekdayHoliday> frameNo) {
			
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(frameNo.get().useFrameNo(atr.get())), //0時跨ぎマスタから埋めるべき値を取得する
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
														Finally.of(StaturoryAtrOfHolidayWork.deicisionAtrByHolidayAtr(atr.get())));
		}
		
		/**
		 * 休出の0時跨ぎ処理
		 * @param baseTime　基準時間
		 * @param isBefore 前日～当日の0時跨ぎである
		 */
		private static OverDayEnd splitFromHolidayWork(HolidayWorkFrameTimeSheetForCalc holidayWorkTimeSheet,TimeWithDayAttr baseTime,
													   WorkType notTargetDayWorkType,boolean isBefore,ZeroTime zeroTime) {
			List<OverTimeFrameTimeSheetForCalc> overTimeFrames = new ArrayList<>();
			List<HolidayWorkFrameTimeSheetForCalc> holidayTimeFrames = new ArrayList<>();
			Optional<HolidayAtr> atr = notTargetDayWorkType.getHolidayAtr();
			//全て他の休出・残業時間帯へ変更する
			if(!holidayWorkTimeSheet.getTimeSheet().getTimeSpan().contains(baseTime)) {
				/*休出時間帯から休出時間帯作成*/
				if(notTargetDayWorkType.getDailyWork().isHolidayWork() 
				 ||notTargetDayWorkType.getDailyWork().isOneDayHoliday()) {
					val getframe = zeroTime.getOverdayCalcHoliday().stream().filter(tc -> tc.getHolidayWorkFrameNo() == holidayWorkTimeSheet.getFrameTime().getHolidayFrameNo().v().intValue()).findFirst();
					if(getframe.get().useFrameNo(atr.get()) == 0) {
						//振り替えない
						holidayTimeFrames.add(holidayWorkTimeSheet);
					}
					else {
						/*休出時間帯へ変換*/
						holidayTimeFrames.add(convertDifferenceHolidayWork(holidayWorkTimeSheet,atr,zeroTime));
					}

				}
				else {
					val getframe = zeroTime.getOverdayHolidayAtten().stream().filter(tc -> tc.getHolidayWorkFrameNo() == holidayWorkTimeSheet.getFrameTime().getHolidayFrameNo().v().intValue()).findFirst();
					if(getframe.get().getOverWorkNo().intValue() == 0) {
						//振り替えない
						holidayTimeFrames.add(holidayWorkTimeSheet);
					}
					else {
						/*残業時間帯へ変換*/
						overTimeFrames.add(convertOverTimeWork(holidayWorkTimeSheet,zeroTime));
					}
				}
			}
			//休出時間帯が、一部他の休出残業時間帯へ変化するパティーン
			else {
				//前日～当日跨ぎの時の分割処理
				if(isBefore) {
					//前日分の休出時間帯作成
					
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
																									   Finally.of(StaturoryAtrOfHolidayWork.deicisionAtrByHolidayAtr(atr.get()))
																									   );
					if(notTargetDayWorkType.getDailyWork().isHolidayWork() 
					 ||notTargetDayWorkType.getDailyWork().isOneDayHoliday()) {
						val getframe = zeroTime.getOverdayCalcHoliday().stream().filter(tc -> tc.getHolidayWorkFrameNo() == holidayWorkTimeSheet.getFrameTime().getHolidayFrameNo().v().intValue()).findFirst();
						if(getframe.get().useFrameNo(atr.get()) == 0) {
							//振り替えない
							return new OverDayEnd(overTimeFrames, Arrays.asList(holidayWorkTimeSheet));
						}
						else {
							/*休出時間帯へ変換*/
							holidayTimeFrames.add(convertDifferenceHolidayWork(beforeitem,atr, zeroTime));
						}
					}
					else {
						val getframe = zeroTime.getOverdayHolidayAtten().stream().filter(tc -> tc.getHolidayWorkFrameNo() == holidayWorkTimeSheet.getFrameTime().getHolidayFrameNo().v().intValue()).findFirst();
						if(getframe.get().getOverWorkNo().intValue() == 0) {
							return new OverDayEnd(overTimeFrames, Arrays.asList(holidayWorkTimeSheet));
						}
						else {
							/*残業時間帯へ変換*/
							overTimeFrames.add(convertOverTimeWork(beforeitem, zeroTime));
						}
					}
					
					//当日分の休出時間帯作成
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
							   																		  holidayWorkTimeSheet.getStatutoryAtr()
							   																		  );
					holidayTimeFrames.add(afterList);
				}
				//当日～翌日跨ぎ
				else {
					//当日分の休出時間帯作成
					HolidayWorkFrameTimeSheetForCalc targetItem = new HolidayWorkFrameTimeSheetForCalc(new TimeZoneRounding( holidayWorkTimeSheet.getTimeSheet().getStart(),baseTime, holidayWorkTimeSheet.getTimeSheet().getRounding()),
							   																		  new TimeSpanForCalc(holidayWorkTimeSheet.getCalcrange().getStart(),baseTime),
							   																		  holidayWorkTimeSheet.recreateDeductionItemBeforeBase(baseTime, true, DeductionAtr.Appropriate),
							   																		  holidayWorkTimeSheet.recreateDeductionItemBeforeBase(baseTime, true, DeductionAtr.Deduction),
							   																		  holidayWorkTimeSheet.recreateBonusPayListBeforeBase(baseTime, true),
							   																		  holidayWorkTimeSheet.recreateSpecifiedBonusPayListBeforeBase(baseTime, true),
							   																		  holidayWorkTimeSheet.recreateMidNightTimeSheetBeforeBase(baseTime, true),
							   																		  holidayWorkTimeSheet.getFrameTime(),
							   																		  holidayWorkTimeSheet.isTreatAsTimeSpentAtWork(),
							   																		  holidayWorkTimeSheet.getHolidayWorkTimeSheetNo(),
							   																		  holidayWorkTimeSheet.getStatutoryAtr()
							   																		  );
					/*開始時間が早い方の時間帯を休日出勤時間帯へ変更*/
					holidayTimeFrames.add(targetItem);
					//翌日分の休出時間帯作成
					HolidayWorkFrameTimeSheetForCalc noTargetitem = new HolidayWorkFrameTimeSheetForCalc(new TimeZoneRounding(baseTime,holidayWorkTimeSheet.getTimeSheet().getEnd(),  holidayWorkTimeSheet.getTimeSheet().getRounding()),
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
																									   Finally.of(StaturoryAtrOfHolidayWork.deicisionAtrByHolidayAtr(atr.get()))
																									   );
					if(notTargetDayWorkType.getDailyWork().isHolidayWork() 
					 ||notTargetDayWorkType.getDailyWork().isOneDayHoliday()) {
						val getframe = zeroTime.getOverdayCalcHoliday().stream().filter(tc -> tc.getHolidayWorkFrameNo() == holidayWorkTimeSheet.getFrameTime().getHolidayFrameNo().v().intValue()).findFirst();
						if(getframe.get().useFrameNo(atr.get()) == 0) {
							//振り替えない
							return new OverDayEnd(overTimeFrames, Arrays.asList(holidayWorkTimeSheet));
						}
						else {
							/*休出時間帯へ変換*/
							holidayTimeFrames.add(convertDifferenceHolidayWork(noTargetitem,atr, zeroTime));
						}
					}
					else {
						val getframe = zeroTime.getOverdayHolidayAtten().stream().filter(tc -> tc.getHolidayWorkFrameNo() == holidayWorkTimeSheet.getFrameTime().getHolidayFrameNo().v().intValue()).findFirst();
						if(getframe.get().getOverWorkNo().intValue() == 0) {
							//振り替えない
							return new OverDayEnd(overTimeFrames, Arrays.asList(holidayWorkTimeSheet));
						}
						else {
						/*残業時間帯へ変換*/
							overTimeFrames.add(convertOverTimeWork(noTargetitem, zeroTime));
						}
					}
				}
			}
			return new OverDayEnd(overTimeFrames, holidayTimeFrames);
		}
		
		
		
		/**
		 * 休日出勤から残業時間帯への変換
		 * @param holidayWorkTimeSheet 変換する休出時間帯
		 * @return　残業時間帯
		 */
		public static OverTimeFrameTimeSheetForCalc convertOverTimeWork(HolidayWorkFrameTimeSheetForCalc holidayWorkTimeSheet,ZeroTime zeroTime) {
			val getframe = zeroTime.getOverdayHolidayAtten().stream().filter(tc -> tc.getHolidayWorkFrameNo() == holidayWorkTimeSheet.getFrameTime().getHolidayFrameNo().v().intValue()).findFirst();
			OverTimeFrameTime frameTime = new OverTimeFrameTime(new OverTimeFrameNo(getframe.get().getOverWorkNo().intValue()),//0時跨ぎマスタの設定を基にここを埋めるように変更する
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
		 * @param atr 
		 * @param zeroTime 
		 * @return　休出時間帯
		 */
		public static HolidayWorkFrameTimeSheetForCalc convertDifferenceHolidayWork(HolidayWorkFrameTimeSheetForCalc holidayWorkTimeSheet, Optional<HolidayAtr> atr, ZeroTime zeroTime) {
			
			val getframe = zeroTime.getOverdayCalcHoliday().stream().filter(tc -> tc.getHolidayWorkFrameNo() == holidayWorkTimeSheet.getFrameTime().getHolidayFrameNo().v().intValue()).findFirst();
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(getframe.get().useFrameNo(atr.get())),//0時跨ぎマスタの設定を基にここを埋めるように変更する
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
													    Finally.of(StaturoryAtrOfHolidayWork.deicisionAtrByHolidayAtr(atr.get())));
		}
	}



	

