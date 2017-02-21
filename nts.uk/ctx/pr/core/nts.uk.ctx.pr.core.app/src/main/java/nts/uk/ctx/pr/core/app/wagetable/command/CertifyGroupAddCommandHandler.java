/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupRepository;
import nts.uk.ctx.pr.core.dom.wagetable.service.CertifyGroupService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CertifyGroupAddCommandHandler.
 */
@Stateless
@Transactional
public class CertifyGroupAddCommandHandler extends CommandHandler<CertifyGroupAddCommand> {

	/** The certify group repository. */
	@Inject
	private CertifyGroupRepository certifyGroupRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CertifyGroupAddCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		CertifyGroup certifyGroup = context.getCommand().toDomain(companyCode);
		this.certifyGroupRepository.add(certifyGroup);
	}

}
