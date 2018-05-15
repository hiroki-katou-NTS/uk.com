package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private String companyId;
	private int category;
	/** no */
	private int no;
	private String code;
	/** 36超過時間 */
	private BigDecimal ot36;
	/** 36超過回数 */
	private int excessNum;
	/** 表示するメッセージ */
	private String messageDisp;
}
