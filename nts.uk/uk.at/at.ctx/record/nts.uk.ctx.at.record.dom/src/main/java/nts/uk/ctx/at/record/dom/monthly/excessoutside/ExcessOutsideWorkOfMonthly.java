package nts.uk.ctx.at.record.dom.monthly.excessoutside;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の時間外超過
 * @author shuichu_ishida
 */
@Getter
public class ExcessOutsideWorkOfMonthly {

	/** 週割増合計時間 */
	private AttendanceTimeMonth weeklyTotalPremiumTime;
	/** 月割増合計時間 */
	private AttendanceTimeMonth monthlyTotalPremiumTime;
	/** 変形繰越時間 */
	private AttendanceTimeMonth deformationCarryforwardTime;
	/** 時間 */
	private Map<Integer, ExcessOutsideWork> time;
	
	/**
	 * コンストラクタ
	 */
	public ExcessOutsideWorkOfMonthly(){
		
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.monthlyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.deformationCarryforwardTime = new AttendanceTimeMonth(0);
		this.time = new HashMap<>();
	}
}
