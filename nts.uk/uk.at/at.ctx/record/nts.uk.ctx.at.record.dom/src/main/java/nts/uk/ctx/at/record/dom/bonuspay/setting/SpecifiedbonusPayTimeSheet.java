package nts.uk.ctx.at.record.dom.bonuspay.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.AttendanceClock;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 特定日加給時間帯
 * @author keisuke_hoshina
 *
 */
@Value
public class SpecifiedbonusPayTimeSheet extends CalculationTimeSheet{
	private AttendanceClock start;
	private AttendanceClock end;
	private int specifiedItemNo;
	
	
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
			List<TimeSheetOfDeductionItem> deductionTimeSheets = this.recreateDeductionItemBeforeBase(baseTime,isDateBefore);
			List<BonusPayTimesheet>        bonusPayTimeSheet = this.recreateBonusPayListBeforeBase(baseTime,isDateBefore);
			Optional<MidNightTimeSheet>    midNighttimeSheet = this.recreateMidNightTimeSheetBeforeBase(baseTime,isDateBefore);
			TimeSpanForCalc renewSpan = decisionNewEndPoint(this.calculationTimeSheet,baseTime,isDateBefore);
			
			return new SpecifiedbonusPayTimeSheet();
			//return new BonusPayTimesheet(this.timeSheetId,this.useAtr,this.timeItemId,this.startTime,newEnd,this.roundingTimeAtr,this.roundingAtr);
	}
	

}
