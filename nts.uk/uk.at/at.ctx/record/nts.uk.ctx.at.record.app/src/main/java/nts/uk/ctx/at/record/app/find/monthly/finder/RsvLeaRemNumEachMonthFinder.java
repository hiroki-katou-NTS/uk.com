package nts.uk.ctx.at.record.app.find.monthly.finder;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.RsvLeaRemNumEachMonthDto;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.MonthlyFinderFacade;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Stateless
public class RsvLeaRemNumEachMonthFinder extends MonthlyFinderFacade {
	
	@Inject
	private RsvLeaRemNumEachMonthRepository repo;

	@Override
	@SuppressWarnings("unchecked")
	public RsvLeaRemNumEachMonthDto find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		return RsvLeaRemNumEachMonthDto.from(this.repo.find(employeeId, yearMonth, closureId, closureDate).orElse(null));
	}

}
