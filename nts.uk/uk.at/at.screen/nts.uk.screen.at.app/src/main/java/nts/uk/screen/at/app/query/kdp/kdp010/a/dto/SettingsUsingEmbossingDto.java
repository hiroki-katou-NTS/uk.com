package nts.uk.screen.at.app.query.kdp.kdp010.a.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;

@Getter
@AllArgsConstructor
public class SettingsUsingEmbossingDto {

	private String companyId;
	
	private Boolean name_selection;
	
	private Boolean finger_authc;
	
	private Boolean ic_card;
	
	private Boolean indivition;
	
	private Boolean portal;
	
	private Boolean smart_phone;
	
	private Boolean ricohStamp;
	
	public SettingsUsingEmbossingDto(SettingsUsingEmbossing domain) {
		super();
		this.companyId = domain.getCompanyId();
		this.name_selection = domain.isName_selection();
		this.finger_authc = domain.isFinger_authc();
		this.ic_card = domain.isIc_card();
		this.indivition = domain.isIndivition();
		this.portal = domain.isPortal();
		this.smart_phone = domain.isSmart_phone();
		this.ricohStamp = domain.isRicohStamp();
	}
}
