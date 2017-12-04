package nts.uk.ctx.pereg.app.command.roles.auth;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pereg.app.command.roles.auth.category.SavePersonInfoCategoryAuthCommand;

@Getter
@Setter
public class SavePersonInfoRoleAuthCommand {
	
	private String roleId;
	
	private String roleCode;
	
	private String roleName;
	
	private int allowMapBrowse;
	
	private int allowMapUpload;
	
	private int allowDocUpload;
	
	private int allowDocRef;
	
	private int allowAvatarUpload;
	
	private int allowAvatarRef;
	
	private SavePersonInfoCategoryAuthCommand currentCategory;
}
