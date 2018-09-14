package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 集計乖離時間
 * @author shuichu_ishida
 */
@Getter
@NoArgsConstructor
public class AggregateDivergenceTime {

	/** 乖離時間No */
	private int divergenceTimeNo;
	/** 乖離時間 */
	private AttendanceTimeMonth divergenceTime;
	/** 控除時間 */
	private AttendanceTimeMonth deductionTime;
	/** 控除後乖離時間 */
	private AttendanceTimeMonth divergenceTimeAfterDeduction;
	/** 乖離フラグ */
	@Setter
	private DivergenceAtrOfMonthly divergenceAtr;

	/**
	 * コンストラクタ
	 */
	public AggregateDivergenceTime(int divergenceTimeNo){
		 
		this.divergenceTimeNo = divergenceTimeNo;
		this.divergenceTime = new AttendanceTimeMonth(0);
		this.deductionTime = new AttendanceTimeMonth(0);
		this.divergenceTimeAfterDeduction = new AttendanceTimeMonth(0);
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
			AttendanceTimeMonth deductionTime,
			AttendanceTimeMonth divergenceTimeAfterDeduction,
			DivergenceAtrOfMonthly divergenceAtr){
		
		val domain = new AggregateDivergenceTime(divergenceTimeNo);
		domain.divergenceTime = divergenceTime;
		domain.deductionTime = deductionTime;
		domain.divergenceTimeAfterDeduction = divergenceTimeAfterDeduction;
		domain.divergenceAtr = divergenceAtr;
		return domain;
	}
	/**
	 * for using table KRCDT_MON_MERGE
	 * @author lanlt
	 * @param divergenceTime
	 * @param deductionTime
	 * @param divergenceTimeAfterDeduction
	 * @param divergenceAtr
	 * @return
	 */
	public static AggregateDivergenceTime of(
			AttendanceTimeMonth divergenceTime,
			AttendanceTimeMonth deductionTime,
			AttendanceTimeMonth divergenceTimeAfterDeduction,
			DivergenceAtrOfMonthly divergenceAtr){
		
		val domain = new AggregateDivergenceTime();
		domain.divergenceTime = divergenceTime;
		domain.deductionTime = deductionTime;
		domain.divergenceTimeAfterDeduction = divergenceTimeAfterDeduction;
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
	
	/**
	 * 控除時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToDeductionTime(int minutes){
		this.deductionTime = this.deductionTime.addMinutes(minutes);
	}
	
	/**
	 * 控除後乖離時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToDivergenceTimeAfterDeduction(int minutes){
		this.divergenceTimeAfterDeduction = this.divergenceTimeAfterDeduction.addMinutes(minutes);
	}
}
