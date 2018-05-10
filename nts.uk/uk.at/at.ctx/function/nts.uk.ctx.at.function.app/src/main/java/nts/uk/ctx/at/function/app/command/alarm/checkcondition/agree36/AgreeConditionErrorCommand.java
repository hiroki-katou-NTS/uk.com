package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * insert/update AgreeConditionError Command
 * @author yennth
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class AgreeConditionErrorCommand {
	/** ID */
	private String id;
	private String code;
	private String companyId;
	private int category;
	/** 使用区分 */
	private int useAtr;
	/** 期間 */
	private int period;
	/** エラーアラーム */
	private int errorAlarm;
	/** 表示するメッセージ */
	private String messageDisp;
}
