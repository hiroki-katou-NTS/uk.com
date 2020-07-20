package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * refactor 4
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.短時間勤務.短時間勤務時間帯
 * @author Doan Duy Hung
 *
 */
@Getter
public class ShortWorkTime {
	
	/**
	 * 短時間勤務枠NO
	 */
	private ShortWorkTimeFrameNo shortWorkTimeFrameNo;
	
	/**
	 * 終了
	 */
	private TimeWithDayAttr end;
	
	/**
	 * 育児介護区分
	 */
	private ChildCareAtr childCareAtr;
	
	/**
	 * 開始
	 */
	private TimeWithDayAttr start;
	
}
