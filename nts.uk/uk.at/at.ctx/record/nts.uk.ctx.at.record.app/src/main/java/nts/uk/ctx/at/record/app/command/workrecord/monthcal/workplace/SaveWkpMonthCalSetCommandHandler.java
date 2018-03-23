/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.workplace;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveWkpMonthCalSetCommandHandler.
 */
@Stateless
public class SaveWkpMonthCalSetCommandHandler extends CommandHandler<SaveWkpMonthCalSetCommand> {

	/** The defor labor month act cal set repo. */
	@Inject
	private WkpDeforLaborMonthActCalSetRepository deforLaborMonthActCalSetRepo;

	/** The flex month act cal set repo. */
	@Inject
	private WkpFlexMonthActCalSetRepository flexMonthActCalSetRepo;

	/** The regula month act cal set repo. */
	@Inject
	private WkpRegulaMonthActCalSetRepository regulaMonthActCalSetRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveWkpMonthCalSetCommand> context) {

		// get company id
		String cid = AppContexts.user().companyId();

		// get workplace id
		SaveWkpMonthCalSetCommand command = context.getCommand();
		String wkpId = command.getWkpId();

		WkpDeforLaborMonthActCalSet wkpDeforLaborMonthActCalSet = new WkpDeforLaborMonthActCalSet(context.getCommand());
		WkpFlexMonthActCalSet wkpFlexMonthActCalSet = new WkpFlexMonthActCalSet(context.getCommand());
		WkpRegulaMonthActCalSet wkpRegulaMonthActCalSet = new WkpRegulaMonthActCalSet(context.getCommand());

		Optional<WkpDeforLaborMonthActCalSet> optWkpDeforLaborMonthActCalSet = deforLaborMonthActCalSetRepo.find(cid,
				wkpId);

		if (optWkpDeforLaborMonthActCalSet.isPresent()) {
			deforLaborMonthActCalSetRepo.update(wkpDeforLaborMonthActCalSet);
		} else {
			deforLaborMonthActCalSetRepo.add(wkpDeforLaborMonthActCalSet);
		}

		Optional<WkpFlexMonthActCalSet> optWkpFlexMonthActCalSet = flexMonthActCalSetRepo.find(cid, wkpId);

		if (optWkpFlexMonthActCalSet.isPresent()) {
			flexMonthActCalSetRepo.update(wkpFlexMonthActCalSet);
		} else {
			flexMonthActCalSetRepo.add(wkpFlexMonthActCalSet);
		}

		Optional<WkpRegulaMonthActCalSet> optWkpRegulaMonthActCalSet = regulaMonthActCalSetRepo.find(cid, wkpId);

		if (optWkpRegulaMonthActCalSet.isPresent()) {
			regulaMonthActCalSetRepo.update(wkpRegulaMonthActCalSet);
		} else {
			regulaMonthActCalSetRepo.add(wkpRegulaMonthActCalSet);
		}
	}

}
