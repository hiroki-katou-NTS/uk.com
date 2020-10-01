/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.classification;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DeleteTimeClassificationCommandHandler extends CommandHandler<DeleteTimeClassificationCommand> {

	@Inject
	private AgreementTimeOfClassificationRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteTimeClassificationCommand> context) {

		DeleteTimeClassificationCommand command = context.getCommand();
		repo.remove(AppContexts.user().companyId(), EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),command.getClassificationCode());

	}
}
