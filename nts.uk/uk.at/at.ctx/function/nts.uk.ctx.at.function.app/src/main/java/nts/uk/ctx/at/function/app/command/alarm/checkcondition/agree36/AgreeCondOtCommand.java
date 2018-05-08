package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.MessageDisp;

/**
 * insert/update AgreeCondOtCommand
 * @author yennth
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class AgreeCondOtCommand {
	/** ID */
	private String id;
	/** no */
	private int no;
	/** 36超過時間 */
	private BigDecimal ot36;
	/** 36超過回数 */
	private int excessNum;
	/** 表示するメッセージ */
	private MessageDisp messageDisp;
}
