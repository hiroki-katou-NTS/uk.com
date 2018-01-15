package nts.uk.ctx.pereg.app.command.roles.auth.item;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonInfoItemAuthCommand {

	private String roleId;

	private String personItemDefId;

	private int otherAuth;

	private int selfAuth;

	private List<PersonInfoItemAuthCommand> setItems;
}
