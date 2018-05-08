package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.ErrorAlarm;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.MessageDisp;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.Period;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.UseClassification;
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
	/** 使用区分 */
	private UseClassification useAtr;
	/** 期間 */
	private Period period;
	/** エラーアラーム */
	private ErrorAlarm errorAlarm;
	/** 表示するメッセージ */
	private MessageDisp messageDisp;
}
