/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.salarydetail.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputSettingSaveCommandHandler.
 */
@Stateless
public class SalaryOutputSettingSaveCommandHandler extends CommandHandler<SalaryOutputSettingSaveCommand> {

	/** The repository. */
	@Inject
	private SalaryOutputSettingRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<SalaryOutputSettingSaveCommand> context) {
		SalaryOutputSettingSaveCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		Boolean isExist = repository.isExist(companyCode, command.getCode());

		// In case of adding.
		if (command.isCreateMode()) {
			// Check exist.
			if (isExist) {
				throw new BusinessException("ER026");
			}

			// Convert to domain & validate
			SalaryOutputSetting outputSetting = command.toDomain(companyCode);
			outputSetting.validate();

			this.repository.create(outputSetting);
		}

		// In case of updating.
		else {
			// Check exist.
			if (!isExist) {
				throw new IllegalStateException("Output Setting is not found");
			}
			// Convert to domain & validate
			SalaryOutputSetting outputSetting = command.toDomain(companyCode);
			outputSetting.validate();

			this.repository.update(outputSetting);
		}
	}

}
