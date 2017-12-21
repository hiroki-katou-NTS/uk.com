/**
 * 9:19:40 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.setting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.RoundingAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UnitAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.AttendanceClock;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author hungnm
 *
 */
@Getter
public class BonusPayTimesheet {

	private int timeSheetId;

	private UseAtr useAtr;

	private String timeItemId;

	private AttendanceClock startTime;

	private AttendanceClock endTime;

	private UnitAtr roundingTimeAtr;

	private RoundingAtr roundingAtr;

	public BonusPayTimesheet(
			int timeSheetId, UseAtr useAtr, String timeItemId,
			AttendanceClock startTime, AttendanceClock endTime, UnitAtr roundingTimeAtr,
			RoundingAtr roundingAtr) {
		this.timeSheetId = timeSheetId;
		this.useAtr = useAtr;
		this.timeItemId = timeItemId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roundingTimeAtr = roundingTimeAtr;
		this.roundingAtr = roundingAtr;
	}

//	protected BonusPayTimesheet() {
//		super();
//	}

	public static BonusPayTimesheet createFromJavaType(int timeSheetId, int useAtr, String timeItemId, int startTime,
			int endTime, int roundingTimeAtr, int roundingAtr) {
		return new BonusPayTimesheet(
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
	public BonusPayTimesheet reCreateCalcRange(TimeSpanForCalc newRange) {
		return new BonusPayTimesheet(
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
	public BonusPayTimesheet reCreateOwn(TimeWithDayAttr baseTime,boolean isDateBefore) {
//			List<TimeSheetOfDeductionItem> deductionTimeSheets = this.recreateDeductionItemBeforeBase(baseTime,isDateBefore);
//			List<BonusPayTimesheet>        bonusPayTimeSheet = this.recreateBonusPayListBeforeBase(baseTime,isDateBefore);
//			Optional<MidNightTimeSheet>    midNighttimeSheet = this.recreateMidNightTimeSheetBeforeBase(baseTime,isDateBefore);
//			TimeSpanForCalc renewSpan = decisionNewSpan(this.calcrange,baseTime,isDateBefore);
			
			return new BonusPayTimesheet(
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
