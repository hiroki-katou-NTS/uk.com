/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteShainStatWorkTimeSetCommandHandler.
 */
@Stateless
public class DeleteEmpStatWorkTimeSetCommandHandler extends CommandHandler<DeleteEmpStatWorkTimeSetCommand> {

	/** The trans labor time repository. */
	@Inject
	private DeforLaborTimeEmpRepo transLaborTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private RegularLaborTimeEmpRepo regularLaborTimeRepository;

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	/*
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteEmpStatWorkTimeSetCommand> context) {
		
		DeleteEmpStatWorkTimeSetCommand command = context.getCommand();

		String companyId = AppContexts.user().companyId();
		int year = command.getYear();
		String emplCode = command.getEmploymentCode();

		// remove domains belong to year
		monthlyWorkTimeSetRepo.removeEmployment(companyId, emplCode, year);

		// set isOverOneYear == true
		command.setOverOneYear(true);
		
		// if isOverOneYearRegister == false, remove remain domains
		if (!this.isOverOneYearRegister(companyId, emplCode)) {
			regularLaborTimeRepository.findById(companyId, emplCode)
					.ifPresent((setting -> regularLaborTimeRepository.delete(companyId, emplCode)));
			transLaborTimeRepository.find(companyId, emplCode)
					.ifPresent((setting -> transLaborTimeRepository.delete(companyId, emplCode)));

			// set isOverOneYear == false
			command.setOverOneYear(false);
		}

	}

	/**
	 * Checks if is over one year register.
	 *
	 * @param cid
	 *            the cid
	 * @param emplCode
	 *            the empl code
	 * @return true, if is over one year register
	 */
	public boolean isOverOneYearRegister(String cid, String emplCode) {

		// find list emp normal setting register
		List<MonthlyWorkTimeSetEmp> listEmpNormalSetting = monthlyWorkTimeSetRepo.findEmployment(cid, emplCode, LaborWorkTypeAttr.REGULAR_LABOR);

		// check list emp normal setting > 0
		if (!listEmpNormalSetting.isEmpty() && listEmpNormalSetting.size() > 0) {
			return true;
		}
		return false;

	}

}
