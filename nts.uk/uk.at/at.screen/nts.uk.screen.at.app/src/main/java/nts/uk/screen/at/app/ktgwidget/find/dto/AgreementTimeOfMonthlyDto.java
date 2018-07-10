package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Value;

@Value

public class AgreementTimeOfMonthlyDto {
	private int agreementTime;
	/** 限度エラー時間 */
	private int limitErrorTime;
	/** 限度アラーム時間 */
	private int limitAlarmTime;
	/** 特例限度エラー時間 */
	private int exceptionLimitErrorTime;
	/** 特例限度アラーム時間 */
	private int exceptionLimitAlarmTime;
	/** 状態 */
	private int status;
	public AgreementTimeOfMonthlyDto(int agreementTime, int limitErrorTime, int limitAlarmTime, int exceptionLimitErrorTime, int exceptionLimitAlarmTime, int status) {
		super();
		this.agreementTime = agreementTime;
		this.limitErrorTime = limitErrorTime;
		this.limitAlarmTime = limitAlarmTime;
		this.exceptionLimitErrorTime = exceptionLimitErrorTime;
		this.exceptionLimitAlarmTime = exceptionLimitAlarmTime;
		this.status = status;
	}

}
