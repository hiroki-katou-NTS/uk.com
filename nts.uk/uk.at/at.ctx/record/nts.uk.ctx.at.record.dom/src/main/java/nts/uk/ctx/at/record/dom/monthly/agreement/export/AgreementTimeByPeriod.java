package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
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
	@Setter
	private AgreementOneYearTime limitErrorTime;
	/** 限度アラーム時間 */
	@Setter
	private AgreementOneYearTime limitAlarmTime;
	/** 特例限度エラー時間 */
	@Setter
	private Optional<AgreementOneYearTime> exceptionLimitErrorTime;
	/** 特例限度アラーム時間 */
	@Setter
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
			AgreementOneYearTime exceptionLimitErrorTime,
			AgreementOneYearTime exceptionLimitAlarmTime,
			AgreementTimeStatusOfMonthly status){
		
		AgreementTimeByPeriod domain = new AgreementTimeByPeriod(startMonth, endMonth);
		domain.agreementTime = agreementTime;
		domain.limitErrorTime = limitErrorTime;
		domain.limitAlarmTime = limitAlarmTime;
		domain.exceptionLimitErrorTime = Optional.ofNullable(exceptionLimitErrorTime);
		domain.exceptionLimitAlarmTime = Optional.ofNullable(exceptionLimitAlarmTime);
		domain.status = status;
		return domain;
	}
	
	/**
	 * 36協定時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToAgreementTime(int minutes){
		this.agreementTime = this.agreementTime.addMinutes(minutes);
	}
	
	/**
	 * エラーチェック
	 */
	public void errorCheck(){
		
		// 特例限度アラーム時間に値が入っているか確認する
		if (!this.exceptionLimitAlarmTime.isPresent()){
			
			// 限度アラーム時間以下
			if (this.agreementTime.lessThanOrEqualTo(this.limitAlarmTime.v())){
				this.status = AgreementTimeStatusOfMonthly.NORMAL;
				return;
			}
			
			// 限度エラー時間以下
			if (this.agreementTime.lessThanOrEqualTo(this.limitErrorTime.v())){
				this.status = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM;
				return;
			}
			
			this.status = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR;
			return;
		}
		
		// 限度アラーム時間以下
		if (this.agreementTime.lessThanOrEqualTo(this.limitAlarmTime.v())){
			this.status = AgreementTimeStatusOfMonthly.NORMAL_SPECIAL;
			return;
		}
		
		// 限度エラー時間以下
		if (this.agreementTime.lessThanOrEqualTo(this.limitErrorTime.v())){
			this.status = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP;
			return;
		}
		
		// 特例限度アラーム時間以下
		if (this.agreementTime.lessThanOrEqualTo(this.exceptionLimitAlarmTime.get().v())){
			this.status = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP;
			return;
		}
		
		// 特例限度エラー時間以下
		if (this.exceptionLimitErrorTime.isPresent()){
			if (this.agreementTime.lessThanOrEqualTo(this.exceptionLimitErrorTime.get().v())){
				this.status = AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM;
				return;
			}
		}
		else {
			this.status = AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM;
			return;
		}
		
		// 特例限度エラー時間を超える
		this.status = AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR;
	}
}
