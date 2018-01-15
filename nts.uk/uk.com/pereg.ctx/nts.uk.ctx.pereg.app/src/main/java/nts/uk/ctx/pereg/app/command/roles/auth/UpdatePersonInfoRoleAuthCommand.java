package nts.uk.ctx.pereg.app.command.roles.auth;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePersonInfoRoleAuthCommand {
	private String roleIdDestination;
	private List<String> roleIds;
}
