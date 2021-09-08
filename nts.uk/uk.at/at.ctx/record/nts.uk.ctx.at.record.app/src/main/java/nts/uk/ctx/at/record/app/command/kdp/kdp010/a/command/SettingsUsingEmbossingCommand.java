package nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;
import nts.uk.shr.com.context.AppContexts;

@Data
@NoArgsConstructor
public class SettingsUsingEmbossingCommand {

	private String companyId;
	
	private Boolean name_selection;
	
	private Boolean finger_authc;
	
	private Boolean ic_card;
	
	private Boolean indivition;
	
	private Boolean portal;
	
	private Boolean smart_phone;
	
	private Boolean ricohStamp;
	
	public SettingsUsingEmbossing toDomain() {
		return new SettingsUsingEmbossing(AppContexts.user().companyId(), this.name_selection, this.finger_authc, this.ic_card, this.indivition, this.portal, this.smart_phone, this.ricohStamp);
	}
}
