package nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FixedExtraItemMonFunImport {
	/**NO*/
	private int fixedExtraItemMonNo;
	/**名称*/
	private String fixedExtraItemMonName;
	/**初期メッセージ*/
	private String message;
	public FixedExtraItemMonFunImport(int fixedExtraItemMonNo, String fixedExtraItemMonName, String message) {
		super();
		this.fixedExtraItemMonNo = fixedExtraItemMonNo;
		this.fixedExtraItemMonName = fixedExtraItemMonName;
		this.message = message;
	}
	
}
