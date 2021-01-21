package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.FlexTime;

/**
 * 時系列のフレックス時間
 * @author shuichi_ishida
 */
@Getter
public class FlexTimeOfTimeSeries implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 年月日 */
	private final GeneralDate ymd;
	
	/** フレックス時間（日別実績） */
	@Setter
	private FlexTime flexTime;
	
	/**
	 * コンストラクタ
	 * @param ymd 年月日
	 */
	public FlexTimeOfTimeSeries(GeneralDate ymd){
		
		this.ymd = ymd;
		this.flexTime = new FlexTime(
				TimeDivergenceWithCalculationMinusExist.sameTime(new AttendanceTimeOfExistMinus(0)),
				new AttendanceTime(0));
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param flexTime フレックス時間（日別実績）
	 * @return 時系列のフレックス時間
	 */
	public static FlexTimeOfTimeSeries of(GeneralDate ymd, FlexTime flexTime){
		
		val domain = new FlexTimeOfTimeSeries(ymd);
		domain.flexTime = flexTime;
		return domain;
	}
	
	/**
	 * フレックス時間：フレックス時間：時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToFlexTimeInFlexTime(int minutes){
		this.flexTime = new FlexTime(
				TimeDivergenceWithCalculationMinusExist.createTimeWithCalculation(
						this.flexTime.getFlexTime().getTime().addMinutes(minutes),
						this.flexTime.getFlexTime().getCalcTime()),
				this.flexTime.getBeforeApplicationTime());
	}
}
