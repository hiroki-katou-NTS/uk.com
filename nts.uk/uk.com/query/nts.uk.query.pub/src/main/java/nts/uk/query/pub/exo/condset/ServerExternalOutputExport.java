package nts.uk.query.pub.exo.condset;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@Data
@AllArgsConstructor
public class ServerExternalOutputExport {

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
}
