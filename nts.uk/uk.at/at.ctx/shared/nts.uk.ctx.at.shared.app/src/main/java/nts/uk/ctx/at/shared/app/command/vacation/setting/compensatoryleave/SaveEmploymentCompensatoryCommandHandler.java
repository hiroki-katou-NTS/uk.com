/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.CompensatoryAcquisitionUseDto;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.CompensatoryDigestiveTimeUnitDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveEmploymentCompensatoryCommandHandler.
 */
@Stateless
public class SaveEmploymentCompensatoryCommandHandler extends CommandHandler<SaveEmploymentCompensatoryCommand> {

	/** The compens leave em set repository. */
	@Inject
	CompensLeaveEmSetRepository compensLeaveEmSetRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveEmploymentCompensatoryCommand> context) {
		SaveEmploymentCompensatoryCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		CompensatoryLeaveEmSetting findItem = compensLeaveEmSetRepository.find(companyId, command.getEmploymentCode());

		/*if (findItem != null) {
			// check disable get data
			if (command.getIsManaged() != ManageDistinct.YES.value) {
				//ExpirationTime
				CompensatoryAcquisitionUseDto acqui = command.getCompensatoryAcquisitionUse();
				acqui.setExpirationTime(findItem.getCompensatoryAcquisitionUse().getExpirationTime().value);
				//PreemptionPermit
				acqui.setPreemptionPermit(findItem.getCompensatoryAcquisitionUse().getPreemptionPermit().value);
				// ManageByTime
				CompensatoryDigestiveTimeUnitDto digest2 = command.getCompensatoryDigestiveTimeUnit();
				digest2.setIsManageByTime(findItem.getCompensatoryDigestiveTimeUnit().getIsManageByTime().value);
			}
			// DigestiveTimeUnit
			if ((command.getCompensatoryDigestiveTimeUnit().getIsManageByTime() != ManageDistinct.YES.value)
					|| (command.getIsManaged() != ManageDistinct.YES.value)) {
				CompensatoryDigestiveTimeUnitDto digest = command.getCompensatoryDigestiveTimeUnit();
				digest.setDigestiveUnit(findItem.getCompensatoryDigestiveTimeUnit().getDigestiveUnit().value);
			}
		}*/
		CompensatoryLeaveEmSetting domain = command.toDomain(companyId);

		if (findItem != null) {
			compensLeaveEmSetRepository.update(domain);
		} else {
			compensLeaveEmSetRepository.insert(domain);
		}
	}

}
