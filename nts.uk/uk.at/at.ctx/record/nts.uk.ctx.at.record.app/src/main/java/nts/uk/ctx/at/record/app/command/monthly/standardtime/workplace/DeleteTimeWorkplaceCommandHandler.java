/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.workplace;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Workplace36AgreedHoursRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class DeleteTimeWorkplaceCommandHandler extends CommandHandler<DeleteTimeWorkplaceCommand> {

	@Inject
	private Workplace36AgreedHoursRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteTimeWorkplaceCommand> context) {

		DeleteTimeWorkplaceCommand command = context.getCommand();
		Optional<AgreementTimeOfWorkPlace> timeOfWorkPlace =  repo.getByWorkplaceId(command.getWorkplaceId(), EnumAdaptor.valueOf(command.getLaborSystemAtr(),LaborSystemtAtr.class));
		//1: delete(会社ID,雇用コード)
		timeOfWorkPlace.ifPresent(agreementTimeOfWorkPlace -> repo.delete(agreementTimeOfWorkPlace));

	}
}
