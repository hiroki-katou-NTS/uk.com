package nts.uk.ctx.sys.assist.app.find.logdataresult;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Data
public class LogResultDto {
	
	/**
	 * 結果ログ．ログ連番
	 */
	private int logNumber;
	
	/**
	 * 結果ログ．処理内容
	 */
	private String processingContent;
	
	/**
	 * 結果ログ．エラー内容　
	 */
	private String errorContent;
	
	/**
	 * 結果ログ．実行SQL　
	 */
	private String contentSql;
	
	/**
	 * 結果ログ．エラー日付　
	 */
	private GeneralDate errorDate;
	
	/**
	 * 結果ログ．エラー社員
	 */
	private String errorEmployeeId;

}
