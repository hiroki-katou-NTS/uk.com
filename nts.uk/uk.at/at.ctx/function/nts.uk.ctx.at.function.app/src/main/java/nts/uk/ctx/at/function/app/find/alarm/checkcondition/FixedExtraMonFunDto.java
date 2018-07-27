package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunImport;

@Data
@AllArgsConstructor
public class FixedExtraMonFunDto {
	/**ID*/
	private String  monAlarmCheckID;
	
	private String monAlarmCheckName;
	/**NO*/
	private  int fixedExtraItemMonNo;
	/**使用区分*/
	private boolean useAtr;
	/**表示メッセージ*/
	private String message;
	
	public static FixedExtraMonFunDto convertToImport(FixedExtraMonFunImport importData) {
		return new FixedExtraMonFunDto(
				importData.getMonAlarmCheckID(),
				"",
				importData.getFixedExtraItemMonNo(),
				importData.isUseAtr(),
				importData.getMessage()
				);
	}
}
