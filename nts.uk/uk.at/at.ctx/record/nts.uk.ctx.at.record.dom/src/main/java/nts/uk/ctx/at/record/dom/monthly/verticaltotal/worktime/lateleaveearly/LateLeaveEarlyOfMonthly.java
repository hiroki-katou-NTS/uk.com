package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.lateleaveearly;

import lombok.Getter;
import lombok.val;

/**
 * 月別実績の遅刻早退
 * @author shuichu_ishida
 */
@Getter
public class LateLeaveEarlyOfMonthly {

	/** 早退 */
	private LeaveEarly leaveEarly;
	/** 遅刻 */
	private Late late;
	
	/**
	 * コンストラクタ
	 */
	public LateLeaveEarlyOfMonthly(){
		
		this.leaveEarly = new LeaveEarly();
		this.late = new Late();
	}
	
	/**
	 * ファクトリー
	 * @param leaveEarly 早退
	 * @param late 遅刻
	 * @return 月別実績の遅刻早退
	 */
	public static LateLeaveEarlyOfMonthly of(LeaveEarly leaveEarly, Late late){
		
		val domain = new LateLeaveEarlyOfMonthly();
		domain.leaveEarly = leaveEarly;
		domain.late = late;
		return domain;
	}
}
