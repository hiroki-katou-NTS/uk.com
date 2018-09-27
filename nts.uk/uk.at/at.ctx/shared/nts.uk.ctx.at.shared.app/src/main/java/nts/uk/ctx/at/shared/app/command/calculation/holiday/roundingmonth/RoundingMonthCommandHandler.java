/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.calculation.holiday.roundingmonth;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingMonth;
import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingMonthRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RoundingMonthCommandHandler.
 */
@Stateless
public class RoundingMonthCommandHandler extends CommandHandler<List<RoundingMonthCommand>>{

	@Inject
	private RoundingMonthRepository roundingMonthRepository;
	
	@Override
	protected void handle(CommandHandlerContext<List<RoundingMonthCommand>> context) {
		String companyId = AppContexts.user().companyId();
		roundingMonthRepository.addList(
				context.getCommand().stream().map(dto -> {
					return RoundingMonth.createFromJavaType(companyId, dto.getTimeItemId(), dto.getUnit(), dto.getRounding());
				}).collect(Collectors.toList())
		);
	}
}
