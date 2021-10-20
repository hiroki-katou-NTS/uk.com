package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.divergencetime;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 集計乖離時間
 * @author shuichi_ishida
 */
@Getter
@NoArgsConstructor
public class AggregateDivergenceTime implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 乖離時間No */
	private int divergenceTimeNo;
	/** 乖離時間 */
	private AttendanceTimeMonth divergenceTime;
	/** 乖離フラグ */
	@Setter
	private DivergenceAtrOfMonthly divergenceAtr;

	/**
	 * コンストラクタ
	 */
	public AggregateDivergenceTime(int divergenceTimeNo){
		 
		this.divergenceTimeNo = divergenceTimeNo;
		this.divergenceTime = new AttendanceTimeMonth(0);
		this.divergenceAtr = DivergenceAtrOfMonthly.NORMAL;
	}
	
	/**
	 * ファクトリー
	 * @param divergenceTimeNo 乖離時間No
	 * @param divergenceTime 乖離時間
	 * @param deductionTime 控除時間
	 * @param divergenceTimeAfterDeduction 控除後乖離時間
	 * @param divergenceAtr 乖離フラグ
	 * @return 集計乖離時間
	 */
	public static AggregateDivergenceTime of(
			int divergenceTimeNo,
			AttendanceTimeMonth divergenceTime,
			DivergenceAtrOfMonthly divergenceAtr){
		
		val domain = new AggregateDivergenceTime(divergenceTimeNo);
		domain.divergenceTime = divergenceTime;
		domain.divergenceAtr = divergenceAtr;
		return domain;
	}

	
	/**
	 * 乖離時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToDivergenceTime(int minutes){
		this.divergenceTime = this.divergenceTime.addMinutes(minutes);
	}
}
