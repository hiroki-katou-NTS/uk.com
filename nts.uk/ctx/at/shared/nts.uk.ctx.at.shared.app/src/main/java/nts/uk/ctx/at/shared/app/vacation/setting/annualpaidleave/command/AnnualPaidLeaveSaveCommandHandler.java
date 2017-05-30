/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AnnualPaidLeaveUpateCommandHandler.
 */
@Stateless
public class AnnualPaidLeaveSaveCommandHandler extends CommandHandler<AnnualPaidLeaveSaveCommand> {

    /** The annual repo. */
    @Inject
    private AnnualPaidLeaveSettingRepository annualRepo;

    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
     * .CommandHandlerContext)
     */
    @Override
    protected void handle(CommandHandlerContext<AnnualPaidLeaveSaveCommand> context) {
        AnnualPaidLeaveSaveCommand command = context.getCommand();

        String companyId = AppContexts.user().companyId();
        AnnualPaidLeaveSetting setting = command.toDomain(companyId);

        AnnualPaidLeaveSetting domain = this.annualRepo.findByCompanyId(companyId);
        if (domain != null) {
            this.annualRepo.update(setting);
        } else {
            this.annualRepo.add(setting);
        }
    }
}
