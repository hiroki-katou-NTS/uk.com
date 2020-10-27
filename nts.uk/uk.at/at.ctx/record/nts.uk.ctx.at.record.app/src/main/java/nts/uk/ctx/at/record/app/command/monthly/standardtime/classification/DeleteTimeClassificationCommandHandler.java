/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.classification;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Classification36AgreementTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class DeleteTimeClassificationCommandHandler extends CommandHandler<DeleteTimeClassificationCommand> {

	@Inject
	private Classification36AgreementTimeRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteTimeClassificationCommand> context) {

		DeleteTimeClassificationCommand command = context.getCommand();
		Optional<AgreementTimeOfClassification> timeOfClassification =  repo.getByCidAndClassificationCode(AppContexts.user().companyId(),
				command.getClassificationCode(), EnumAdaptor.valueOf(command.getLaborSystemAtr(),LaborSystemtAtr.class));

		//1: delete(会社ID,雇用コード)
		timeOfClassification.ifPresent(agreementTimeOfClassification -> repo.delete(agreementTimeOfClassification));

	}
}
