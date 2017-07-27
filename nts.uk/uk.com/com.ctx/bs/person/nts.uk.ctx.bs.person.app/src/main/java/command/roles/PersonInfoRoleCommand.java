package command.roles;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonInfoRoleCommand {
	private String companyId;
	private String roleId;
	private String roleCode;
	private String roleName;
}
