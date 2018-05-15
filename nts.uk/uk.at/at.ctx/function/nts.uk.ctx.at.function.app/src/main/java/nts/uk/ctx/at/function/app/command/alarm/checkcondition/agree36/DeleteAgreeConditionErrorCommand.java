package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteAgreeConditionErrorCommand {
	private String companyId;
	private int category;
	private String code;
}
