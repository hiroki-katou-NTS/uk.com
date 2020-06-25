package nts.uk.ctx.at.record.app.command.kdp.kdp010.a;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.PortalStampSettingsCommand;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;

@Stateless
public class PortalStampSettingsCommandHandler {

	@Inject
	private PortalStampSettingsRepository portalStampSettingsRepo;
	
	public void save(PortalStampSettingsCommand command) {
		portalStampSettingsRepo.insert(command.toDomain());
	}
	
}
