/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly.dto.EmploymentSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmploymentSaveCommandHandler.
 */
@Stateless
public class EmploymentSaveCommandHandler extends CommandHandler<EmploymentSaveCommand> {

	/** The repository. */
	@Inject
	private EmploymentSettingRepository repository;
	
	@Inject
	private AnnualPaidLeaveSettingRepository isManagedRepo;

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<EmploymentSaveCommand> context) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code user login
		String companyId = loginUserContext.companyId();

		// Get Command
		EmploymentSaveCommand command = context.getCommand();

		Optional<EmptYearlyRetentionSetting> emptRetentionOpt = this.repository.find(companyId,
				command.getEmploymentSetting().getEmploymentCode());
		int isManaged = this.isManagedRepo.findByCompanyId(companyId).getYearManageType().value;
		
		// Check is managed, keep old values when is not managed
		if (emptRetentionOpt.isPresent() && isManaged == ManageDistinct.NO.value) {
			EmptYearlyRetentionSetting emptRetentionDB = emptRetentionOpt.get();
			EmploymentSettingDto emptSettingDto = command.getEmploymentSetting();
			emptSettingDto.setEmploymentCode(emptRetentionDB.getEmploymentCode());
			emptSettingDto.setManagementCategory(emptRetentionDB.getManagementCategory().value);
		}
		// Convert to Domain
		EmptYearlyRetentionSetting emptRetentionSetting = command.toDomain(companyId);

		// Validate
		emptRetentionSetting.validate();
		
		// Check exist
		if(emptRetentionOpt.isPresent()) {
			this.repository.update(emptRetentionSetting);
		}
		else {
			this.repository.insert(emptRetentionSetting);
		}
	}

}
