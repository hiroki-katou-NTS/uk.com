/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeComRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeComRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveComStatWorkTimeSetCommandHandler.
 */
@Stateless
@Transactional
public class SaveComStatWorkTimeSetCommandHandler extends CommandHandler<SaveComStatWorkTimeSetCommand> {

	/** The trans labor time repository. */
	@Inject
	private DeforLaborTimeComRepo transLaborTimeRepository;

	/** The regular labor time repository. */
	@Inject
	private RegularLaborTimeComRepo regularLaborTimeRepository;

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	/*
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveComStatWorkTimeSetCommand> context) {

		SaveComStatWorkTimeSetCommand command = context.getCommand();
		int year = command.getYear();
		String companyId = AppContexts.user().companyId();

		List<MonthlyWorkTimeSetCom> comNormalSetting = command.regular(companyId);
		List<MonthlyWorkTimeSetCom> comFlexSetting = command.flex(companyId);
		List<MonthlyWorkTimeSetCom> comDeforLaborSetting = command.defor(companyId);
		RegularLaborTimeCom comRegularLaborTime = command.regurlarLabor(companyId);
		DeforLaborTimeCom comTransLaborTime = command.deforLabor(companyId);

		List<MonthlyWorkTimeSetCom> regulars = monthlyWorkTimeSetRepo.findCompany(companyId, LaborWorkTypeAttr.REGULAR_LABOR, year);
		// Check info ComNormalSetting if exist -> update into db / not exist -> insert into DB
		addOrUpdate(comNormalSetting, regulars);

		List<MonthlyWorkTimeSetCom> flex = monthlyWorkTimeSetRepo.findCompany(companyId, LaborWorkTypeAttr.FLEX, year);
		// Check info ComNormalSetting if exist -> update into db / not exist -> insert into DB
		addOrUpdate(comFlexSetting, flex);

		List<MonthlyWorkTimeSetCom> defor = monthlyWorkTimeSetRepo.findCompany(companyId, LaborWorkTypeAttr.DEFOR_LABOR, year);
		// Check info ComNormalSetting if exist -> update into db / not exist -> insert into DB
		addOrUpdate(comDeforLaborSetting, defor);

		Optional<RegularLaborTimeCom> optComRegularSet = regularLaborTimeRepository.find(companyId);
		// Check info ComRegularLaborTime if exist -> update into db / not exist -> insert into DB
		if (optComRegularSet.isPresent()) {
			regularLaborTimeRepository.update(comRegularLaborTime);
		} else {
			regularLaborTimeRepository.create(comRegularLaborTime);
		}

		Optional<DeforLaborTimeCom> optComTransSet = transLaborTimeRepository.find(companyId);
		// Check info ComTransLaborTime if exist -> update into db / not exist -> insert into DB
		if (optComTransSet.isPresent()) {
			transLaborTimeRepository.update(comTransLaborTime);
		} else {
			transLaborTimeRepository.create(comTransLaborTime);
		}

	}
	
	private void addOrUpdate (List<MonthlyWorkTimeSetCom> n, List<MonthlyWorkTimeSetCom> o) {
		
		n.stream().forEach(mwtn -> {
			if (o.stream().filter(mwto -> mwto.getYm().equals(mwtn.getYm())).findFirst().isPresent()) {
				monthlyWorkTimeSetRepo.update(mwtn);
			} else {
				monthlyWorkTimeSetRepo.add(mwtn);
			}
		});
	}
}
