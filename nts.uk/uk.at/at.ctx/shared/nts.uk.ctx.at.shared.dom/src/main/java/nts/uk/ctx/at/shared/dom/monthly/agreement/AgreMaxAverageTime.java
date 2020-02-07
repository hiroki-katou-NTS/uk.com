package nts.uk.ctx.at.shared.dom.monthly.agreement;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 36協定上限各月平均時間
 * @author shuichi_ishida
 */
@Getter
public class AgreMaxAverageTime implements Cloneable {

	/** 期間 */
	private YearMonthPeriod period;
	/** 合計時間 */
	private AttendanceTimeYear totalTime;
	/** 平均時間 */
	private AttendanceTimeMonth averageTime;
	/** 状態 */
	private AgreMaxTimeStatusOfMonthly status;
	
	/**
	 * コンストラクタ
	 */
	public AgreMaxAverageTime() {
		YearMonth nowYm = YearMonth.of(GeneralDate.today().year(), GeneralDate.today().month());
		this.period = new YearMonthPeriod(nowYm, nowYm);
		this.totalTime = new AttendanceTimeYear(0);
		this.averageTime = new AttendanceTimeMonth(0);
		this.status = AgreMaxTimeStatusOfMonthly.NORMAL;
	}
	
	/**
	 * ファクトリー
	 * @param period 期間
	 * @param totalTime 合計時間
	 * @param status 状態
	 * @return 36協定上限各月平均時間
	 */
	public static AgreMaxAverageTime of(
			YearMonthPeriod period,
			AttendanceTimeYear totalTime,
			AgreMaxTimeStatusOfMonthly status) {
		
		AgreMaxAverageTime domain = new AgreMaxAverageTime();
		domain.period = period;
		domain.totalTime = totalTime;
		domain.status = status;
		
		// 平均時間を計算
		domain.calcAverageTime();
		return domain;
	}
	
	@Override
	public AgreMaxAverageTime clone() {
		AgreMaxAverageTime cloned = new AgreMaxAverageTime();
		try {
			cloned.period = new YearMonthPeriod(this.period.start(), this.period.end());
			cloned.totalTime = new AttendanceTimeYear(this.totalTime.v());
			cloned.averageTime = new AttendanceTimeMonth(this.averageTime.v());
			cloned.status = this.status;
		}
		catch (Exception e){
			throw new RuntimeException("AgreMaxAverageTime clone error.");
		}
		return cloned;
	}
	
	/**
	 * 平均時間を計算
	 */
	private void calcAverageTime(){
		
		if (this.period == null) {
			this.averageTime = new AttendanceTimeMonth(0);
			return;
		}
		if (this.totalTime == null) {
			this.averageTime = new AttendanceTimeMonth(0);
			return;
		}
		Integer monthNum = this.period.yearMonthsBetween().size();	// 期間の月数
		Double averageMinutes = 0.0;								// 平均時間（分）
		if (monthNum > 0) {
			Double totalMinutes = this.totalTime.v().doubleValue();	// 合計時間
			if (totalMinutes >= 0.0) {
				averageMinutes = Math.ceil(totalMinutes / monthNum.doubleValue());
			}
			else {
				averageMinutes = -Math.ceil(-totalMinutes / monthNum.doubleValue());
			}
		}
		this.averageTime = new AttendanceTimeMonth(averageMinutes.intValue());
	}
	
	/**
	 * エラーチェック
	 * @param limitTime 上限時間
	 */
	public void errorCheck(LimitOneMonth limitTime){
		this.status = AgreMaxTimeStatusOfMonthly.NORMAL;
		if (limitTime.v() <= 0) return;
		if (this.averageTime.v() > limitTime.v()) {
			this.status = AgreMaxTimeStatusOfMonthly.EXCESS_MAXTIME;
		}
	}
}
