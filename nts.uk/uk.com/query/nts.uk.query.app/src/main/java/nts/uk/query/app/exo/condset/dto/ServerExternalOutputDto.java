package nts.uk.query.app.exo.condset.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@Data
public class ServerExternalOutputDto {
	/**
	 * 実行結果
	 */
	private boolean executionResult;
	
	/**
	 * エラーメッセージ
	 */
	private String errorMessage;
	
	/**
	 * 期間
	 */
	private DatePeriod period;
	
	/**
	 * 基準日
	 */
	private GeneralDate baseDate;

	public ServerExternalOutputDto(boolean executionResult, String errorMessage) {
		super();
		this.executionResult = executionResult;
		this.errorMessage = errorMessage;
	}

	public ServerExternalOutputDto(boolean executionResult, DatePeriod period, GeneralDate baseDate) {
		super();
		this.executionResult = executionResult;
		this.period = period;
		this.baseDate = baseDate;
	}
}
