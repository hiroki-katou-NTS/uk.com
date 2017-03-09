/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.DateTimeConstraints;
import nts.arc.time.YearMonth;
import nts.gul.collection.ListUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class AccidentInsuranceRateAddCommandHandler.
 */
@Stateless
public class AccidentInsuranceRateDeleteCommandHandler extends CommandHandler<AccidentInsuranceRateDeleteCommand> {
	/** The Constant BEGIN_FIRST. */
	public static final int BEGIN_FIRST = 0;

	/** The Constant BEGIN_SENDCOND. */
	public static final int BEGIN_SECOND = 1;

	/** The Constant SIZE_TWO. */
	public static final int SIZE_TWO = 2;
	/** The accident insurance rate repository. */

	/** The Constant YEAR_MONTH_MAX. */
	public static final YearMonth YEAR_MONTH_MAX = YearMonth.of(DateTimeConstraints.LIMIT_YEAR.max(),
			DateTimeConstraints.LIMIT_MONTH.max());
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
		CompanyCode companyCode = new CompanyCode(loginUserContext.companyCode());
		// get command
		AccidentInsuranceRateDeleteCommand command = context.getCommand();

		List<AccidentInsuranceRate> lstAccidentInsuranceRate = this.accidentInsuranceRateRepo.findAll(companyCode);
		if (!ListUtil.isEmpty(lstAccidentInsuranceRate)) {
			// history first
			if (lstAccidentInsuranceRate.get(BEGIN_FIRST).getHistoryId()
					.equals(command.getAccidentInsuranceRateDeleteDto().getCode())) {
				// Start Begin remove
				this.accidentInsuranceRateRepo.remove(companyCode,
						command.getAccidentInsuranceRateDeleteDto().getCode(),
						command.getAccidentInsuranceRateDeleteDto().getVersion());
				if (lstAccidentInsuranceRate.size() >= SIZE_TWO) {
					Optional<AccidentInsuranceRate> optionalRateUpdate = this.accidentInsuranceRateRepo
							.findById(companyCode, lstAccidentInsuranceRate.get(BEGIN_SECOND).getHistoryId());
					if (optionalRateUpdate.isPresent()) {
						this.accidentInsuranceRateRepo.updateYearMonth(optionalRateUpdate.get(), YEAR_MONTH_MAX);
					}
				}
			}
		}
	}

}
