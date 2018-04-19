package nts.uk.ctx.at.request.dom.settting.worktype.history.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistory;
import nts.uk.ctx.at.request.dom.settting.worktype.history.VacationHistoryPolicy;
import nts.uk.ctx.at.request.dom.settting.worktype.history.VacationHistoryRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class VacationHistoryPolicyImpl.
 */
@Stateless
public class VacationHistoryPolicyImpl implements VacationHistoryPolicy{
	
	/** The history repository. */
	@Inject
	private VacationHistoryRepository historyRepository;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.settting.worktype.history.VacationHistoryPolicy#validate(nts.arc.error.BundledBusinessException, java.lang.Boolean, nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistory)
	 */
	@Override
	public void validate(BundledBusinessException be, Boolean isCreated, PlanVacationHistory vacationHistory) {
		
		DatePeriod period = new DatePeriod(vacationHistory.start(), vacationHistory.end());
		Integer count = this.historyRepository.countByDatePeriod(vacationHistory.getCompanyId(), vacationHistory.getWorkTypeCode(), period,
				vacationHistory.identifier());

		if (count.intValue() > 0) {
			be.addMessage("Msg_106");
		}
		
		if (isCreated) {
			if (this.historyRepository.findByWorkTypeCode(vacationHistory.getCompanyId(), vacationHistory.getWorkTypeCode()).size() >= 20) {
				be.addMessage("Msg_976");
			}
		}
	}
}
