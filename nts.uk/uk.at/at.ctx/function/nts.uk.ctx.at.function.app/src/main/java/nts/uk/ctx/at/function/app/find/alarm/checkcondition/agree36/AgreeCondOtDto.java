package nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36;

import lombok.Value;
@Value
public class AgreeCondOtDto {
	int category;
	/** ID */
	String id;
	/** no */
	int no;
	String code;
	/** 36超過時間 */
	int ot36;
	/** 36超過回数 */
	int excessNum;
	/** 表示するメッセージ */
	String messageDisp;
}
