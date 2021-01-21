/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteWkpStatWorkTimeSetCommandHandler.
 */
@Stateless
public class DeleteWkpStatWorkTimeSetCommandHandler extends CommandHandler<DeleteWkpStatWorkTimeSetCommand> {

	/** The trans labor time repository. */
	@Inject
	private DeforLaborTimeWkpRepo transLaborTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private RegularLaborTimeWkpRepo regularLaborTimeRepository;

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	/*
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteWkpStatWorkTimeSetCommand> context) {
		DeleteWkpStatWorkTimeSetCommand command = context.getCommand();

		String companyId = AppContexts.user().companyId();
		int year = command.getYear();
		String wkpId = command.getWorkplaceId();

		// remove domains belong to year
		monthlyWorkTimeSetRepo.removeWorkplace(companyId, wkpId, year);

		// set isOverOneYear == true
		command.setOverOneYear(true);
		
		// if isOverOneYearRegister == false, remove remain domains
		if (!this.isOverOneYearRegister(companyId, wkpId)) {
			transLaborTimeRepository.find(companyId, wkpId)
					.ifPresent((setting) -> transLaborTimeRepository.remove(companyId, wkpId));
			regularLaborTimeRepository.find(companyId, wkpId)
					.ifPresent((setting) -> regularLaborTimeRepository.remove(companyId, wkpId));

			// set isOverOneYear == false
			command.setOverOneYear(false);
		}		
	}

	/**
	 * Checks if is over one year register.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return true, if is over one year register
	 */
	public boolean isOverOneYearRegister(String cid, String wkpId) {

		// find list wkp normal setting register
		List<MonthlyWorkTimeSetWkp> listWkpNormalSetting = monthlyWorkTimeSetRepo.findWorkplace(cid, wkpId, LaborWorkTypeAttr.REGULAR_LABOR);

		// check list wkp normal setting > 0
		if (!listWkpNormalSetting.isEmpty() && listWkpNormalSetting.size() > 0) {
			return true;
		}
		return false;

	}

}
