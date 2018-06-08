package nts.uk.ctx.pereg.app.command.roles.auth.functionauth;

import java.util.List;

import lombok.Getter;

@Getter
public class RegisterFuncAuthCommand {
	
	private String roleId;
	
	private List<FunctionAuthObject> functionAuthList;
	
}
