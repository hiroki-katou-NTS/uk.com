/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.employment;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveEmpMonthCalSetCommandHandler.
 */
@Stateless
public class SaveEmpMonthCalSetCommandHandler extends CommandHandler<SaveEmpMonthCalSetCommand> {

	/** The defor labor month act cal set repo. */
	@Inject
	private EmpDeforLaborMonthActCalSetRepository deforLaborMonthActCalSetRepo;

	/** The flex month act cal set repo. */
	@Inject
	private EmpFlexMonthActCalSetRepository flexMonthActCalSetRepo;

	/** The regula month act cal set repo. */
	@Inject
	private EmpRegulaMonthActCalSetRepository regulaMonthActCalSetRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveEmpMonthCalSetCommand> context) {

		// get company id
		String cid = AppContexts.user().companyId();

		// get employment code
		SaveEmpMonthCalSetCommand command = context.getCommand();
		String empCode = command.getEmplCode();

		EmpDeforLaborMonthActCalSet empDeforLaborMonthActCalSet = new EmpDeforLaborMonthActCalSet(context.getCommand());
		EmpFlexMonthActCalSet empFlexMonthActCalSet = new EmpFlexMonthActCalSet(context.getCommand());
		EmpRegulaMonthActCalSet empRegulaMonthActCalSet = new EmpRegulaMonthActCalSet(context.getCommand());

		Optional<EmpDeforLaborMonthActCalSet> optEmpDeforLaborMonthActCalSet = deforLaborMonthActCalSetRepo.find(cid, empCode);

		if (optEmpDeforLaborMonthActCalSet.isPresent()) {
			deforLaborMonthActCalSetRepo.update(empDeforLaborMonthActCalSet);
		} else {
			deforLaborMonthActCalSetRepo.add(empDeforLaborMonthActCalSet);
		}

		Optional<EmpFlexMonthActCalSet> optEmpFlexMonthActCalSet = flexMonthActCalSetRepo.find(cid, empCode);

		if (optEmpFlexMonthActCalSet.isPresent()) {
			flexMonthActCalSetRepo.update(empFlexMonthActCalSet);
		} else {
			flexMonthActCalSetRepo.add(empFlexMonthActCalSet);
		}

		Optional<EmpRegulaMonthActCalSet> optEmpRegulaMonthActCalSet = regulaMonthActCalSetRepo.find(cid, empCode);

		if (optEmpRegulaMonthActCalSet.isPresent()) {
			regulaMonthActCalSetRepo.update(empRegulaMonthActCalSet);
		} else {
			regulaMonthActCalSetRepo.add(empRegulaMonthActCalSet);
		}

	}

}
