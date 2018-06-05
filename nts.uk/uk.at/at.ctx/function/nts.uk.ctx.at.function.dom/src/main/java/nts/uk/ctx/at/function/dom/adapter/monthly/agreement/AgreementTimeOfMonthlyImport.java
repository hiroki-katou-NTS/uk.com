package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import java.util.Optional;

import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;

public class AgreementTimeOfMonthlyImport {

	/** 36協定時間 */
	@Setter
	private AttendanceTimeMonth agreementTime;
	/** 限度エラー時間 */
	private LimitOneMonth limitErrorTime;
	/** 限度アラーム時間 */
	private LimitOneMonth limitAlarmTime;
	/** 特例限度エラー時間 */
	private Optional<LimitOneMonth> exceptionLimitErrorTime;
	/** 特例限度アラーム時間 */
	private Optional<LimitOneMonth> exceptionLimitAlarmTime;
	/** 状態 */
	private AgreementTimeStatusOfMonthly status;
	
	/**
	 * コンストラクタ
	 */
	public AgreementTimeOfMonthlyImport() {
		this.agreementTime = new AttendanceTimeMonth(0);
		this.limitErrorTime = new LimitOneMonth(0);
		this.limitAlarmTime = new LimitOneMonth(0);
		this.exceptionLimitErrorTime = Optional.empty();
		this.exceptionLimitAlarmTime = Optional.empty();
		this.status = AgreementTimeStatusOfMonthly.NORMAL;
	}
	
	/**
	 * ファクトリー
	 * @param agreementTime 36協定時間
	 * @param limitErrorTime 限度エラー時間
	 * @param limitAlarmTime 限度アラーム時間
	 * @param exceptionLimitErrorTime 特例限度エラー時間
	 * @param exceptionLimitAlarmTime 特例限度アラーム時間
	 * @param status 状態
	 * @return 月別実績の36協定時間
	 */
	public static AgreementTimeOfMonthlyImport of(
			AttendanceTimeMonth agreementTime,
			LimitOneMonth limitErrorTime,
			LimitOneMonth limitAlarmTime,
			Optional<LimitOneMonth> exceptionLimitErrorTime,
			Optional<LimitOneMonth> exceptionLimitAlarmTime,
			AgreementTimeStatusOfMonthly status){

		AgreementTimeOfMonthlyImport domain = new AgreementTimeOfMonthlyImport();
		domain.agreementTime = agreementTime;
		domain.limitErrorTime = limitErrorTime;
		domain.limitAlarmTime = limitAlarmTime;
		domain.exceptionLimitErrorTime = exceptionLimitErrorTime;
		domain.exceptionLimitAlarmTime = exceptionLimitAlarmTime;
		domain.status = status;
		return domain;
	}

	/**
	 * エラーチェック
	 */
	public void errorCheck(){
		
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
		
		// 特例限度アラーム時間以下
		if (this.exceptionLimitAlarmTime.isPresent()){
			if (this.exceptionLimitAlarmTime.get().lessThanOrEqualTo(0)){
				this.status = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR;
				return;
			}
			if (this.agreementTime.lessThanOrEqualTo(this.exceptionLimitAlarmTime.get().v())){
				this.status = AgreementTimeStatusOfMonthly.IN_EXCEPTION_LIMIT;
				return;
			}
		}
		else {
			this.status = AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR;
			return;
		}
		
		// 特例限度エラー時間以下
		if (this.exceptionLimitErrorTime.isPresent()){
			if (this.agreementTime.lessThanOrEqualTo(this.exceptionLimitErrorTime.get().v())){
				this.status = AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM;
				return;
			}
		}
		
		// 特例限度エラー時間を超える
		this.status = AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AgreementTimeOfMonthlyImport target) {
		
		this.agreementTime = this.agreementTime.addMinutes(target.agreementTime.v());
		
		// 再エラーチェックする
		this.errorCheck();
	}
}