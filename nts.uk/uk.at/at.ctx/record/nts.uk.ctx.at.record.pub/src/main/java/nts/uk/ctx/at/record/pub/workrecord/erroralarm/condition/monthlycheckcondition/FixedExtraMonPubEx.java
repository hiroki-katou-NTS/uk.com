package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class FixedExtraMonPubEx {
	/**ID*/
	private String  monAlarmCheckID;
	/**NO*/
	private  int fixedExtraItemMonNo;
	/**使用区分*/
	private boolean useAtr;
	/**表示メッセージ*/
	private String message;
	public FixedExtraMonPubEx(String monAlarmCheckID, int fixedExtraItemMonNo, boolean useAtr, String message) {
		super();
		this.monAlarmCheckID = monAlarmCheckID;
		this.fixedExtraItemMonNo = fixedExtraItemMonNo;
		this.useAtr = useAtr;
		this.message = message;
	}
	
	
}
