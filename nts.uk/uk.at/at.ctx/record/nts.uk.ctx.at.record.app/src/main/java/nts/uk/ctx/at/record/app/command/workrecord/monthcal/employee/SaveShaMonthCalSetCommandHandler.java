/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSetRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveShaMonthCalSetCommandHandler.
 */
@Stateless
public class SaveShaMonthCalSetCommandHandler extends CommandHandler<SaveShaMonthCalSetCommand> {

	/** The defor labor month act cal set repo. */
	@Inject
	private ShaDeforLaborMonthActCalSetRepo deforLaborMonthActCalSetRepo;

	/** The flex month act cal set repo. */
	@Inject
	private ShaFlexMonthActCalSetRepo flexMonthActCalSetRepo;

	/** The regula month act cal set repo. */
	@Inject
	private ShaRegulaMonthActCalSetRepo regulaMonthActCalSetRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveShaMonthCalSetCommand> context) {
		
		// get company id
		String cid = AppContexts.user().companyId();
		
		// get employee id
		SaveShaMonthCalSetCommand command = context.getCommand();
		String sid = command.getEmployeeId();

		ShaDeforLaborMonthActCalSet shaDeforLaborMonthActCalSet = context.getCommand().defor(cid);
		ShaFlexMonthActCalSet shaFlexMonthActCalSet = context.getCommand().flex(cid);
		ShaRegulaMonthActCalSet shaRegulaMonthActCalSet = context.getCommand().regular(cid);

		Optional<ShaDeforLaborMonthActCalSet> optShaDeforLaborMonthActCalSet = deforLaborMonthActCalSetRepo.find(cid,
				sid);

		if (optShaDeforLaborMonthActCalSet.isPresent()) {
			deforLaborMonthActCalSetRepo.update(shaDeforLaborMonthActCalSet);
		} else {
			deforLaborMonthActCalSetRepo.add(shaDeforLaborMonthActCalSet);
		}

		Optional<ShaFlexMonthActCalSet> optShaFlexMonthActCalSet = flexMonthActCalSetRepo.find(cid, sid);

		if (optShaFlexMonthActCalSet.isPresent()) {
			flexMonthActCalSetRepo.update(shaFlexMonthActCalSet);
		} else {
			flexMonthActCalSetRepo.add(shaFlexMonthActCalSet);
		}

		Optional<ShaRegulaMonthActCalSet> optShaRegulaMonthActCalSet = regulaMonthActCalSetRepo.find(cid, sid);

		if (optShaRegulaMonthActCalSet.isPresent()) {
			regulaMonthActCalSetRepo.update(shaRegulaMonthActCalSet);
		} else {
			regulaMonthActCalSetRepo.add(shaRegulaMonthActCalSet);
		}
	}

}
