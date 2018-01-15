package nts.uk.ctx.pereg.app.command.roles.auth.category;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePersonInfoCategory {
	private String roleId;
	
	private String categoryId;
	
	private String categoryCode;
		
	private int allowPersonRef;
	
	private int allowOtherRef;
	
}
