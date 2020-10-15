package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.excessoutside;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;

/**
 * 基準フレックス時間
 * @author shuichi_ishida
 */
@Getter
public class StandardFlexTime {

	/** 清算基準時間 */
	private AttendanceTimeYear settleStandTime;
	/** 清算法定時間 */
	private AttendanceTimeYear settleStatTime;
	/** 前月基準時間 */
	private AttendanceTimeYear prevStandTime;
	/** 前月累計時間 */
	private AttendanceTimeYear prevSumTime;
	/** 当月週平均時間 */
	@Setter
	private AttendanceTimeMonth currWeekAveTime;
	
	/**
	 * コンストラクタ
	 */
	public StandardFlexTime(){
		
		this.settleStandTime = new AttendanceTimeYear(0);
		this.settleStatTime = new AttendanceTimeYear(0);
		this.prevStandTime = new AttendanceTimeYear(0);
		this.prevSumTime = new AttendanceTimeYear(0);
		this.currWeekAveTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param settleStandTime 清算基準時間
	 * @param settleStatTime 清算法定時間
	 * @param prevStandTime 前月基準時間
	 * @param prevSumTime 前月累計時間
	 * @param currWeekAveTime 当月週平均時間
	 * @return 基準フレックス時間
	 */
	public static StandardFlexTime of(
			AttendanceTimeYear settleStandTime,
			AttendanceTimeYear settleStatTime,
			AttendanceTimeYear prevStandTime,
			AttendanceTimeYear prevSumTime,
			AttendanceTimeMonth currWeekAveTime){
		
		StandardFlexTime domain = new StandardFlexTime();
		domain.settleStandTime = settleStandTime;
		domain.settleStatTime = settleStatTime;
		domain.prevStandTime = prevStandTime;
		domain.prevSumTime = prevSumTime;
		domain.currWeekAveTime = currWeekAveTime;
		return domain;
	}
	
	/**
	 * 清算基準時間に加算する
	 * @param minutes 分
	 */
	public void addSettleStandTime(int minutes){
		this.settleStandTime = this.settleStandTime.addMinutes(minutes);
	}
	
	/**
	 * 清算法定時間に加算する
	 * @param minutes 分
	 */
	public void addSettleStatTime(int minutes){
		this.settleStatTime = this.settleStatTime.addMinutes(minutes);
	}
	
	/**
	 * 前月基準時間に加算する
	 * @param minutes 分
	 */
	public void addPrevStandTime(int minutes){
		this.prevStandTime = this.prevStandTime.addMinutes(minutes);
	}
	
	/**
	 * 前月累計時間に加算する
	 * @param minutes 分
	 */
	public void addPrevSumtime(int minutes){
		this.prevSumTime = this.prevSumTime.addMinutes(minutes);
	}
}
