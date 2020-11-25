/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveWkpStatWorkTimeSetCommandHandler.
 */
@Stateless
@Transactional
public class SaveWkpStatWorkTimeSetCommandHandler extends CommandHandler<SaveWkpStatWorkTimeSetCommand> {

	/** The trans labor time repository. */
	@Inject
	private DeforLaborTimeWkpRepo transLaborTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private RegularLaborTimeWkpRepo regularLaborTimeRepository;

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	/* 
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveWkpStatWorkTimeSetCommand> context) {

		SaveWkpStatWorkTimeSetCommand command = context.getCommand();
		int year = command.getYear();
		String wkpId = command.getWorkplaceId();
		String companyId = AppContexts.user().companyId();

		val normalSetting = command.regular(companyId);
		val flexSetting = command.flex(companyId);
		val deforLaborSetting = command.defor(companyId);
		val regularLaborTime = command.regurlarLabor(companyId);
		val deforLaborTime = command.deforLabor(companyId);
		
		val regulars = monthlyWorkTimeSetRepo.findWorkplace(companyId, wkpId, LaborWorkTypeAttr.REGULAR_LABOR, year);
		addOrUpdate(normalSetting, regulars);

		val flex = monthlyWorkTimeSetRepo.findWorkplace(companyId, wkpId, LaborWorkTypeAttr.FLEX, year);
		addOrUpdate(flexSetting, flex);

		val defor = monthlyWorkTimeSetRepo.findWorkplace(companyId, wkpId, LaborWorkTypeAttr.DEFOR_LABOR, year);
		addOrUpdate(deforLaborSetting, defor);

		val optRegularTime = regularLaborTimeRepository.find(companyId, wkpId);
		if (optRegularTime.isPresent()) {
			regularLaborTimeRepository.update(regularLaborTime);
		} else {
			regularLaborTimeRepository.add(regularLaborTime);
		}

		val optDeforTime = transLaborTimeRepository.find(companyId, wkpId);
		if (optDeforTime.isPresent()) {
			transLaborTimeRepository.update(deforLaborTime);
		} else {
			transLaborTimeRepository.add(deforLaborTime);
		}
	}
	
	private void addOrUpdate (List<MonthlyWorkTimeSetWkp> n, List<MonthlyWorkTimeSetWkp> o) {
		
		n.stream().forEach(mwtn -> {
			if (o.stream().filter(mwto -> mwto.getYm().equals(mwtn.getYm())).findFirst().isPresent()) {
				monthlyWorkTimeSetRepo.update(mwtn);
			} else {
				monthlyWorkTimeSetRepo.add(mwtn);
			}
		});
	}

}
