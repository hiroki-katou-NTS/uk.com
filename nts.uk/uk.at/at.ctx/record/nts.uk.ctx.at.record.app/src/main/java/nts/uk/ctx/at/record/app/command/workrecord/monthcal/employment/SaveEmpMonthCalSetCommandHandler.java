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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSetRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveEmpMonthCalSetCommandHandler.
 */
@Stateless
public class SaveEmpMonthCalSetCommandHandler extends CommandHandler<SaveEmpMonthCalSetCommand> {

	/** The defor labor month act cal set repo. */
	@Inject
	private EmpDeforLaborMonthActCalSetRepo deforLaborMonthActCalSetRepo;

	/** The flex month act cal set repo. */
	@Inject
	private EmpFlexMonthActCalSetRepo flexMonthActCalSetRepo;

	/** The regula month act cal set repo. */
	@Inject
	private EmpRegulaMonthActCalSetRepo regulaMonthActCalSetRepo;

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
		String empCode = command.getEmploymentCode();

		EmpDeforLaborMonthActCalSet empDeforLaborMonthActCalSet = context.getCommand().defor(cid);
		EmpFlexMonthActCalSet empFlexMonthActCalSet = context.getCommand().flex(cid);
		EmpRegulaMonthActCalSet empRegulaMonthActCalSet = context.getCommand().regular(cid);

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
