package nts.uk.ctx.at.record.dom.weekly;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.byperiod.FlexTimeByPeriod;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;

/**
 * 週別の計算
 * @author shuichi_ishida
 */
@Getter
public class WeeklyCalculation implements Cloneable {

	/** 通常変形時間 */
	private RegAndIrgTimeOfWeekly regAndIrgTime;
	/** フレックス時間 */
	private FlexTimeByPeriod flexTime;
	/** 総労働時間 */
	private AggregateTotalWorkingTime totalWorkingTime;
	/** 総拘束時間 */
	private AggregateTotalTimeSpentAtWork totalSpentTime;
	/** 36協定時間 */
	private AgreementTimeOfWeekly agreementTime;

	/**
	 * コンストラクタ
	 */
	public WeeklyCalculation(){

		this.regAndIrgTime = new RegAndIrgTimeOfWeekly();
		this.flexTime = new FlexTimeByPeriod();
		this.totalWorkingTime = new AggregateTotalWorkingTime();
		this.totalSpentTime = new AggregateTotalTimeSpentAtWork();
		this.agreementTime = new AgreementTimeOfWeekly();
	}

	/**
	 * ファクトリー
	 * @param regAndIrgTime 通常変形時間
	 * @param flexTime フレックス時間
	 * @param totalWorkingTime 総労働時間
	 * @param totalSpentTime 総拘束時間
	 * @param agreementTime 36協定時間
	 * @return 月別実績の月の計算
	 */
	public static WeeklyCalculation of(
			RegAndIrgTimeOfWeekly regAndIrgTime,
			FlexTimeByPeriod flexTime,
			AggregateTotalWorkingTime totalWorkingTime,
			AggregateTotalTimeSpentAtWork totalSpentTime,
			AgreementTimeOfWeekly agreementTime){
		
		WeeklyCalculation domain = new WeeklyCalculation();
		domain.regAndIrgTime = regAndIrgTime;
		domain.flexTime = flexTime;
		domain.totalWorkingTime = totalWorkingTime;
		domain.totalSpentTime = totalSpentTime;
		domain.agreementTime = agreementTime;
		return domain;
	}
	
	@Override
	public WeeklyCalculation clone() {
		WeeklyCalculation cloned = new WeeklyCalculation();
		try {
			cloned.regAndIrgTime = this.regAndIrgTime.clone();
			cloned.flexTime = this.flexTime.clone();
			cloned.totalWorkingTime = this.totalWorkingTime.clone();
			cloned.totalSpentTime = this.totalSpentTime.clone();
			cloned.agreementTime = this.agreementTime.clone();
		}
		catch (Exception e){
			throw new RuntimeException("WeeklyCalculation clone error.");
		}
		return cloned;
	}
}
