package nts.uk.ctx.sys.gateway.app.command.stopsetting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.gateway.app.command.stopsetting.stopbycompany.StopByCompanyCommand;
import nts.uk.ctx.sys.gateway.app.command.stopsetting.stopbysystem.StopBySystemCommand;

@Setter
@Getter
public class SaveStopSettingCommand {

	private int isSystem;

	private StopBySystemCommand systemCommand;

	private StopByCompanyCommand companyCommand;
}
