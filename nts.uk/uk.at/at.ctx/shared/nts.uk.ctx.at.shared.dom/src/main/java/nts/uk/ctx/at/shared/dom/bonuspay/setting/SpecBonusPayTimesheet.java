/**
 * 9:20:03 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.setting;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.RoundingAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UnitAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.SpecBonusPayNumber;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.AttendanceClock;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author hungnm
 * 特定日加給時間帯
 *
 */
@Getter
public class SpecBonusPayTimesheet {

	private int timeSheetId;

	private UseAtr useAtr;

	private String timeItemId;

	private AttendanceClock startTime;

	private AttendanceClock endTime;

	private UnitAtr roundingTimeAtr;

	private RoundingAtr roundingAtr;
	
	private int dateCode;
	
	private SpecBonusPayNumber specBonusPayNumber;

	private SpecBonusPayTimesheet(
			int timeSheetId, UseAtr useAtr, String timeItemId,
			AttendanceClock startTime, AttendanceClock endTime, UnitAtr roundingTimeAtr,
			RoundingAtr roundingAtr, int dateCode,SpecBonusPayNumber specBonusPayNumber) {
		this.timeSheetId = timeSheetId;
		this.useAtr = useAtr;
		this.timeItemId = timeItemId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roundingTimeAtr = roundingTimeAtr;
		this.roundingAtr = roundingAtr;
		this.dateCode = dateCode;
		this.specBonusPayNumber = specBonusPayNumber;
	}

	public static SpecBonusPayTimesheet createFromJavaType(int timeSheetId, int useAtr, String timeItemId,
			int startTime, int endTime, int roundingTimeAtr, int roundingAtr, int dateCode) {
		return new SpecBonusPayTimesheet(
				timeSheetId, EnumAdaptor.valueOf(useAtr, UseAtr.class),
				timeItemId, new AttendanceClock(startTime),
				new AttendanceClock(endTime), EnumAdaptor.valueOf(roundingTimeAtr, UnitAtr.class),
				EnumAdaptor.valueOf(roundingAtr, RoundingAtr.class), dateCode,new SpecBonusPayNumber(BigDecimal.ONE));
	}
	
	
	/**
	 * 開始と終了時刻を入れ替え作り直す
	 * @return
	 */
	public SpecBonusPayTimesheet reCreateCalcRange(TimeSpanForCalc newRange){
		return new SpecBonusPayTimesheet(
									 this.timeSheetId,
									 this.useAtr,
									 this.timeItemId,
									 new AttendanceClock(newRange.getStart().valueAsMinutes()),
									 new AttendanceClock(newRange.getEnd().valueAsMinutes()),
									 this.roundingTimeAtr,
									 this.roundingAtr,
									 this.dateCode,
									 this.specBonusPayNumber);//new BonusPayTimesheet(timeSheetId,useAtr,timeItemId,startTime,endTime,roundingTimeAtr,roundingAtr);
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
	public SpecBonusPayTimesheet reCreateOwn(TimeWithDayAttr baseTime,boolean isDateBefore) {
//			List<TimeSheetOfDeductionItem> deductionTimeSheets = this.recreateDeductionItemBeforeBase(baseTime,isDateBefore);
//			List<BonusPayTimesheet>        bonusPayTimeSheet  = this.recreateBonusPayListBeforeBase(baseTime,isDateBefore);
//			List<SpecBonusPayTimesheet>    specBonusTimeSheet = this.recreateSpecifiedBonusPayListBeforeBase(baseTime, isDateBefore);
//			Optional<MidNightTimeSheet>    midNighttimeSheet  = this.recreateMidNightTimeSheetBeforeBase(baseTime,isDateBefore);
//			TimeSpanForCalc renewSpan = decisionNewSpan(this.calcrange,baseTime,isDateBefore);
			
			return new SpecBonusPayTimesheet(//new TimeSpanWithRounding(renewSpan.getStart(), renewSpan.getEnd(), this.timeSheet.getRounding()),
//											 renewSpan,
//											 deductionTimeSheets,
//											 bonusPayTimeSheet,
//											 specBonusTimeSheet,
//											 midNighttimeSheet,
											 this.timeSheetId,
											 this.useAtr,
											 this.timeItemId,
											 this.getStartTime(),
//											 new AttendanceClock(renewSpan.getStart().valueAsMinutes()),
											 this.getEndTime(),
//											 new AttendanceClock(renewSpan.getEnd().valueAsMinutes()),
											 this.roundingTimeAtr,
											 this.roundingAtr,
											 this.dateCode,
											 this.specBonusPayNumber
											 );
	}

}
