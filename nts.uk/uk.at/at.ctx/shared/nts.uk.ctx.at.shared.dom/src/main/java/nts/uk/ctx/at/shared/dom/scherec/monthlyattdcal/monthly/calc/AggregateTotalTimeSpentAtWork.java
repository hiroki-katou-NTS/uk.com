package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 期間別の総拘束時間
 * @author shuichi_ishida
 */
@Getter
public class AggregateTotalTimeSpentAtWork implements Cloneable, Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 拘束残業時間 */
	private AttendanceTimeMonth overTimeSpentAtWork;
	/** 拘束深夜時間 */
	private AttendanceTimeMonth midnightTimeSpentAtWork;
	/** 拘束休出時間 */
	private AttendanceTimeMonth holidayTimeSpentAtWork;
	/** 拘束差異時間 */
	private AttendanceTimeMonth varienceTimeSpentAtWork;
	/** 総拘束時間 */
	private AttendanceTimeMonth totalTimeSpentAtWork;

	/**
	 * コンストラクタ
	 */
	public AggregateTotalTimeSpentAtWork(){
		
		this.overTimeSpentAtWork = new AttendanceTimeMonth(0);
		this.midnightTimeSpentAtWork = new AttendanceTimeMonth(0);
		this.holidayTimeSpentAtWork = new AttendanceTimeMonth(0);
		this.varienceTimeSpentAtWork = new AttendanceTimeMonth(0);
		this.totalTimeSpentAtWork = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param overTimeSpentAtWork 拘束残業時間
	 * @param midnightTimeSpentAtWork 拘束深夜時間
	 * @param holidayTimeSpentAtWork 拘束休出時間
	 * @param varienceTimeSpentAtWork 拘束差異時間
	 * @param totalTimeSpentAtWork 総拘束時間
	 * @return 集計総拘束時間
	 */
	public static AggregateTotalTimeSpentAtWork of(
			AttendanceTimeMonth overTimeSpentAtWork,
			AttendanceTimeMonth midnightTimeSpentAtWork,
			AttendanceTimeMonth holidayTimeSpentAtWork,
			AttendanceTimeMonth varienceTimeSpentAtWork,
			AttendanceTimeMonth totalTimeSpentAtWork){

		val domain = new AggregateTotalTimeSpentAtWork();
		domain.overTimeSpentAtWork = overTimeSpentAtWork;
		domain.midnightTimeSpentAtWork = midnightTimeSpentAtWork;
		domain.holidayTimeSpentAtWork = holidayTimeSpentAtWork;
		domain.varienceTimeSpentAtWork = varienceTimeSpentAtWork;
		domain.totalTimeSpentAtWork = totalTimeSpentAtWork;
		return domain;
	}
	
	@Override
	public AggregateTotalTimeSpentAtWork clone() {
		AggregateTotalTimeSpentAtWork cloned = new AggregateTotalTimeSpentAtWork();
		try {
			cloned.overTimeSpentAtWork = new AttendanceTimeMonth(this.overTimeSpentAtWork.v());
			cloned.midnightTimeSpentAtWork = new AttendanceTimeMonth(this.midnightTimeSpentAtWork.v());
			cloned.holidayTimeSpentAtWork = new AttendanceTimeMonth(this.holidayTimeSpentAtWork.v());
			cloned.varienceTimeSpentAtWork = new AttendanceTimeMonth(this.varienceTimeSpentAtWork.v());
			cloned.totalTimeSpentAtWork = new AttendanceTimeMonth(this.totalTimeSpentAtWork.v());
		}
		catch (Exception e){
			throw new RuntimeException("AggregateTotalTimeSpentAtWork clone error.");
		}
		return cloned;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AggregateTotalTimeSpentAtWork target){

		this.overTimeSpentAtWork = this.overTimeSpentAtWork.addMinutes(target.overTimeSpentAtWork.v());
		this.midnightTimeSpentAtWork = this.midnightTimeSpentAtWork.addMinutes(target.midnightTimeSpentAtWork.v());
		this.holidayTimeSpentAtWork = this.holidayTimeSpentAtWork.addMinutes(target.holidayTimeSpentAtWork.v());
		this.varienceTimeSpentAtWork = this.varienceTimeSpentAtWork.addMinutes(target.varienceTimeSpentAtWork.v());
		this.totalTimeSpentAtWork = this.totalTimeSpentAtWork.addMinutes(target.totalTimeSpentAtWork.v());
	}
}
