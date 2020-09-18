package nts.uk.ctx.sys.gateway.app.command.accessrestrictions;

import lombok.Setter;

@Setter
public class AllowedIPAddressUpdateCommand {

	public AllowedIPAddressCommand allowedIPAddressNew;
	
	public AllowedIPAddressCommand allowedIPAddressOld;
}
