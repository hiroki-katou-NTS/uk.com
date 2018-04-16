package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;

/**
 * 年休残明細
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveRemainingDetail {

	/** 日数 */
	private AnnualLeaveRemainingDayNumber days;
	/** 時間 */
	private AnnualLeaveRemainingTime time;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveRemainingDetail(){
		
		this.days = new AnnualLeaveRemainingDayNumber(0.0);
		this.time = new AnnualLeaveRemainingTime(0);
	}
	
	/**
	 * ファクトリー
	 * @param days 日数
	 * @param time 時間
	 * @return 年休残明細
	 */
	public static AnnualLeaveRemainingDetail of(
			AnnualLeaveRemainingDayNumber days,
			AnnualLeaveRemainingTime time){
		
		AnnualLeaveRemainingDetail domain = new AnnualLeaveRemainingDetail();
		domain.days = days;
		domain.time = time;
		return domain;
	}
}
