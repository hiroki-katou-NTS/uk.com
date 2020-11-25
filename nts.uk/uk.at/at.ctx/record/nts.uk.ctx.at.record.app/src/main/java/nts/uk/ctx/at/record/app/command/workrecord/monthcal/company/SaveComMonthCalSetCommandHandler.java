/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.company;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSetRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveComMonthCalSetCommandHandler.
 */
@Stateless
public class SaveComMonthCalSetCommandHandler extends CommandHandler<SaveComMonthCalSetCommand> {

	/** The com defor labor month act cal set repo. */
	@Inject
	private ComDeforLaborMonthActCalSetRepo comDeforLaborMonthActCalSetRepo;

	/** The com flex month act cal set repo. */
	@Inject
	private ComFlexMonthActCalSetRepo comFlexMonthActCalSetRepo;

	/** The com regula month act cal set repo. */
	@Inject
	private ComRegulaMonthActCalSetRepo comRegulaMonthActCalSetRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveComMonthCalSetCommand> context) {
		String cid = AppContexts.user().companyId();

		ComDeforLaborMonthActCalSet comDeforLaborMonthActCalSet = context.getCommand().defor(cid);
		ComFlexMonthActCalSet comFlexMonthActCalSet = context.getCommand().flex(cid);
		ComRegulaMonthActCalSet comRegulaMonthActCalSet = context.getCommand().regular(cid);

		Optional<ComDeforLaborMonthActCalSet> optComDeforLaborMonthActCalSet = comDeforLaborMonthActCalSetRepo.find(cid);
		if (optComDeforLaborMonthActCalSet.isPresent()) {
			comDeforLaborMonthActCalSetRepo.update(comDeforLaborMonthActCalSet);
		} else {
			comDeforLaborMonthActCalSetRepo.add(comDeforLaborMonthActCalSet);
		}

		Optional<ComFlexMonthActCalSet> optComFlexMonthActCalSet = comFlexMonthActCalSetRepo.find(cid);
		if (optComFlexMonthActCalSet.isPresent()) {
			comFlexMonthActCalSetRepo.update(comFlexMonthActCalSet);
		} else {
			comFlexMonthActCalSetRepo.add(comFlexMonthActCalSet);
		}

		Optional<ComRegulaMonthActCalSet> optComRegulaMonthActCalSet = comRegulaMonthActCalSetRepo.find(cid);
		if (optComRegulaMonthActCalSet.isPresent()) {
			comRegulaMonthActCalSetRepo.update(comRegulaMonthActCalSet);
		} else {
			comRegulaMonthActCalSetRepo.add(comRegulaMonthActCalSet);
		}

	}
}
