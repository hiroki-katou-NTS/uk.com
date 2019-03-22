package nts.uk.ctx.at.shared.dom.monthly.agreement;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

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
	 * @param averageTime 平均時間
	 * @param status 状態
	 * @return 36協定上限各月平均時間
	 */
	public static AgreMaxAverageTime of(
			YearMonthPeriod period,
			AttendanceTimeYear totalTime,
			AttendanceTimeMonth averageTime,
			AgreMaxTimeStatusOfMonthly status) {
		
		AgreMaxAverageTime domain = new AgreMaxAverageTime();
		domain.period = period;
		domain.totalTime = totalTime;
		domain.averageTime = averageTime;
		domain.status = status;
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
	 * エラーチェック
	 * @param limitTime 上限時間
	 */
	public void errorCheck(LimitOneMonth limitTime){
		this.status = AgreMaxTimeStatusOfMonthly.NORMAL;
		if (this.averageTime.v() > limitTime.v()) {
			this.status = AgreMaxTimeStatusOfMonthly.EXCESS_MAXTIME;
		}
	}
}
