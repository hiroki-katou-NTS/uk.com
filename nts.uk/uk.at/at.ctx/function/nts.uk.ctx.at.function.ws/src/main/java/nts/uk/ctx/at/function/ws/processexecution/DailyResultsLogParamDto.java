package nts.uk.ctx.at.function.ws.processexecution;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.i18n.I18NText;

@Value
@AllArgsConstructor
public class DailyResultsLogParamDto {
	
	// 実行したメニュ
	private String executedMenuName = I18NText.getText("#KBT002_344");
	
	// 締め名称
	private String nameClosure;
	
	// 計算実行ログID
	private String empCalAndSumExecLogID;
}
