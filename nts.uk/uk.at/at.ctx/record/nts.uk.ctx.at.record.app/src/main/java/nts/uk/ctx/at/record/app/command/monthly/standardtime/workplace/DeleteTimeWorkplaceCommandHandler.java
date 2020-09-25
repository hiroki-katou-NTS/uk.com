/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.workplace;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfWorkPlaceRepository;
import nts.uk.ctx.at.shared.dom.standardtime.enums.LaborSystemtAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DeleteTimeWorkplaceCommandHandler extends CommandHandler<DeleteTimeWorkplaceCommand> {

	@Inject
	private AgreementTimeOfWorkPlaceRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteTimeWorkplaceCommand> context) {

		DeleteTimeWorkplaceCommand command = context.getCommand();
		repo.remove(command.getWorkplaceId(), EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));

	}
}
