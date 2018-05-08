package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.ErrorAlarm;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.MessageDisp;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.Period;
/**
 * insert/update AgreeNameErrorCommand
 * @author yennth
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class AgreeNameErrorCommand {
	/** 期間 */
	private Period period;
	/** エラーアラーム */
	private ErrorAlarm errorAlarm;
	/** 名称 */
	private MessageDisp name;
}
