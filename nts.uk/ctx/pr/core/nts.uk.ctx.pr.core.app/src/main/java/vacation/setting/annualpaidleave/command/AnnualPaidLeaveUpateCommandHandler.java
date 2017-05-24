/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package vacation.setting.annualpaidleave.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.ManageAnnualSettingReposity;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AnnualPaidLeaveUpateCommandHandler.
 */
@Stateless
public class AnnualPaidLeaveUpateCommandHandler extends CommandHandler<AnnualPaidLeaveUpateCommand> {
    
    /** The annual repo. */
    @Inject
    private AnnualPaidLeaveSettingRepository annualRepo;
    
    /** The manage annual repo. */
    @Inject
    private ManageAnnualSettingReposity manageAnnualRepo;
    
    /* (non-Javadoc)
     * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
     */
    @Override
    protected void handle(CommandHandlerContext<AnnualPaidLeaveUpateCommand> context) {
        AnnualPaidLeaveUpateCommand command = context.getCommand();
        
        // TODO: fake company id.
        String companyId = AppContexts.user().companyCode();
        
        Optional<AnnualPaidLeaveSetting> optional = this.annualRepo.findByCompanyId(companyId);
        if (!optional.isPresent()) {
            // TODO: do some thing
        }
        AnnualPaidLeaveSetting setting = optional.get();
        setting.setYearManageType(command.getAnnualManage());
        this.annualRepo.update(setting);
        
        ManageAnnualSetting manageSetting = command.getSetting().toDomain();
        this.manageAnnualRepo.update(manageSetting);
    }
}
