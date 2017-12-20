package nts.uk.ctx.pereg.app.command.roles.auth.item;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SavePersonInfoItemAuthCommand {
	private String personItemDefId;
	
	private String setting;
	
	private String requiredAtr;
	
	private String itemName;
	
	private String otherAuth;
	
	private String selfAuth;
}
