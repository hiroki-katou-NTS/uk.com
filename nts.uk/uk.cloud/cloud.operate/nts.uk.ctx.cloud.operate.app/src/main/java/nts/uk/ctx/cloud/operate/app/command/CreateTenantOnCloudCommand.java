package nts.uk.ctx.cloud.operate.app.command;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class CreateTenantOnCloudCommand {

	String tenanteCode;

	String tenantPassword;
	GeneralDate tenantStartDate;
	String administratorLoginId;
	String administratorPassword;

	String optionCode;
}
