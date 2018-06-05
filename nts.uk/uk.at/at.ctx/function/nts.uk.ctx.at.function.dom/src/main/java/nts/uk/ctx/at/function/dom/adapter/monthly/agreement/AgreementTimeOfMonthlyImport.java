package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;

@Getter
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
}