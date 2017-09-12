package nts.uk.ctx.at.record.dom.dailyprocess.calc.record.mekestimesheet;

import lombok.val;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.LeaveEarlyDecisionClock;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.SpecifiedTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.basicinformation.DailyWorkClassification;
import nts.uk.ctx.at.shared.dom.worktime.basicinformation.WorkTimeClassification;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 遅刻時間帯
 * @author keisuke_hoshina
 *
 */
public class LateTimeSheet {
	
	private TimeWithDayAttr start;
	private TimeWithDayAttr end;

	private LateTimeSheet(TimeWithDayAttr start, TimeWithDayAttr end) {
		this.start = start;
		this.end   = end;
	}
	
	/**
	 * @param goWorkTime 出勤時間
	 * @param workTime   就業時間帯クラス
	 * @return 計算用時間帯：遅刻時間帯
	 */
//	public static LateTimeSheet of(TimeWithDayAttr goWorkTime, WorkTime workTime){
//		val lateTimeSheet = workTime.getLateTimeCalcRange(goWorkTime);
//			
//		if(goWorkTime.greaterThan(lateTimeSheet.getStart())) {
//			return new LateTimeSheet(lateTimeSheet.getStart(),goWorkTime);
//		}
//		else {
//			return new LateTimeSheet(lateTimeSheet.getStart(),lateTimeSheet.getEnd());
//		}
//	}
	public static LateTimeSheet of(SpecifiedTimeSheetSetting specifiedTimeSheet,TimeWithDayAttr goWorkTime,int workNo/*,WorkTimeClassification classification,控除区分*/) {
		
		//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
		if(classification.getDailyWorkClassification().equals(DailyWorkClassification.NormalWork)) {
			//計算範囲の取得
			TimeSpanForCalc calcRange = new TimeSpanForCalc(specifiedTimeSheet.getMatchWorkNoTimeSheet(workNo).getStartTime(), goWorkTime);	
		}else {
			/* フレックス勤務の場合の計算範囲 */			
		}
		TimeWithDayAttr lateStart = calcRange.getStart();
		if(goWorkTime.lessThanOrEqualTo(calcRange.getEnd())) {//出勤時刻＜＝計算範囲の終了の場合
			TimeWithDayAttr lateEnd = goWorkTime;				
		}else {//出勤時刻＞計算範囲の終了の場合
			TimeWithDayAttr lateEnd = calcRange.getEnd();
		}

		val lateTime = calcRange.lengthAsMinutes();
		
		//控除時間の計算
		
		//遅刻時間から控除時間を控除する
		
		//丸め処理
				
		return new LateTimeSheet(lateStart,lateEnd);				
		
		//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊	
	}

	
//	public void calcLateTime(計上控除区分) {
//	}
	
}
