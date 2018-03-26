/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteWkpStatWorkTimeSetCommandHandler.
 */
@Stateless
public class DeleteWkpStatWorkTimeSetCommandHandler
		extends CommandHandler<DeleteWkpStatWorkTimeSetCommand> {

	/** The wkp normal setting repository. */
	@Inject
	private WkpNormalSettingRepository wkpNormalSettingRepository;
	
	/** The wkp flex setting repository. */
	@Inject
	private WkpFlexSettingRepository wkpFlexSettingRepository;
	
	/** The wkp defor labor setting repository. */
	@Inject
	private WkpDeforLaborSettingRepository wkpDeforLaborSettingRepository;
	
	/** The wkp regular work time repository. */
	@Inject
	private WkpRegularLaborTimeRepository wkpRegularWorkTimeRepository;
	
	/** The wkp spe defor labor time repository. */
	@Inject
	private WkpTransLaborTimeRepository wkpTransLaborTimeRepository;
	
	/* 
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteWkpStatWorkTimeSetCommand> context) {
		DeleteWkpStatWorkTimeSetCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		int year = command.getYear();
		String wkpId = command.getWkpId();
		
		// remove with companyId, workplaceId & year
		this.wkpNormalSettingRepository.remove(companyId, wkpId, year);
		this.wkpFlexSettingRepository.remove(companyId, wkpId, year);
		this.wkpDeforLaborSettingRepository.remove(companyId, wkpId, year);
		this.wkpRegularWorkTimeRepository.remove(companyId, wkpId);
		this.wkpTransLaborTimeRepository.remove(companyId, wkpId);
	}

}
