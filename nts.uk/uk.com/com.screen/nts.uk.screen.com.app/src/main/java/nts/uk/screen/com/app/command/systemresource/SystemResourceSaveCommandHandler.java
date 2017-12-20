/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.app.command.systemresource;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.screen.com.app.repository.systemresource.SystemResourceQueryRepository;
import nts.uk.screen.com.app.systemresource.dto.SystemResourceDto;
import nts.uk.shr.infra.i18n.resource.data.CisctI18NResource;

/**
 * The Class SystemResourceSaveCommandHandler.
 */
@Stateless

public class SystemResourceSaveCommandHandler extends CommandHandler<SystemResourceSaveCommand> {
	
	/** The system resource repository. */
	@Inject
	private SystemResourceQueryRepository systemResourceRepository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SystemResourceSaveCommand> context) {
		// Get command
		SystemResourceSaveCommand command = context.getCommand();
		
		for (SystemResourceDto item : command.getListData()) {
			
			Optional<CisctI18NResource> optSysresoucre = this.systemResourceRepository.findByResourceId(item.getResourceId());
			
			if(optSysresoucre.isPresent()){
				CisctI18NResource entity = optSysresoucre.get();
				entity.setContent(item.getResourceContent());
				this.systemResourceRepository.update(entity);
			}
		}
	}

}
