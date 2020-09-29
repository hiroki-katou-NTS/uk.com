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
 * The Class DelShaMonthCalSetCommandHandler.
 */
@Stateless
public class DelShaMonthCalSetCommandHandler extends CommandHandler<DelShaMonthCalSetCommand> {

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
	protected void handle(CommandHandlerContext<DelShaMonthCalSetCommand> context) {

		// get company id
		String cid = AppContexts.user().companyId();

		// get employee id
		DelShaMonthCalSetCommand command = context.getCommand();
		String sid = command.getSid();

		Optional<ShaDeforLaborMonthActCalSet> optShaDeforLaborMonthActCalSet = deforLaborMonthActCalSetRepo
				.find(cid, sid);
		if (optShaDeforLaborMonthActCalSet.isPresent()) {
			deforLaborMonthActCalSetRepo.remove(cid, sid);
		}

		Optional<ShaFlexMonthActCalSet> optShaFlexMonthActCalSet = flexMonthActCalSetRepo.find(cid,
				sid);

		if (optShaFlexMonthActCalSet.isPresent()) {
			flexMonthActCalSetRepo.remove(cid, sid);
		}

		Optional<ShaRegulaMonthActCalSet> optShaRegulaMonthActCalSet = regulaMonthActCalSetRepo
				.find(cid, sid);

		if (optShaRegulaMonthActCalSet.isPresent()) {
			regulaMonthActCalSetRepo.remove(cid, sid);
		}

	}

}
