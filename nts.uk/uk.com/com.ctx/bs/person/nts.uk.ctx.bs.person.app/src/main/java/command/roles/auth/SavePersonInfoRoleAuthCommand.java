package command.roles.auth;

import command.roles.auth.category.SavePersonInfoCategoryAuthCommand;
import lombok.Getter;
import lombok.Setter;

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
