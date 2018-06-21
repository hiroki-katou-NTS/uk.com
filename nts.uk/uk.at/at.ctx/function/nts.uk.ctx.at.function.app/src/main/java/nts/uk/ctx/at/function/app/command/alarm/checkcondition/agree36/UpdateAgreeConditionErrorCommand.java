package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import java.util.List;

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
public class UpdateAgreeConditionErrorCommand {
	List<AgreeConditionErrorCommand> agreeConditionErrorCommand;
	List<DeleteAgreeConditionErrorCommand> deleteCondError;
	List<DeleteAgreeCondOtCommand> deleteCondOt;
}
