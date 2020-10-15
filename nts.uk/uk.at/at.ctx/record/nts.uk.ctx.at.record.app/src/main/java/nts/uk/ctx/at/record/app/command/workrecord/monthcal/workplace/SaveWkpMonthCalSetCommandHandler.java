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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSetRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveWkpMonthCalSetCommandHandler.
 */
@Stateless
public class SaveWkpMonthCalSetCommandHandler extends CommandHandler<SaveWkpMonthCalSetCommand> {

	/** The defor labor month act cal set repo. */
	@Inject
	private WkpDeforLaborMonthActCalSetRepo deforLaborMonthActCalSetRepo;

	/** The flex month act cal set repo. */
	@Inject
	private WkpFlexMonthActCalSetRepo flexMonthActCalSetRepo;

	/** The regula month act cal set repo. */
	@Inject
	private WkpRegulaMonthActCalSetRepo regulaMonthActCalSetRepo;

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
		String wkpId = command.getWorkplaceId();

		WkpDeforLaborMonthActCalSet wkpDeforLaborMonthActCalSet = context.getCommand().defor(cid);
		WkpFlexMonthActCalSet wkpFlexMonthActCalSet = context.getCommand().flex(cid);
		WkpRegulaMonthActCalSet wkpRegulaMonthActCalSet = context.getCommand().regular(cid);

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
