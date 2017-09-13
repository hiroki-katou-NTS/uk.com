package nts.uk.ctx.at.record.dom.dailyprocess.calc.record.mekestimesheet;

import lombok.val;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.LeaveEarlyDecisionClock;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
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
	public static LateTimeSheet of(TimeWithDayAttr goWorkTime, WorkTime workTime,WorkTimeClassification classification) {
		
//		val lateTimeSheet = workTime.getLateTimeCalcRange(goWorkTime);
//			
//		if(goWorkTime.greaterThan(lateTimeSheet.getStart())) {
//			return new LateTimeSheet(lateTimeSheet.getStart(),goWorkTime);
//		}
//		else {
//			return new LateTimeSheet(lateTimeSheet.getStart(),lateTimeSheet.getEnd());
//		}

		
		//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
		if(classification.getDailyWorkClassification().equals(DailyWorkClassification.NormalWork)) {
			
		}else {
			/* フレックス勤務の場合の処理を記載する事 */
		}
			
		//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
		
		
	}
	
//	public void calcLateTime(計上控除区分) {
//	}
	
}
