/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveShainStatWorkTimeSetCommandHandler.
 */
@Stateless
@Transactional
public class SaveEmpStatWorkTimeSetCommandHandler extends CommandHandler<SaveEmpStatWorkTimeSetCommand> {

	/** The trans labor time repository. */
	@Inject
	private DeforLaborTimeEmpRepo transLaborTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private RegularLaborTimeEmpRepo regularLaborTimeRepository;

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	/* 
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveEmpStatWorkTimeSetCommand> context) {

		SaveEmpStatWorkTimeSetCommand command = context.getCommand();
		int year = command.getYear();
		String emplCode = command.getEmploymentCode();
		String companyId = AppContexts.user().companyId();

		val normalSetting = command.regular(companyId);
		val flexSetting = command.flex(companyId);
		val deforLaborSetting = command.defor(companyId);
		val regularLaborTime = command.regurlarLabor(companyId);
		val deforLaborTime = command.deforLabor(companyId);
		
		val regulars = monthlyWorkTimeSetRepo.findEmployment(companyId, emplCode, LaborWorkTypeAttr.REGULAR_LABOR, year);
		addOrUpdate(normalSetting, regulars);

		val flex = monthlyWorkTimeSetRepo.findEmployment(companyId, emplCode, LaborWorkTypeAttr.FLEX, year);
		addOrUpdate(flexSetting, flex);

		val defor = monthlyWorkTimeSetRepo.findEmployment(companyId, emplCode, LaborWorkTypeAttr.DEFOR_LABOR, year);
		addOrUpdate(deforLaborSetting, defor);

		val optRegularTime = regularLaborTimeRepository.findById(companyId, emplCode);
		if (optRegularTime.isPresent()) {
			regularLaborTimeRepository.update(regularLaborTime);
		} else {
			regularLaborTimeRepository.add(regularLaborTime);
		}

		val optDeforTime = transLaborTimeRepository.find(companyId, emplCode);
		if (optDeforTime.isPresent()) {
			transLaborTimeRepository.update(deforLaborTime);
		} else {
			transLaborTimeRepository.add(deforLaborTime);
		}
	}
	
	private void addOrUpdate (List<MonthlyWorkTimeSetEmp> n, List<MonthlyWorkTimeSetEmp> o) {
		
		n.stream().forEach(mwtn -> {
			if (o.stream().filter(mwto -> mwto.getYm().equals(mwtn.getYm())).findFirst().isPresent()) {
				monthlyWorkTimeSetRepo.update(mwtn);
			} else {
				monthlyWorkTimeSetRepo.add(mwtn);
			}
		});
	}

}

