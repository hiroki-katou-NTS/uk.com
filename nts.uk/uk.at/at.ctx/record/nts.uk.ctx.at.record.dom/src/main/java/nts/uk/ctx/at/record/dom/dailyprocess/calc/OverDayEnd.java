package nts.uk.ctx.at.record.dom.dailyprocess.calc;
//
//import java.util.List;
//
//import lombok.Getter;
//import nts.gul.util.value.Finally;
//import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
//import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTime;
//import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTimeSheet;
//import nts.uk.ctx.at.record.dom.daily.overtimework.enums.StatutoryAtr;
//import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
//import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
//import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSet;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndSetOfHolidayAttendance;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndSetOfHolidayHoliday;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndSetOfWeekDayHoliday;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
//import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
//import nts.uk.ctx.at.shared.dom.worktime.commonsetting.primitive.WorkTimeNo;
//import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
//import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
//import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
//import nts.uk.ctx.at.shared.dom.worktype.WorkType;
//import nts.uk.shr.com.time.TimeWithDayAttr;
//
///**
// * 日付をまたいだ時の処理
// * @author keisuke_hoshina
// *
// */
//@Getter
public class OverDayEnd {

//	/**
//	 * 残業時間帯の分割
//	 * @author keisuke_hoshina
//	 *
//	 */
//	public class SplitOverTimeWork{
//		
//		@Getter
//		public List<OverTimeFrameTimeSheet> dedList;
//		
//		@Getter
//		public List<HolidayWorkFrameTimeSheet> holList; 
//		/**
//		 * メインの流れを司るメソッド
//		 * @return 時間帯
//		 */
//		public SplitOverTimeWork(OverDayEndCalcSet dayEndSet,WorkTimeCommonSet overDayEndSet ,List<OverTimeFrameTimeSheet> overTimeWorkItems,
//														WorkType beforeDay,WorkType toDay,WorkType afterDay) {
//			if(judge(dayEndSet,overDayEndSet)) {
//				for(OverTimeFrameTimeSheet overTimeWorkItem : overTimeWorkItems) {
////					if(isSplit(overTimeWorkItem.calcrange,0,beforeDay.getDailyWork().isHolidayWork() , beforeDay.getDailyWork(),toDay.getDailyWork(),dayEndSet)) {
////						daySplit(overTimeWorkItem,
////								new TimeWithDayAttr(0),
////								dayEndSet.getOverDayEndAggregateFrameSet().getOverDayEndSetOfWeekDayHoliday().stream().filter(tc->tc.getOverWorkFrameNo().v().equals(overTimeWorkItem.getFrameNo().v()) ).findFirst().get(),
////								beforeDay);	
////					}
////					else if(isSplit(overTimeWorkItem.calcrange,1440,toDay.getDailyWork().isHolidayWork(), toDay.getDailyWork(),afterDay.getDailyWork(),dayEndSet)) {
////						daySplit(overTimeWorkItem,
////								new TimeWithDayAttr(1440),
////								dayEndSet.getOverDayEndAggregateFrameSet().getOverDayEndSetOfWeekDayHoliday().stream().filter(tc->tc.getOverWorkFrameNo().v().equals(overTimeWorkItem.getFrameNo().v())).findFirst().get(),
////								afterDay);
////					}
////					else {
////						dedList.add(overTimeWorkItem);
////					}
//				}
//			}
//			
//			else {
//				dedList.addAll(overTimeWorkItems);
//			}
//			
//		}
//		
//		
//		/**
//		 * 0時またぎ分割処理
//		 */
//		private void daySplit(OverTimeFrameTimeSheet item,TimeWithDayAttr baseTime,OverDayEndSetOfWeekDayHoliday yesterDayWorkTime,WorkType notTargetDay) {
//			if(!item.calcrange.contains(baseTime)) {
//				/*残業時間帯から休出時間帯作成*/
//				holList.add(convertHolidayWorkTimeSheet(yesterDayWorkTime,item.calcrange,item,notTargetDay));
//			}
//			else {
//				/*基準時間で分割*/
//				OverTimeFrameTimeSheet beforeitem = new OverTimeFrameTimeSheet(
//																item.timeSheet,
//																new TimeSpanForCalc(item.calcrange.getStart(),baseTime),
//																item.recreateDeductionItemBeforeBase(baseTime, true),
//																item.recreateBonusPayListBeforeBase(baseTime, true),
//																item.recreateSpecifiedBonusPayListBeforeBase(baseTime, true),
//																item.recreateMidNightTimeSheetBeforeBase(baseTime, true),
//																item.getFrameNo(),
//																item.getOverWorkFrameTime(),
//																item.isGoEarly(),
//																item.getWithinStatutoryAtr());
//				
//				HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(item.getFrameNo().v()),
//																		  Finally.of(TimeWithCalculation.sameTime(new AttendanceTime(0))),
//																		  Finally.of(TimeWithCalculation.sameTime(new AttendanceTime(0))),
//																		  Finally.of(new AttendanceTime(0)));
//				/*開始時間が早い方の時間帯を休日出勤時間帯へ変更*/
//				HolidayWorkFrameTimeSheet holidaybeforeitem = new HolidayWorkFrameTimeSheet(
//																beforeitem.getTimeSheet(),
//																beforeitem.calcrange,
//																beforeitem.getDeductionTimeSheet(),
//																beforeitem.getBonusPayTimeSheet(),
//																beforeitem.specBonusPayTimesheet,
//																beforeitem.getMidNightTimeSheet(),
//																frameTime,
//																false,
//																new WorkTimeNo(0));
//				
//				
//				OverTimeFrameTimeSheet afterList = new OverTimeFrameTimeSheet(
//																item.timeSheet,
//																new TimeSpanForCalc(baseTime,item.calcrange.getEnd()),
//																item.recreateDeductionItemBeforeBase(baseTime, false),
//																item.recreateBonusPayListBeforeBase(baseTime, false),
//																item.recreateSpecifiedBonusPayListBeforeBase(baseTime, false),
//																item.recreateMidNightTimeSheetBeforeBase(baseTime, false),
//																item.getFrameNo(),
//																item.getOverWorkFrameTime(),
//																item.isGoEarly(),
//																item.getWithinStatutoryAtr());
//				
//				holList.add(holidaybeforeitem);
//				dedList.add(afterList);
//			}
//			
//
//		}
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
//	
//	
//	/**
//	 * 休日出勤時間帯の分割
//	 * @author keisuke_hoshina
//	 *
//	 */
//	public class SplitHolidayWorkTime{
//		@Getter
//		private List<OverTimeFrameTimeSheet> dedList;
//		@Getter
//		private List<HolidayWorkFrameTimeSheet> holList; 
//		/**:
//		 * メインの流れを司るメソッド
//		 * @return 時間帯
//		 */
//		public SplitHolidayWorkTime(OverDayEndCalcSet dayEndSet,WorkTimeCommonSet overDayEndSet ,List<HolidayWorkFrameTimeSheet> holidayTimeWorkItems,
//														WorkType beforeDay,WorkType toDay,WorkType afterDay) {
//			if(judge(dayEndSet,overDayEndSet)) {
////				for(HolidayWorkFrameTimeSheet holidayTimeWorkItem : holidayTimeWorkItems) {
////					if(isSplit(holidayTimeWorkItem.calcrange,0,beforeDay.getDailyWork().isHolidayWork() , beforeDay.getDailyWork(),toDay.getDailyWork(),dayEndSet)) {
////						daySplit(new TimeWithDayAttr(0),
////								dayEndSet.getOverDayEndAggregateFrameSet().getOverDayEndSetOfHolidayAttendance().stream().filter(tc -> tc.getHolidayWorkFrameNo().v().intValue() == holidayTimeWorkItem.getHolidayWorkTimeSheetNo().v().intValue()).findFirst().get()
////								,holidayTimeWorkItem
////								,toDay
////								,beforeDay
////								,dayEndSet.getOverDayEndAggregateFrameSet().getOverDayEndSetOfHolidayHoliday().stream().filter(tc -> tc.getHolidayWorkFrameNo().v().intValue() == holidayTimeWorkItem.getHolidayWorkTimeSheetNo().v().intValue()).findFirst().get()
////								);
////					}
////					else if(isSplit(holidayTimeWorkItem.calcrange,1440,beforeDay.getDailyWork().isHolidayWork(), beforeDay.getDailyWork(),toDay.getDailyWork(),dayEndSet)) {
////						daySplit(new TimeWithDayAttr(1440),
////								dayEndSet.getOverDayEndAggregateFrameSet().getOverDayEndSetOfHolidayAttendance().stream().filter(tc -> tc.getHolidayWorkFrameNo().v().intValue() == holidayTimeWorkItem.getHolidayWorkTimeSheetNo().v().intValue()).findFirst().get()
////								,holidayTimeWorkItem
////								,toDay
////								,afterDay
////								,dayEndSet.getOverDayEndAggregateFrameSet().getOverDayEndSetOfHolidayHoliday().stream().filter(tc -> tc.getHolidayWorkFrameNo().v().intValue() == holidayTimeWorkItem.getHolidayWorkTimeSheetNo().v().intValue()).findFirst().get()
////								);
////					}
////					else {
////						holList.add(holidayTimeWorkItem);
////					}
////				}
//			}
//			else {
//				holList.addAll(holidayTimeWorkItems);
//			}
//		}
//		
//
//		/**
//		 * 休出の0時またぎ分割処理
//		 * @param baseTime　基準時間
//		 * @param holidayFrameSet 休日枠の設定
//		 * @param holidayWorkTimeSheet 休出時間帯
//		 */
//		private void daySplit(TimeWithDayAttr baseTime,OverDayEndSetOfHolidayAttendance holidayFrameSet,HolidayWorkFrameTimeSheet holidayWorkTimeSheet
//							  ,WorkType toDay,WorkType notToDay,OverDayEndSetOfHolidayHoliday holidayholidayFrameSet) {
////			//baseTimeを含んでいない
//			if(!holidayWorkTimeSheet.calcrange.contains(baseTime)) {
////				/*休出時間帯から休出時間帯作成*/
////				if()) {
////					/*休出時間帯へ変換*/
////					holList.add(convertDifferenceHolidayWork(holidayholidayFrameSet));
////				}
////				else {
////					/*残業時間帯へ変換*/
////					dedList.add(convertOverTimeWork(holidayWorkTimeSheet,holidayFrameSet.getTransferFrameNoOfOverWork()));
////				}
////				/*残業時間帯クリア*/
////			}
////			//baseTimeを含んでいる
////			else {
////				/*当日baseTimeで分割*/
////				HolidayWorkFrameTimeSheet beforeitem = new HolidayWorkFrameTimeSheet(
////																holidayWorkTimeSheet.timeSheet,
////																new TimeSpanForCalc(holidayWorkTimeSheet.calculationTimeSheet.getStart(),baseTime),
////																holidayWorkTimeSheet.recreateDeductionItemBeforeBase(baseTime, true),
////																holidayWorkTimeSheet.recreateBonusPayListBeforeBase(baseTime, true),
////																holidayWorkTimeSheet.recreateMidNightTimeSheetBeforeBase(baseTime, true),
////																holidayWorkTimeSheet.getFrameTime(),
////																holidayWorkTimeSheet.isTreatAsTimeSpentAtWork()
////																);
////				if(!notToDay.getWorkTypeSetList().get(0).getDigestPublicHd().equals(null)
////					&&!notToDay.getWorkTypeSetList().get(0).getDigestPublicHd().equals(null)) {
////					/*休出時間帯へ変換*/
////					holList.add(convertDifferenceHolidayWork(beforeitem));
////				}
////				else {
////					/*残業時間帯へ変換*/
////					dedList.add(convertOverTimeWork(beforeitem));
////				}
////				
////				
////				HolidayWorkFrameTimeSheet afterList = new HolidayWorkFrameTimeSheet(
////																holidayWorkTimeSheet.timeSheet,
////																new TimeSpanForCalc(baseTime,holidayWorkTimeSheet.getCalculationTimeSheet().getEnd()),
////																holidayWorkTimeSheet.recreateDeductionItemBeforeBase(baseTime, false),
////																holidayWorkTimeSheet.recreateBonusPayListBeforeBase(baseTime, false),
////																holidayWorkTimeSheet.recreateMidNightTimeSheetBeforeBase(baseTime, false),
////																holidayWorkTimeSheet.getFrameTime(),
////																holidayWorkTimeSheet.isTreatAsTimeSpentAtWork(),
////																holidayWorkTimeSheet.);
////				/*開始時間が早い方の時間帯を休日出勤時間帯へ変更*/
////				holList.add(afterList);
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
//	/*共通で使う処理たち*/
//	private boolean judge(OverDayEndCalcSet dayEndSet,WorkTimeCommonSet overDayEndSet){
//		return dayEndSet.getCalcOverDayEnd().isUse()&&overDayEndSet.isOverDayEndCalcSet();
//	}
//	
//
//	/**
//	 * 前日・翌日の0時またぎ判断処理
//	 * @param timeSpan 時間帯
//	 * @param baseTime 基準時間 
//	 * @param dicisionAtr 休出・残業によって変化する勤務種類の状態
//	 *         休出：前日と当日の休出区分が同じである
//	 *         残業：休出以外である
//	 * @param beforeDay　時刻が早い方の勤務種類
//	 * @param afterDay　　時刻が遅い方の勤務種類
//	 * @param overDaySet 0時跨ぎ設定
//	 * @return
//	 */
//	public boolean isSplit(TimeSpanForCalc timeSpan,int baseTime,boolean dicisionAtr,DailyWork beforeDay,DailyWork afterDay, OverDayEndCalcSet overDaySet) {
//		if(getIsUse(beforeDay,afterDay,overDaySet).isUse()) {
//			if(!beforeDay.equals(null)) {
//				if(timeSpan.getEnd().lessThan(baseTime)) {
//					if(dicisionAtr) {
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
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
//
//	
}
