package nts.uk.ctx.sys.auth.app.command.grant;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class RoleSetGrantedJobTitleCommand {

	private boolean applyToConcurrentPerson;
	private List<RoleSetGrantedJobTitleDetailCommand> details;
	
}
