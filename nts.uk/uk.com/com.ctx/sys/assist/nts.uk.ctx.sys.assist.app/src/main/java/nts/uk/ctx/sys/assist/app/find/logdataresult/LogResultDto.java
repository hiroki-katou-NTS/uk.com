package nts.uk.ctx.sys.assist.app.find.logdataresult;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Value
public class LogResultDto {
	
	//field 結果ログ．ログ連番
	private int logNumber;
	
	//field 結果ログ．処理内容
	private String processingContent;
	
	//field 結果ログ．エラー内容　
	private String errorContent;
	
	//field 結果ログ．実行SQL　
	private String contentSql;
	
	//field 結果ログ．エラー日付　
	private GeneralDate errorDate;
	
	//field 結果ログ．エラー社員　
	private String errorEmployeeId;

}
