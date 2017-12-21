package nts.uk.ctx.at.record.dom.dailyprocess.calc.bonuspay;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.RoundingAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UnitAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.shr.com.time.AttendanceClock;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 加給時間(計算用)
 * @author keisuke_hoshina
 *
 */
public class BonusPayForCalc extends CalculationTimeSheet{
	
	private int timeSheetId;

	private UseAtr useAtr;

	private String timeItemId;

	private AttendanceClock startTime;

	private AttendanceClock endTime;

	private UnitAtr roundingTimeAtr;

	private RoundingAtr roundingAtr;

	public BonusPayForCalc(TimeSpanWithRounding withRounding
			,TimeSpanForCalc timeSpan
			,List<TimeSheetOfDeductionItem> deductionTimeSheets
			,List<BonusPayTimesheet> bonusPayTimeSheet
			,List<SpecBonusPayTimesheet> SpecBonusPayTimesheet
			,Optional<MidNightTimeSheet> midNighttimeSheet,
			int timeSheetId, UseAtr useAtr, String timeItemId,
			AttendanceClock startTime, AttendanceClock endTime, UnitAtr roundingTimeAtr,
			RoundingAtr roundingAtr) {
		super(withRounding,timeSpan,deductionTimeSheets,bonusPayTimeSheet,SpecBonusPayTimesheet,midNighttimeSheet);
		this.timeSheetId = timeSheetId;
		this.useAtr = useAtr;
		this.timeItemId = timeItemId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roundingTimeAtr = roundingTimeAtr;
		this.roundingAtr = roundingAtr;
	}
	
	public BonusPayForCalc convertForCalc(BonusPayTimesheet bonusPayTimeSheet) {
		return new BonusPayForCalc(new TimeSpanWithRounding(new TimeWithDayAttr(bonusPayTimeSheet.getStartTime().v()), new TimeWithDayAttr(bonusPayTimeSheet.getEndTime().v()),Finally.empty()),
								   new TimeSpanForCalc(new TimeWithDayAttr(bonusPayTimeSheet.getStartTime().v()),new TimeWithDayAttr(bonusPayTimeSheet.getEndTime().v())),
								  Collections.emptyList(),
								  Collections.emptyList(),
								  Collections.emptyList(),
								  Optional.empty(),
								  bonusPayTimeSheet.getTimeSheetId(),
								  bonusPayTimeSheet.getUseAtr(),
								  bonusPayTimeSheet.getTimeItemId(),
								  bonusPayTimeSheet.getStartTime(),
								  bonusPayTimeSheet.getEndTime(),
								  bonusPayTimeSheet.getRoundingTimeAtr(),
								  bonusPayTimeSheet.getRoundingAtr());
	}
	
	
	public static BonusPayForCalc createFromJavaType(int timeSheetId, int useAtr, String timeItemId, int startTime,
			int endTime, int roundingTimeAtr, int roundingAtr) {
		return new BonusPayForCalc(
				new TimeSpanWithRounding(new TimeWithDayAttr(startTime),new TimeWithDayAttr(endTime),
										 Finally.of(new TimeRoundingSetting(EnumAdaptor.valueOf(roundingTimeAtr, Unit.class),EnumAdaptor.valueOf(roundingAtr, Rounding.class)))),
				new TimeSpanForCalc(new TimeWithDayAttr(startTime),new TimeWithDayAttr(endTime)),
				Collections.emptyList(),
				Collections.emptyList(),
				Collections.emptyList(),
				Optional.empty(),
				timeSheetId, EnumAdaptor.valueOf(useAtr, UseAtr.class), timeItemId,
				new AttendanceClock(startTime), new AttendanceClock(endTime),
				EnumAdaptor.valueOf(roundingTimeAtr, UnitAtr.class),
				EnumAdaptor.valueOf(roundingAtr, RoundingAtr.class));
	}
	
//                           -----------------origin---------------
//	public static BonusPayTimesheet createFromJavaType(int timeSheetId, int useAtr, String timeItemId, int startTime,
//			int endTime, int roundingTimeAtr, int roundingAtr) {
//		return new BonusPayTimesheet(timeSheetId, EnumAdaptor.valueOf(useAtr, UseAtr.class), timeItemId,
//				new BonusPayTime(startTime), new BonusPayTime(endTime),
//				EnumAdaptor.valueOf(roundingTimeAtr, UnitAtr.class),
//				EnumAdaptor.valueOf(roundingAtr, RoundingAtr.class));
//	}
//	
	
	
	
	/**
	 * 開始と終了時刻を入れ替え作り直す
	 * @return 加給時間帯クラス
	 */
	public BonusPayForCalc reCreateCalcRange(TimeSpanForCalc newRange) {
		return new BonusPayForCalc(
									new TimeSpanWithRounding(newRange.getStart(), newRange.getEnd(), this.timeSheet.getRounding()),
									this.calcrange,
									this.deductionTimeSheet,
									this.bonusPayTimeSheet,
									this.specBonusPayTimesheet,
									this.midNightTimeSheet,
									this.timeSheetId,
									this.useAtr,
									this.timeItemId,
									this.startTime,
									this.endTime,
									this.roundingTimeAtr,
									this.roundingAtr);
	}
	
	/**
	 * 終了時間と基準時間の早い方の時間を取得する
	 * @param basePoint　基準時間
	 * @return 時刻が早い方
	 */
	public AttendanceClock decisionNewEndPoint(AttendanceClock baseTime) {
		return (this.endTime.greaterThan(baseTime))?this.endTime:baseTime;
	}
	
	
	
	/**
	 * 指定時間を内包しているか判定する
	 * @param 指定時間
	 * @return 内包している
	 */
	public boolean contains(TimeWithDayAttr baseTime) {
		return this.startTime.lessThan(baseTime) && this.endTime.greaterThan(baseTime);
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
	public BonusPayForCalc reCreateOwn(TimeWithDayAttr baseTime,boolean isDateBefore) {
			List<TimeSheetOfDeductionItem> deductionTimeSheets = this.recreateDeductionItemBeforeBase(baseTime,isDateBefore);
			List<BonusPayTimesheet>        bonusPayTimeSheet = this.recreateBonusPayListBeforeBase(baseTime,isDateBefore);
			Optional<MidNightTimeSheet>    midNighttimeSheet = this.recreateMidNightTimeSheetBeforeBase(baseTime,isDateBefore);
			TimeSpanForCalc renewSpan = decisionNewSpan(this.calcrange,baseTime,isDateBefore);
			
			return new BonusPayForCalc(new TimeSpanWithRounding(renewSpan.getStart(), renewSpan.getEnd(), this.timeSheet.getRounding()),
										 renewSpan,
										 deductionTimeSheets,
										 bonusPayTimeSheet,
										 this.specBonusPayTimesheet,
										 midNighttimeSheet,
										 this.timeSheetId,
										 this.useAtr,
										 this.timeItemId,
										 this.startTime,
										 this.endTime,
										 this.roundingTimeAtr,
										 this.roundingAtr);
			//return new BonusPayTimesheet(this.timeSheetId,this.useAtr,this.timeItemId,this.startTime,newEnd,this.roundingTimeAtr,this.roundingAtr);
	}
}
