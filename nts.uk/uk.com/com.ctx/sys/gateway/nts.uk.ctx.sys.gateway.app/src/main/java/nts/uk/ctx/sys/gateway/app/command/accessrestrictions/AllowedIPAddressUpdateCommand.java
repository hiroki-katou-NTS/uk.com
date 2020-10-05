package nts.uk.ctx.sys.gateway.app.command.accessrestrictions;

import lombok.Setter;

@Setter
public class AllowedIPAddressUpdateCommand {
	
	public Integer accessLimitUseAtr;

	public AllowedIPAddressCommand allowedIPAddressNew;
	
	public AllowedIPAddressCommand allowedIPAddressOld;
}
