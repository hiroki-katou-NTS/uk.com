package nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;

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
	
	public SettingsUsingEmbossing toDomain() {
		return new SettingsUsingEmbossing(this.companyId, this.name_selection, this.finger_authc, this.ic_card, this.indivition, this.portal, this.smart_phone);
	}
}
