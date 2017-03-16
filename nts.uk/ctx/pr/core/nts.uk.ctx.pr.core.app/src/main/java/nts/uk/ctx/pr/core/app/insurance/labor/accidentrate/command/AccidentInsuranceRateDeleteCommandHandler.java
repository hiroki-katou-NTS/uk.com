/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.DateTimeConstraints;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class AccidentInsuranceRateAddCommandHandler.
 */
@Stateless
public class AccidentInsuranceRateDeleteCommandHandler extends CommandHandler<AccidentInsuranceRateDeleteCommand> {

	/** The Constant YEAR_MONTH_MAX. */
	public static final YearMonth YEAR_MONTH_MAX = YearMonth.of(DateTimeConstraints.LIMIT_YEAR.max(),
			DateTimeConstraints.LIMIT_MONTH.max());

	/** The accident insurance rate repo. */
	@Inject
	private AccidentInsuranceRateRepository accidentInsuranceRateRepo;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<AccidentInsuranceRateDeleteCommand> context) {

		// get user login info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyCode by user login
		String companyCode = loginUserContext.companyCode();

		// get command
		AccidentInsuranceRateDeleteCommand command = context.getCommand();

		// get first data (remove)
		Optional<AccidentInsuranceRate> optionalRemove = this.accidentInsuranceRateRepo.findFirstData(companyCode);

		if (optionalRemove.isPresent()
				&& optionalRemove.get().getHistoryId().equals(command.getAccidentInsuranceRateDeleteDto().getCode())) {

			// history first
			this.accidentInsuranceRateRepo.remove(companyCode, command.getAccidentInsuranceRateDeleteDto().getCode(),
					command.getAccidentInsuranceRateDeleteDto().getVersion());

			// get first data (update)
			Optional<AccidentInsuranceRate> optionalUpdate = this.accidentInsuranceRateRepo.findFirstData(companyCode);
			if (optionalUpdate.isPresent()) {
				this.accidentInsuranceRateRepo.updateYearMonth(optionalUpdate.get(),
						AccidentInsuranceRateDeleteCommandHandler.YEAR_MONTH_MAX);
			}
		}
	}

}
