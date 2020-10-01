/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.employment;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentRepostitory;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DeleteTimeEmploymentCommandHandler extends CommandHandler<DeleteTimeEmploymentCommand> {

    @Inject
    private AgreementTimeOfEmploymentRepostitory repo;

    @Override
    protected void handle(CommandHandlerContext<DeleteTimeEmploymentCommand> context) {

        DeleteTimeEmploymentCommand command = context.getCommand();
        repo.remove(AppContexts.user().companyId(), command.getEmploymentCD(), EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));

    }
}
