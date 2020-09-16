package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.onemonth.AgreementOneMonth;

@Getter
public class AgreementTimeOfMonthlyImport {

	/** 36協定時間 */
	@Setter
	private AttendanceTimeMonth agreementTime;
	/** 限度エラー時間 */
	private AgreementOneMonth limitErrorTime;
	/** 限度アラーム時間 */
	private AgreementOneMonth limitAlarmTime;
	/** 特例限度エラー時間 */
	private Optional<AgreementOneMonth> exceptionLimitErrorTime;
	/** 特例限度アラーム時間 */
	private Optional<AgreementOneMonth> exceptionLimitAlarmTime;
	/** 状態 */
	private AgreementTimeStatusOfMonthly status;
	
	/**
	 * コンストラクタ
	 */
	public AgreementTimeOfMonthlyImport() {
		this.agreementTime = new AttendanceTimeMonth(0);
		this.limitErrorTime = new AgreementOneMonth(0);
		this.limitAlarmTime = new AgreementOneMonth(0);
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
			AgreementOneMonth limitErrorTime,
			AgreementOneMonth limitAlarmTime,
			Optional<AgreementOneMonth> exceptionLimitErrorTime,
			Optional<AgreementOneMonth> exceptionLimitAlarmTime,
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
}