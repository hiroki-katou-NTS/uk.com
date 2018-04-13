package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;

/**
 * 年休上限残時間
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AnnualLeaveMaxRemainingTime {

	/** 時間 */
	private RemainingMinutes time;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveMaxRemainingTime(){
		
		this.time = new RemainingMinutes(0);
	}
	
	/**
	 * ファクトリー
	 * @param time 時間
	 * @return 年休上限残時間
	 */
	public static AnnualLeaveMaxRemainingTime of(RemainingMinutes time){
		
		AnnualLeaveMaxRemainingTime domain = new AnnualLeaveMaxRemainingTime();
		domain.time = time;
		return domain;
	}
}
