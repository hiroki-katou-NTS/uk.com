package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;

@Value
public class AgreementTimeOfMonthlyDto {
	private int agreementTime;
	/** 限度エラー時間 */
	private int limitErrorTime;
	/** 限度アラーム時間 */
	private int limitAlarmTime;
	/** 特例限度エラー時間 */
	private Integer exceptionLimitErrorTime;
	/** 特例限度アラーム時間 */
	private Integer exceptionLimitAlarmTime;
	/** 状態 */
	private int status;
	public AgreementTimeOfMonthlyDto(int agreementTime, int limitErrorTime, int limitAlarmTime, Integer exceptionLimitErrorTime, Integer exceptionLimitAlarmTime, int status) {
		super();
		this.agreementTime = agreementTime;
		this.limitErrorTime = limitErrorTime;
		this.limitAlarmTime = limitAlarmTime;
		this.exceptionLimitErrorTime = exceptionLimitErrorTime;
		this.exceptionLimitAlarmTime = exceptionLimitAlarmTime;
		this.status = status;
	}
	
	public static AgreementTimeOfMonthlyDto fromAgreementTimeOfMonthly(AgreementTimeOfMonthly agreementTimeOfMonthly) {
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		return new AgreementTimeOfMonthlyDto(
//				agreementTimeOfMonthly.getAgreementTime().v(), 
//				agreementTimeOfMonthly.getLimitErrorTime().v(), 
//				agreementTimeOfMonthly.getLimitAlarmTime().v(), 
//				agreementTimeOfMonthly.getExceptionLimitErrorTime().map(x -> x.v()).orElse(null), 
//				agreementTimeOfMonthly.getExceptionLimitAlarmTime().map(x -> x.v()).orElse(null), 
//				agreementTimeOfMonthly.getStatus().value);
		return new AgreementTimeOfMonthlyDto(0, 0, 0, 0, 0, 0);
	}  

}
