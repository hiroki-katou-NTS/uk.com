/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeShaRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeShaRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveShainStatWorkTimeSetCommandHandler.
 */
@Stateless
@Transactional
public class SaveShainStatWorkTimeSetCommandHandler extends CommandHandler<SaveShainStatWorkTimeSetCommand> {

	/** The trans labor time repository. */
	@Inject
	private DeforLaborTimeShaRepo transLaborTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private RegularLaborTimeShaRepo regularLaborTimeRepository;

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	/* 
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveShainStatWorkTimeSetCommand> context) {

		SaveShainStatWorkTimeSetCommand command = context.getCommand();
		int year = command.getYear();
		String employeeId = command.getEmployeeId();
		String companyId = AppContexts.user().companyId();

		val normalSetting = command.regular(companyId);
		val flexSetting = command.flex(companyId);
		val deforLaborSetting = command.defor(companyId);
		val regularLaborTime = command.regurlarLabor(companyId);
		val deforLaborTime = command.deforLabor(companyId);
		
		val regulars = monthlyWorkTimeSetRepo.findEmployee(companyId, employeeId, LaborWorkTypeAttr.REGULAR_LABOR, year);
		addOrUpdate(normalSetting, regulars);

		val flex = monthlyWorkTimeSetRepo.findEmployee(companyId, employeeId, LaborWorkTypeAttr.FLEX, year);
		addOrUpdate(flexSetting, flex);

		val defor = monthlyWorkTimeSetRepo.findEmployee(companyId, employeeId, LaborWorkTypeAttr.DEFOR_LABOR, year);
		addOrUpdate(deforLaborSetting, defor);

		val optRegularTime = regularLaborTimeRepository.find(companyId, employeeId);
		if (optRegularTime.isPresent()) {
			regularLaborTimeRepository.update(regularLaborTime);
		} else {
			regularLaborTimeRepository.add(regularLaborTime);
		}

		val optDeforTime = transLaborTimeRepository.find(companyId, employeeId);
		if (optDeforTime.isPresent()) {
			transLaborTimeRepository.update(deforLaborTime);
		} else {
			transLaborTimeRepository.add(deforLaborTime);
		}
	}
	
	private void addOrUpdate (List<MonthlyWorkTimeSetSha> n, List<MonthlyWorkTimeSetSha> o) {
		
		n.stream().forEach(mwtn -> {
			if (o.stream().filter(mwto -> mwto.getYm().equals(mwtn.getYm())).findFirst().isPresent()) {
				monthlyWorkTimeSetRepo.update(mwtn);
			} else {
				monthlyWorkTimeSetRepo.add(mwtn);
			}
		});
	}

}
