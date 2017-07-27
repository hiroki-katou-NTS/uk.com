/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationBasicWork;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ClassifiBWSaveCommandHandler.
 */
@Stateless
public class ClassifiBWSaveCommandHandler extends CommandHandler<ClassifiBWSaveCommand> {

	/** The repository. */
	@Inject
	private ClassifiBasicWorkRepository repository;

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ClassifiBWSaveCommand> context) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id user login
		String companyId = loginUserContext.companyId();

		// Get Command
		ClassifiBWSaveCommand command = context.getCommand();
		
		// Get Classification Code
		String classifiCode = command.getClassifiBasicWork().getClassificationCode();
		
//		// Get Worktype Code
//		String worktypeCode = command.getClassifiBasicWork().getBasicWorkSetting().get(0).getWorkTypeCode();
		
		// Get workdayDivision
		Integer workdayDivision = command.getClassifiBasicWork().getBasicWorkSetting().get(0).getWorkDayDivision();
		
		// Find if exist
		Optional<ClassificationBasicWork> optional = this.repository.find(companyId, classifiCode, workdayDivision);

		// Convert to Domain
		ClassificationBasicWork classifiBasicWork = command.toDomain();

		// Validate
		classifiBasicWork.validate();

		// Check exist
		if (optional.isPresent()) {
			this.repository.update(classifiBasicWork);
		} else {
			this.repository.insert(classifiBasicWork);
		}
	}

}
