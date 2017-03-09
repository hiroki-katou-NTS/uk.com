/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.DateTimeConstraints;
import nts.arc.time.YearMonth;
import nts.gul.collection.ListUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class UnemployeeInsuranceRateDeleteCommandHandler extends CommandHandler<UnemployeeInsuranceRateDeleteCommand> {

	/** The Constant BEGIN_FIRST. */
	public static final int BEGIN_FIRST = 0;

	/** The Constant BEGIN_SENDCOND. */
	public static final int BEGIN_SECOND = 1;

	/** The Constant SIZE_TWO. */
	public static final int SIZE_TWO = 2;

	/** The Constant YEAR_MONTH_MAX. */
	public static final YearMonth YEAR_MONTH_MAX = YearMonth.of(DateTimeConstraints.LIMIT_YEAR.max(),
			DateTimeConstraints.LIMIT_MONTH.max());

	/** CompanyRepository */
	@Inject
	private UnemployeeInsuranceRateRepository unemployeeInsuranceRateRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UnemployeeInsuranceRateDeleteCommand> context) {
		// get user login info
		LoginUserContext loginUserContext = AppContexts.user();
		// get companyCode by user login
		CompanyCode companyCode = new CompanyCode(loginUserContext.companyCode());
		// get command
		UnemployeeInsuranceRateDeleteCommand command = context.getCommand();

		List<UnemployeeInsuranceRate> lstUnemployeeInsuranceRate = unemployeeInsuranceRateRepository
				.findAll(companyCode);
		if (!ListUtil.isEmpty(lstUnemployeeInsuranceRate)) {
			// history first
			if (lstUnemployeeInsuranceRate.get(BEGIN_FIRST).getHistoryId().equals(command.getCode())) {
				// Start Begin remove
				this.unemployeeInsuranceRateRepository.remove(companyCode, command.getCode(), command.getVersion());
				if (lstUnemployeeInsuranceRate.size() >= SIZE_TWO) {
					UnemployeeInsuranceRate rateUpdate = lstUnemployeeInsuranceRate.get(BEGIN_SECOND);
					this.unemployeeInsuranceRateRepository.updateYearMonth(rateUpdate, YEAR_MONTH_MAX);
				}
			}
		}
	}

}
