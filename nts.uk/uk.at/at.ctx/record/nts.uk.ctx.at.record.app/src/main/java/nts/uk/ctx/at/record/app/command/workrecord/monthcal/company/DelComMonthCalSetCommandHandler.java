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
 * The Class DeleteCompanyCalMonthlyFlexCommandHandler.
 */
@Stateless
public class DelComMonthCalSetCommandHandler extends CommandHandler<DelComMonthCalSetCommand> {

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
	protected void handle(CommandHandlerContext<DelComMonthCalSetCommand> context) {
		String cid = AppContexts.user().companyId();
		
		Optional<ComDeforLaborMonthActCalSet> optComDeforLaborMonthActCalSet = comDeforLaborMonthActCalSetRepo
				.find(cid);
		if (optComDeforLaborMonthActCalSet.isPresent()) {
			comDeforLaborMonthActCalSetRepo.remove(cid);
		}
		
		Optional<ComFlexMonthActCalSet> optComFlexMonthActCalSet = comFlexMonthActCalSetRepo.find(cid);

		if (optComFlexMonthActCalSet.isPresent()) {
			comFlexMonthActCalSetRepo.remove(cid);
		}

		Optional<ComRegulaMonthActCalSet> optComRegulaMonthActCalSet = comRegulaMonthActCalSetRepo
				.find(cid);

		if (optComRegulaMonthActCalSet.isPresent()) {
			comRegulaMonthActCalSetRepo.remove(cid);
		}
	
	}

}
