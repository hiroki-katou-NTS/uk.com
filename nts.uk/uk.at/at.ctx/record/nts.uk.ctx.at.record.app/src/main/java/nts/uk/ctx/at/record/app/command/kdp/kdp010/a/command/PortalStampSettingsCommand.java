package nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.shr.com.context.AppContexts;

@Data
@NoArgsConstructor
public class PortalStampSettingsCommand {
	
	private String cid;
	
	private DisplaySettingsStampScreenCommand displaySettingsStampScreen;
	
	private List<ButtonSettingsCommand> buttonSettings;
	
	private Boolean suppressStampBtn;
	
	private Boolean useTopMenuLink;

	public PortalStampSettings toDomain() {
		return new PortalStampSettings(AppContexts.user().companyId(), this.displaySettingsStampScreen.toDomain(), this.buttonSettings.stream().map(c->c.toDomain()).collect(Collectors.toList()), this.suppressStampBtn, this.useTopMenuLink);
	}
}
