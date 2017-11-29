package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.record.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.record.dom.daily.OverTimeWorkOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.record.dom.daily.holidaywork.HolidayWorkFrameTime;
import nts.uk.ctx.at.record.dom.daily.holidaywork.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndAggregateFrameSet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSetOfWeekDay;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndSetOfHolidayAttendance;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.OverDayEndSetOfWeekDayAttendance;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 日付をまたいだ時の処理
 * @author keisuke_hoshina
 *
 */
public class OverDayEnd {

	/**
	 * 残業時間帯の分割
	 * @author keisuke_hoshina
	 *
	 */
	public class SplitOverTimeWork{
		


		@Getter
		private List<OverTimeWorkFrameTimeSheet> dedList;
		
		@Getter
		private List<HolidayWorkFrameTimeSheet> holList; 
		/**
		 * メインの流れを司るメソッド
		 * @return 時間帯
		 */
		public SplitOverTimeWork(OverDayEndCalcSet dayEndSet,WorkTimeCommonSet overDayEndSet ,List<OverTimeWorkFrameTimeSheet> overTimeWorkItems,
														WorkType beforeDay,WorkType toDay,WorkType afterDay) {
			if(judge(dayEndSet,overDayEndSet)) {
				for(OverTimeWorkFrameTimeSheet overTimeWorkItem : overTimeWorkItems) {
					if(isSplit(overTimeWorkItem.calcrange,0,beforeDay.getDailyWork().isHolidayWork() , beforeDay.getDailyWork(),toDay.getDailyWork(),dayEndSet)) {
						daySplit(overTimeWorkItem,new TimeWithDayAttr(0),dayEndSet.getOverDayEndAggregateFrameSet().getOverDayEndSetOfWeekDayAttendance().stream().filter(tc->tc.getOverWorkFrameID() == overTimeWorkItem.getFrameNo()).findFirst().get());	
					}
					else if(isSplit(overTimeWorkItem.calcrange,1440,toDay.getDailyWork().isHolidayWork(), toDay.getDailyWork(),afterDay.getDailyWork(),dayEndSet)) {
						daySplit(overTimeWorkItem,new TimeWithDayAttr(1440),dayEndSet.getOverDayEndAggregateFrameSet().getOverDayEndSetOfWeekDayAttendance().stream().filter(tc->tc.getOverWorkFrameID() == overTimeWorkItem.getFrameNo()).findFirst().get());
					}
					else {
						dedList.add(overTimeWorkItem);
					}
				}
			}
			else {
				dedList.addAll(overTimeWorkItems);
			}
			
		}
		
		
		/**
		 * 0時またぎ分割処理
		 */
		private void daySplit(OverTimeWorkFrameTimeSheet item,TimeWithDayAttr baseTime,OverDayEndSetOfWeekDayAttendance yesterDayWorkTime) {
			if(!item.calcrange.contains(baseTime)) {
				/*残業時間帯から休出時間帯作成*/
				holList.add(convertHolidayWorkTimeSheet(yesterDayWorkTime,item.calcrange,item));
				/*残業時間帯クリア*/
			}
			else {
				/*基準時間で分割*/
				OverTimeWorkFrameTimeSheet beforeitem = new OverTimeWorkFrameTimeSheet(
																item.timeSheet,
																new TimeSpanForCalc(item.calcrange.getStart(),baseTime),
																item.recreateDeductionItemBeforeBase(baseTime, true),
																item.recreateBonusPayListBeforeBase(baseTime, true),
																item.recreateSpecifiedBonusPayListBeforeBase(baseTime, true),
																item.recreateMidNightTimeSheetBeforeBase(baseTime, true),
																item.getFrameNo(),
																item.getOverWorkFrameTime(),
																item.isGoEarly(),
																item.getWithinStatutoryAtr());
				
				HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(baseTime.valueAsMinutes()),
																		  TimeWithCalculation.of(new AttendanceTime(0)),
																		  TimeWithCalculation.of(new AttendanceTime(0)),
																		  new AttendanceTime(0));
				HolidayWorkFrameTimeSheet holidaybeforeitem = new HolidayWorkFrameTimeSheet(
																beforeitem.getTimeSheet(),
																beforeitem.calcrange,
																beforeitem.getDeductionTimeSheets(),
																beforeitem.getBonusPayTimeSheet(),
																beforeitem.getSpecifiedBonusPayTimeSheet(),
																beforeitem.getMidNightTimeSheet(),
																frameTime,
																false,
																new HolidayWorkFrameNo(0));
				OverTimeWorkFrameTimeSheet afterList = new OverTimeWorkFrameTimeSheet(
																item.timeSheet,
																new TimeSpanForCalc(baseTime,item.calcrange.getEnd()),
																item.recreateDeductionItemBeforeBase(baseTime, false),
																item.recreateBonusPayListBeforeBase(baseTime, false),
																item.recreateSpecifiedBonusPayListBeforeBase(baseTime, false),
																item.recreateMidNightTimeSheetBeforeBase(baseTime, false),
																item.getFrameNo(),
																item.getOverWorkFrameTime(),
																item.isGoEarly(),
																item.getWithinStatutoryAtr());
				/*開始時間が早い方の時間帯を休日出勤時間帯へ変更*/
				holList.add(holidaybeforeitem);
				dedList.add(afterList);
			}
			

		}
		/**
		 * 残業時間帯へから休日出勤時間帯への変換
		 * @param weekDaySet 平日出勤の0時跨ぎの設定
		 * @param timeSpan 時間帯
		 * @return　休出時間帯
		 */
		private HolidayWorkFrameTimeSheet convertHolidayWorkTimeSheet(OverDayEndSetOfWeekDayAttendance weekDaySet,TimeSpanForCalc timeSpan,OverTimeWorkFrameTimeSheet overTimeSheet) {
			HolidayWorkFrameNo no = weekDaySet./*0時跨ぎ計算設定に対して休日区分を投げ、休出NOをここで取得する*/;
			TimeWithCalculation time = TimeWithCalculation.of(new AttendanceTime(0));
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(0),
					  							TimeWithCalculation.of(new AttendanceTime(0)),
					  							TimeWithCalculation.of(new AttendanceTime(0)),
					  							new AttendanceTime(0));
			//return new HolidayWorkFrameTimeSheet(timeSpan , HolidayWorkFrameTime(no,time,time,new AttendanceTime(0)),false);
			return new HolidayWorkFrameTimeSheet(
					overTimeSheet.getTimeSheet(),
					overTimeSheet.getCalcrange(),
					overTimeSheet.getDeductionTimeSheets(),
					overTimeSheet.getBonusPayTimeSheet(),
					overTimeSheet.getSpecifiedBonusPayTimeSheet(),
					overTimeSheet.getMidNightTimeSheet(),
					frameTime,
					false,
					no);
		}
	}
	
	
	/**
	 * 休日出勤時間帯の分割
	 * @author keisuke_hoshina
	 *
	 */
	public class SplitHolidayWorkTime{
		@Getter
		private List<OverTimeWorkFrameTimeSheet> dedList;
		@Getter
		private List<HolidayWorkFrameTimeSheet> holList; 
		/**:
		 * メインの流れを司るメソッド
		 * @return 時間帯
		 */
		public SplitHolidayWorkTime(OverDayEndCalcSet dayEndSet,WorkTimeCommonSet overDayEndSet ,List<HolidayWorkFrameTimeSheet> holidayTimeWorkItems,
														WorkType beforeDay,WorkType toDay,WorkType afterDay) {
			if(judge(dayEndSet,overDayEndSet)) {
				for(HolidayWorkFrameTimeSheet holidayTimeWorkItem : holidayTimeWorkItems) {
					if(isSplit(holidayTimeWorkItem.calcrange,0,beforeDay.getDailyWork().isHolidayWork() , beforeDay.getDailyWork(),toDay.getDailyWork(),dayEndSet)) {
						daySplit(new TimeWithDayAttr(0),dayEndSet.getOverDayEndAggregateFrameSet().getOverDayEndSetOfHolidayAttendance(),holidayTimeWorkItem);
					}
					else if(isSplit(holidayTimeWorkItem.calcrange,1440,beforeDay.getDailyWork().isHolidayWork(), beforeDay.getDailyWork(),toDay.getDailyWork(),dayEndSet)) {
						daySplit(new TimeWithDayAttr(1440),dayEndSet.getOverDayEndAggregateFrameSet().getOverDayEndSetOfHolidayAttendance(),holidayTimeWorkItem);
					}
					else {
						holList.add(holidayTimeWorkItem);
					}
				}
			}
			else {
				holList.addAll(holidayTimeWorkItems);
			}
		}
		

		/**
		 * 休出の0時またぎ分割処理
		 * @param baseTime　基準時間
		 * @param holidayFrameSet 休日枠の設定
		 * @param holidayWorkTimeSheet 休出時間帯
		 */
		private void daySplit(TimeWithDayAttr baseTime,List<OverDayEndSetOfHolidayAttendance> holidayFrameSet,HolidayWorkFrameTimeSheet holidayWorkTimeSheet) {
			if(!holidayWorkTimeSheet.calculationTimeSheet.contains(baseTime)) {
				/*残業時間帯から休出時間帯作成*/
				if(/*次の日の勤務種類が休日設定を持つ場合*/) {
					/*休出時間帯へ変換*/
					holList.add(convertDifferenceHolidayWork());
				}
				else {
					/*残業時間帯へ変換*/
					dedList.add(convertOverTimeWork(holidayWorkTimeSheet,holidayFrameSet.stream().filter(tc -> tc == (int)getFrameTime().getHolidayFrameNo()).findFirst().get()));
				}
				/*残業時間帯クリア*/
			}
			else {
				/*当日baseTimeで分割*/
				HolidayWorkFrameTimeSheet beforeitem = new HolidayWorkFrameTimeSheet(
																holidayWorkTimeSheet.timeSheet,
																new TimeSpanForCalc(holidayWorkTimeSheet.calculationTimeSheet.getStart(),baseTime),
																holidayWorkTimeSheet.recreateDeductionItemBeforeBase(baseTime, true),
																holidayWorkTimeSheet.recreateBonusPayListBeforeBase(baseTime, true),
																holidayWorkTimeSheet.recreateMidNightTimeSheetBeforeBase(baseTime, true),
																holidayWorkTimeSheet.getFrameTime(),
																holidayWorkTimeSheet.isTreatAsTimeSpentAtWork()
																);
				if(/*次の日の勤務種類が休日設定を持つ場合*/) {
					/*休出時間帯へ変換*/
					holList.add(convertDifferenceHolidayWork(beforeitem));
				}
				else {
					/*残業時間帯へ変換*/
					dedList.add(convertOverTimeWork(beforeitem));
				}
				
				
				HolidayWorkFrameTimeSheet afterList = new HolidayWorkFrameTimeSheet(
																holidayWorkTimeSheet.timeSheet,
																new TimeSpanForCalc(baseTime,holidayWorkTimeSheet.getCalculationTimeSheet().getEnd()),
																holidayWorkTimeSheet.recreateDeductionItemBeforeBase(baseTime, false),
																holidayWorkTimeSheet.recreateBonusPayListBeforeBase(baseTime, false),
																holidayWorkTimeSheet.recreateMidNightTimeSheetBeforeBase(baseTime, false),
																holidayWorkTimeSheet.getFrameTime(),
																holidayWorkTimeSheet.isTreatAsTimeSpentAtWork());
				/*開始時間が早い方の時間帯を休日出勤時間帯へ変更*/
				holList.add(afterList);

			}
		}
		
		/**
		 * 休日出勤から残業時間帯への変換
		 * @param holidayWorkTimeSheet
		 * @return　残業時間帯
		 */
		public OverTimeWorkFrameTimeSheet convertOverTimeWork(HolidayWorkFrameTimeSheet holidayWorkTimeSheet,int frameNo) {
			OverTimeWorkFrameTime frameTime = new OverTimeWorkFrameTime(frameNo,
																	TimeWithCalculation.of(new AttendanceTime(0)),
																	TimeWithCalculation.of(new AttendanceTime(0)),
																	new AttendanceTime(0));
			return new OverTimeWorkFrameTimeSheet(holidayWorkTimeSheet.getTimeSheet()
												 ,holidayWorkTimeSheet.calcrange
												 ,holidayWorkTimeSheet.getDeductionTimeSheets()
												 ,holidayWorkTimeSheet.getBonusPayTimeSheet()
												 ,holidayWorkTimeSheet.getSpecifiedBonusPayTimeSheet()
												 ,holidayWorkTimeSheet.getMidNightTimeSheet()
												 ,0
												 ,frameTime
												 ,false
												 ,WithinStatutoryAtr.ExcessOfStatutory);
		}
		
		/**
		 * 休日出勤から休日出勤への変換
		 * @param holidayAtr
		 * @param holidayWorkTimeSheet
		 * @return　休出時間帯
		 */
		public HolidayWorkFrameTimeSheet convertDifferenceHolidayWork(HolidayAtr holidayAtr, HolidayWorkFrameTimeSheet holidayWorkTimeSheet) {//, /*休日出勤の0時跨ぎ設定*/) {
			
			HolidayWorkFrameTime frameTime = new HolidayWorkFrameTime(new HolidayWorkFrameNo(0),
					  TimeWithCalculation.of(new AttendanceTime(0)),
					  TimeWithCalculation.of(new AttendanceTime(0)),
					  new AttendanceTime(0));
			
			return new HolidayWorkFrameTimeSheet(holidayWorkTimeSheet.getTimeSheet(),holidayWorkTimeSheet.calcrange,holidayWorkTimeSheet.getDeductionTimeSheets(),
												 holidayWorkTimeSheet.getBonusPayTimeSheet(), holidayWorkTimeSheet.getMidNightTimeSheet(),frameTime,frameNo,,false,WithinStatutoryAtr.ExcessOfStatutory);
		}
	}
	/*共通で使う処理たち*/
	private boolean judge(OverDayEndCalcSet dayEndSet,WorkTimeCommonSet overDayEndSet){
		return dayEndSet.getCalcOverDayEnd().isUse()&&overDayEndSet.isOverDayEndCalcSet();
	}
	

	/**
	 * 前日・翌日の0時またぎ判断処理
	 * @param timeSpan 時間帯
	 * @param baseTime 基準時間 
	 * @param dicisionAtr 休出・残業によって変化する勤務種類の状態
	 *         休出：前日と当日の休出区分が同じである
	 *         残業：休出以外である
	 * @param beforeDay　時刻が早い方の勤務種類
	 * @param afterDay　　時刻が遅い方の勤務種類
	 * @param overDaySet 0時跨ぎ設定
	 * @return
	 */
	public boolean isSplit(TimeSpanForCalc timeSpan,int baseTime,boolean dicisionAtr,DailyWork beforeDay,DailyWork afterDay, OverDayEndCalcSet overDaySet) {
		if(getIsUse(beforeDay,afterDay,overDaySet).isUse()) {
			if(timeSpan.getEnd().lessThan(baseTime)) {
				if(dicisionAtr) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 該当日の計算するしない設定を取得する
	 * @param beforeDay 前の日の勤務種類
	 * @param afterDay　　後の日の勤務種類
	 * @param overDaySet　0時跨ぎ計算設定
	 * @return
	 */
	private UseAtr getIsUse(DailyWork beforeDay,DailyWork afterDay, OverDayEndCalcSet overDaySet) {
		if(afterDay.) {
			/*休日*/
			switch() {
			case:/*法定内休日*/
			case:/*法定外休日*/
			case:/*祝日*/
			}
		}
		else {
			/*平日*/
		}
		return UseAtr.USE;
	}

	
}
