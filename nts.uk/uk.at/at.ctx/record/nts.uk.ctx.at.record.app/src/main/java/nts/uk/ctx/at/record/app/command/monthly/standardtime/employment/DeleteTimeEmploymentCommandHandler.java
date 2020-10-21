/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.employment;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Employment36HoursRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class DeleteTimeEmploymentCommandHandler extends CommandHandler<DeleteTimeEmploymentCommand> {

    @Inject
    private Employment36HoursRepository repo;

    @Override
    protected void handle(CommandHandlerContext<DeleteTimeEmploymentCommand> context) {
        DeleteTimeEmploymentCommand command = context.getCommand();
        Optional<AgreementTimeOfEmployment> timeOfEmployment =  repo.getByCidAndCd(AppContexts.user().companyId(),command.getEmploymentCD(),
                EnumAdaptor.valueOf(command.getLaborSystemAtr(),LaborSystemtAtr.class));
        //1: delete
        timeOfEmployment.ifPresent(agreementTimeOfEmployment -> repo.delete(agreementTimeOfEmployment));
    }
}
