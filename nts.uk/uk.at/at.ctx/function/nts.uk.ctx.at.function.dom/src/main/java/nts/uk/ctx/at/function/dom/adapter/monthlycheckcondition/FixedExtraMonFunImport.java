package nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class FixedExtraMonFunImport {
	/**ID*/
	private String  monAlarmCheckID;
	/**NO*/
	private  int fixedExtraItemMonNo;
	/**使用区分*/
	private boolean useAtr;
	/**表示メッセージ*/
	private String message;
	public FixedExtraMonFunImport(String monAlarmCheckID, int fixedExtraItemMonNo, boolean useAtr, String message) {
		super();
		this.monAlarmCheckID = monAlarmCheckID;
		this.fixedExtraItemMonNo = fixedExtraItemMonNo;
		this.useAtr = useAtr;
		this.message = message;
	}
	
}
