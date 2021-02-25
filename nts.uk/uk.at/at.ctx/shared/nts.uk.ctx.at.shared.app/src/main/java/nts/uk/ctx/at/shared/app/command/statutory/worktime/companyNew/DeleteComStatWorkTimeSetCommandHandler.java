/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeComRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeComRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteComDeformationLaborSettingCommandHandler.
 */
@Stateless
public class DeleteComStatWorkTimeSetCommandHandler
		extends CommandHandler<DeleteComStatWorkTimeSetCommand> {

	/** The trans labor time repository. */
	@Inject
	private DeforLaborTimeComRepo transLaborTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private RegularLaborTimeComRepo regularLaborTimeRepository;

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteComStatWorkTimeSetCommand> context) {
		DeleteComStatWorkTimeSetCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		int year = command.getYear();
		
		// remove ComNormalSetting, ComFlexSetting, ComDeforLaborSetting, ComRegularLaborTime, ComTransLaborTime with companyID & year if present
		monthlyWorkTimeSetRepo.removeCompany(companyId, year);
		regularLaborTimeRepository.find(companyId).ifPresent((setting) -> regularLaborTimeRepository.remove(companyId));
		transLaborTimeRepository.find(companyId).ifPresent((setting) -> transLaborTimeRepository.remove(companyId));
	}

}
