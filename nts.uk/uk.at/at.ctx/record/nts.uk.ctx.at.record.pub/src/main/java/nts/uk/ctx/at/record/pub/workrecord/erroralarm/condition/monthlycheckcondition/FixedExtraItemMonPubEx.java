package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FixedExtraItemMonPubEx {

	/**NO*/
	private int fixedExtraItemMonNo;
	/**名称*/
	private String fixedExtraItemMonName;
	/**初期メッセージ*/
	private String message;
	
	public FixedExtraItemMonPubEx(int fixedExtraItemMonNo, String fixedExtraItemMonName, String message) {
		super();
		this.fixedExtraItemMonNo = fixedExtraItemMonNo;
		this.fixedExtraItemMonName = fixedExtraItemMonName;
		this.message = message;
	}
	
}
