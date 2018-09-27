/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly.dto.RetentionYearlyDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingDomainEvent;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class RetentionYearlySaveCommandHandler.
 */
@Stateless
public class RetentionYearlySaveCommandHandler extends CommandHandler<RetentionYearlySaveCommand> {

    /** The repository. */
    @Inject
    private RetentionYearlySettingRepository repository;
    
    @Inject
    private AnnualPaidLeaveSettingRepository isManagedRepo;

    /*
     * (non-Javadoc)
     * @see
     * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
     * .CommandHandlerContext)
     */
    @Override
    protected void handle(CommandHandlerContext<RetentionYearlySaveCommand> context) {
        // get user login
        LoginUserContext loginUserContext = AppContexts.user();

        // get company code user login
        String companyId = loginUserContext.companyId();

        // Get Command
        RetentionYearlySaveCommand command = context.getCommand();
        
        Optional<RetentionYearlySetting> yearlyRetentionOpt = this.repository.findByCompanyId(companyId);
        // Find "isManaged"
        int isManaged = this.isManagedRepo.findByCompanyId(companyId).getYearManageType().value;
        
        // Check is managed, keep old values when is not managed
//        if (yearlyRetentionOpt.isPresent() && isManaged == ManageDistinct.NO.value) {
//            RetentionYearlySetting retentionYearlySettingDB = yearlyRetentionOpt.get();
//            RetentionYearlyDto yearlyRetentionDto = command.getRetentionYearly();
//            yearlyRetentionDto.setLeaveAsWorkDays(retentionYearlySettingDB.getLeaveAsWorkDays());
//            yearlyRetentionDto.getUpperLimitSettingDto().setMaxDaysCumulation(
//                    retentionYearlySettingDB.getUpperLimitSetting().getMaxDaysCumulation().v());
//            yearlyRetentionDto.getUpperLimitSettingDto().setRetentionYearsAmount(
//                    retentionYearlySettingDB.getUpperLimitSetting().getRetentionYearsAmount().v());
//            yearlyRetentionDto.setManagementCategory(retentionYearlySettingDB.getManagementCategory().value);
//        }
        
        // To Domain
        RetentionYearlySetting retentionYearlySetting = command.toDomain(companyId);

        // validate domain
        retentionYearlySetting.validate();
        
        if (yearlyRetentionOpt.isPresent()) {
            this.repository.update(retentionYearlySetting);
        } else {
            this.repository.insert(retentionYearlySetting);
        }
        
        
        //get ManagementCategory from DB
        int managementCategoryDB = yearlyRetentionOpt.isPresent() == true ? yearlyRetentionOpt.get().getManagementCategory().value : -1;
        
        //check managementCategory change
		boolean managementCategory = command.getRetentionYearly().getManagementCategory() != managementCategoryDB;
		
		if (managementCategory) {
			boolean manage = command.getRetentionYearly().getManagementCategory() == ManageDistinct.YES.value;
		    val retentionYearlySettingEvent = new RetentionYearlySettingDomainEvent(manage);
		    retentionYearlySettingEvent.toBePublished();
		}
    }
}
