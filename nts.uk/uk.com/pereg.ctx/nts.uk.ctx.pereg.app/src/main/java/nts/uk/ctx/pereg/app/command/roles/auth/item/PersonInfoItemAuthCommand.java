package nts.uk.ctx.pereg.app.command.roles.auth.item;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonInfoItemAuthCommand {

	private String roleId;

	private String personItemDefId;

	private String parrentCd;

	private int otherAuth;

	private int selfAuth;

}
