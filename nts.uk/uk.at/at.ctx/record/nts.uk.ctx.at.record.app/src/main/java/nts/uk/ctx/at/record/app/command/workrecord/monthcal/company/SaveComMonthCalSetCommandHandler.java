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
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveComMonthCalSetCommandHandler.
 */
@Stateless
public class SaveComMonthCalSetCommandHandler extends CommandHandler<SaveComMonthCalSetCommand> {

	/** The com defor labor month act cal set repo. */
	@Inject
	private ComDeforLaborMonthActCalSetRepository comDeforLaborMonthActCalSetRepo;

	/** The com flex month act cal set repo. */
	@Inject
	private ComFlexMonthActCalSetRepository comFlexMonthActCalSetRepo;

	/** The com regula month act cal set repo. */
	@Inject
	private ComRegulaMonthActCalSetRepository comRegulaMonthActCalSetRepo;

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

		ComDeforLaborMonthActCalSet comDeforLaborMonthActCalSet = new ComDeforLaborMonthActCalSet(context.getCommand());
		ComFlexMonthActCalSet comFlexMonthActCalSet = new ComFlexMonthActCalSet(context.getCommand());
		ComRegulaMonthActCalSet comRegulaMonthActCalSet = new ComRegulaMonthActCalSet(context.getCommand());

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
