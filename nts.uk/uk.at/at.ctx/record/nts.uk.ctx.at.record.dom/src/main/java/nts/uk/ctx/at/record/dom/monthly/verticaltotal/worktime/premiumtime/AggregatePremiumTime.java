package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.premiumtime;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 集計割増時間
 * @author shuichu_ishida
 */
@Getter
public class AggregatePremiumTime {

	/** 割増時間項目No */
	private int premiumTimeItemNo;
	/** 時間 */
	private AttendanceTimeMonth time;
	
	/**
	 * コンストラクタ
	 */
	public AggregatePremiumTime(){
		
		this.premiumTimeItemNo = 0;
		this.time = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param premiumTimeItemNo 割増時間項目No
	 * @param time 時間
	 * @return 集計割増時間
	 */
	public static AggregatePremiumTime of(
			int premiumTimeItemNo,
			AttendanceTimeMonth time){
		
		val domain = new AggregatePremiumTime();
		domain.premiumTimeItemNo = premiumTimeItemNo;
		domain.time = time;
		return domain;
	}
}
