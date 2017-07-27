package command.roles.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonInfoRoleAuthCommand {
	private String roleId;
	private int allowMapUpload;
	private int allowMapBrowse;
	private int allowDocUpload;
	private int allowDocRef;
	private int allowAvatarUpload;
	private int allowAvatarRef;
}
