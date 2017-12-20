/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDay;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class DefaultClosureServiceImpl.
 */
@Stateless
public class DefaultClosureServiceImpl implements ClosureService {

	/** The closure repository. */
	@Inject
	private ClosureRepository closureRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService#
	 * getClosurePeriod(int, nts.arc.time.YearMonth)
	 */
	@Override
	public DatePeriod getClosurePeriod(int closureId, YearMonth processingYm) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		//
		Optional<ClosureHistory> optClosureHistory = this.closureRepository
				.findBySelectedYearMonth(companyId, closureId, processingYm.v());

		// Check exist
		if (!optClosureHistory.isPresent()) {
			return null;
		}

		ClosureHistory closureHistory = optClosureHistory.get();

		ClosureDay closureDay = closureHistory.getClosureDate().getClosureDay();
		GeneralDate startDate = GeneralDate.ymd(processingYm.year(), processingYm.month(), 1);
		GeneralDate endDate = DateUtil.getLastDateOfMonth(processingYm.year(),
				processingYm.month());

		if (closureHistory.getClosureDate().getLastDayOfMonth() || !DateUtil
				.isDateOfMonth(processingYm.year(), processingYm.month(), closureDay.v())) {
			return new DatePeriod(startDate, endDate);
		}

		endDate = GeneralDate.ymd(processingYm.year(), processingYm.month(), closureDay.v());
		return new DatePeriod(startDate, endDate);
	}
}
