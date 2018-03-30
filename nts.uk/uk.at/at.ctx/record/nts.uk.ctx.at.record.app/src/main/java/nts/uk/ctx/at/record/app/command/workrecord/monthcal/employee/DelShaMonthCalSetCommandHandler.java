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
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DelShaMonthCalSetCommandHandler.
 */
@Stateless
public class DelShaMonthCalSetCommandHandler extends CommandHandler<DelShaMonthCalSetCommand> {

	/** The defor labor month act cal set repo. */
	@Inject
	private ShaDeforLaborMonthActCalSetRepository deforLaborMonthActCalSetRepo;

	/** The flex month act cal set repo. */
	@Inject
	private ShaFlexMonthActCalSetRepository flexMonthActCalSetRepo;

	/** The regula month act cal set repo. */
	@Inject
	private ShaRegulaMonthActCalSetRepository regulaMonthActCalSetRepo;

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
