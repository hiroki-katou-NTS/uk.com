/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeShaRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeShaRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteShainStatWorkTimeSetCommandHandler.
 */
@Stateless
public class DeleteShainStatWorkTimeSetCommandHandler extends CommandHandler<DeleteShainStatWorkTimeSetCommand> {

	/** The trans labor time repository. */
	@Inject
	private DeforLaborTimeShaRepo transLaborTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private RegularLaborTimeShaRepo regularLaborTimeRepository;

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	/*
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteShainStatWorkTimeSetCommand> context) {
		DeleteShainStatWorkTimeSetCommand command = context.getCommand();

		String companyId = AppContexts.user().companyId();
		int year = command.getYear();
		String employeeId = command.getEmployeeId();

		monthlyWorkTimeSetRepo.removeEmployee(companyId, employeeId, year);

		// set isOverOneYear == true
		command.setOverOneYear(true);

		// if isOverOneYearRegister == false, remove remain domains
		if (!this.isOverOneYearRegister(companyId, employeeId)) {

			transLaborTimeRepository.find(companyId, employeeId)
					.ifPresent((setting) -> transLaborTimeRepository.delete(companyId, employeeId));
			regularLaborTimeRepository.find(companyId, employeeId)
					.ifPresent((setting) -> regularLaborTimeRepository.delete(companyId, employeeId));

			// set isOverOneYear == false
			command.setOverOneYear(false);
		}

	}

	/**
	 * Checks if is over one year register.
	 *
	 * @param cid the cid
	 * @param employeeId
	 *            the employee id
	 * @return true, if is over one year register
	 */
	public boolean isOverOneYearRegister(String cid, String employeeId) {

		// find list sha normal setting register
		List<MonthlyWorkTimeSetSha> listShainNormalSetting = monthlyWorkTimeSetRepo
				.findEmployee(cid, employeeId, LaborWorkTypeAttr.REGULAR_LABOR);

		// check list sha normal setting > 0
		if (!listShainNormalSetting.isEmpty() && listShainNormalSetting.size() > 0) {
			return true;
		}
		return false;

	}

}
