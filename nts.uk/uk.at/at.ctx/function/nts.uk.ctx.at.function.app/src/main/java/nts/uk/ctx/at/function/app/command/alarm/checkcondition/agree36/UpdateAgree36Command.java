package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateAgree36Command {
	List<AgreeConditionErrorCommand> listCondError;
	List<AgreeCondOtCommand> listCondOt;
}
