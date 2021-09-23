package nts.uk.ctx.at.function.ws.processexecution;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class DailyResultsLogParamDto {
	
	// 実行したメニュ
	private String executedMenuName;
	
	// 締め名称
	private String nameClosure;
	
	// 計算実行ログID
	private String empCalAndSumExecLogID;
}
