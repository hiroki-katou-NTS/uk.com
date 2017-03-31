/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.businesstype.command;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessType;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class InsuranceBusinessTypeUpdateCommandHandler.
 */
@Stateless
public class InsuranceBusinessTypeUpdateCommandHandler extends CommandHandler<InsuranceBusinessTypeUpdateCommand> {

	/** The insurance business type repository. */
	@Inject
	private InsuranceBusinessTypeRepository insBizTypeRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<InsuranceBusinessTypeUpdateCommand> context) {
		List<InsuranceBusinessType> lsInsuranceBusinessType = context.getCommand()
				.toDomain((AppContexts.user().companyCode()));
		this.insBizTypeRepo.update(lsInsuranceBusinessType);
	}

}
