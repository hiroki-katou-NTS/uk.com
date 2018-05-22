package nts.uk.ctx.at.record.dom.weekly;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 週別の通常変形時間
 * @author shuichu_ishida
 */
@Getter
public class RegAndIrgTimeOfWeekly implements Cloneable {

	/** 週割増合計時間 */
	private AttendanceTimeMonth weeklyTotalPremiumTime;
	
	/**
	 * コンストラクタ
	 */
	public RegAndIrgTimeOfWeekly(){
		
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param weeklyTotalPremiumTime 週割増合計時間
	 * @return 週別の通常変形時間
	 */
	public static RegAndIrgTimeOfWeekly of(
			AttendanceTimeMonth weeklyTotalPremiumTime){

		RegAndIrgTimeOfWeekly domain = new RegAndIrgTimeOfWeekly();
		domain.weeklyTotalPremiumTime = weeklyTotalPremiumTime;
		return domain;
	}
	
	@Override
	public RegAndIrgTimeOfWeekly clone() {
		RegAndIrgTimeOfWeekly cloned = new RegAndIrgTimeOfWeekly();
		try {
			cloned.weeklyTotalPremiumTime = new AttendanceTimeMonth(this.weeklyTotalPremiumTime.v());
		}
		catch (Exception e){
			throw new RuntimeException("RegAndIrgTimeOfWeekly clone error.");
		}
		return cloned;
	}
}
