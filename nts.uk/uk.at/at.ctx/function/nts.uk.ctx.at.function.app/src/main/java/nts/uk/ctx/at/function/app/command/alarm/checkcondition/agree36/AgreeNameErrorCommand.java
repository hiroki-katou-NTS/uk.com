package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
	private int period;
	/** エラーアラーム */
	private int errorAlarm;
	/** 名称 */
	private String name;
}
