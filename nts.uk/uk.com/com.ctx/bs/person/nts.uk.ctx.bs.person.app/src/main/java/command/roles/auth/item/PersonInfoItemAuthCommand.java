package command.roles.auth.item;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonInfoItemAuthCommand {
	
	private String roleId;
	
	private String personItemDefId;
	
	private int otherAuth;
	
	private int selfAuth;
}
