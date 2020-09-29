package nts.uk.ctx.at.record.pub.monthly.agreement;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;

/**
 * 指定期間36協定時間
 * @author shuichu_ishida
 */
@Getter
public class AgreementTimeByPeriod {

	/** 開始月度 */
	private YearMonth startMonth;
	/** 終了月度 */
	private YearMonth endMonth;
	/** 36協定時間 */
	private AttendanceTimeYear agreementTime;
	/** 限度エラー時間 */
	private AgreementOneYearTime limitErrorTime;
	/** 限度アラーム時間 */
	private AgreementOneYearTime limitAlarmTime;
	/** 特例限度エラー時間 */
	private Optional<AgreementOneYearTime> exceptionLimitErrorTime;
	/** 特例限度アラーム時間 */
	private Optional<AgreementOneYearTime> exceptionLimitAlarmTime;
	/** 状態 */
	private AgreementTimeStatusOfMonthly status;
	
	/**
	 * コンストラクタ
	 * @param startMonth 開始月度
	 * @param endMonth 終了月度
	 */
	public AgreementTimeByPeriod(YearMonth startMonth, YearMonth endMonth){
		
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.agreementTime = new AttendanceTimeYear(0);
		this.limitErrorTime = new AgreementOneYearTime(0);
		this.limitAlarmTime = new AgreementOneYearTime(0);
		this.exceptionLimitErrorTime = Optional.empty();
		this.exceptionLimitAlarmTime = Optional.empty();
		this.status = AgreementTimeStatusOfMonthly.NORMAL;
	}

	/**
	 * ファクトリー
	 * @param startMonth 開始月度
	 * @param endMonth 終了月度
	 * @param agreementTime 36協定時間
	 * @param limitErrorTime 限度エラー時間
	 * @param limitAlarmTime 限度アラーム時間
	 * @param exceptionLimitErrorTime 特例限度エラー時間
	 * @param exceptionLimitAlarmTime 特例限度アラーム時間
	 * @param status 状態
	 * @return 指定期間36協定時間
	 */
	public static AgreementTimeByPeriod of(
			YearMonth startMonth,
			YearMonth endMonth,
			AttendanceTimeYear agreementTime,
			AgreementOneYearTime limitErrorTime,
			AgreementOneYearTime limitAlarmTime,
			Optional<AgreementOneYearTime> exceptionLimitErrorTime,
			Optional<AgreementOneYearTime> exceptionLimitAlarmTime,
			AgreementTimeStatusOfMonthly status){
		
		AgreementTimeByPeriod domain = new AgreementTimeByPeriod(startMonth, endMonth);
		domain.agreementTime = agreementTime;
		domain.limitErrorTime = limitErrorTime;
		domain.limitAlarmTime = limitAlarmTime;
		domain.exceptionLimitErrorTime = exceptionLimitErrorTime;
		domain.exceptionLimitAlarmTime = exceptionLimitAlarmTime;
		domain.status = status;
		return domain;
	}
}
