package nts.uk.ctx.at.record.app.find.monthly.finder;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.AffiliationInfoOfMonthlyDto;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthlyRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.MonthlyFinderFacade;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Stateless
public class AffiliationInfoOfMonthlyFinder extends MonthlyFinderFacade {
	
	@Inject
	private AffiliationInfoOfMonthlyRepository repo;

	@Override
	@SuppressWarnings("unchecked")
	public AffiliationInfoOfMonthlyDto find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		return AffiliationInfoOfMonthlyDto.from(this.repo.find(employeeId, yearMonth, closureId, closureDate).orElse(null));
	}

}
