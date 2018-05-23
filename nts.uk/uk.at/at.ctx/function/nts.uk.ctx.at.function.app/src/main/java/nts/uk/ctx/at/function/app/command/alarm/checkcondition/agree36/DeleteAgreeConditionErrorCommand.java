package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAgreeConditionErrorCommand {
	private String companyId;
	private int category;
	private String code;
	
	public static DeleteAgreeConditionErrorCommand changeType(AgreeConditionErrorCommand cmd){
		return new DeleteAgreeConditionErrorCommand(cmd.getCompanyId(), cmd.getCategory(), cmd.getCode());
	}
}
