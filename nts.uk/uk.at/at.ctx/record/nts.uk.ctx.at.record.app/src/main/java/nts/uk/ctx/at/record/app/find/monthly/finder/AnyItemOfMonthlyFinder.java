package nts.uk.ctx.at.record.app.find.monthly.finder;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.AnyItemOfMonthlyDto;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.MonthlyFinderFacade;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Stateless
public class AnyItemOfMonthlyFinder extends MonthlyFinderFacade {
	
	@Inject
	private AnyItemOfMonthlyRepository repo;

	@Override
	@SuppressWarnings("unchecked")
	public AnyItemOfMonthlyDto find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		return AnyItemOfMonthlyDto.from(this.repo.findByMonthlyAndClosure(employeeId, yearMonth, closureId, closureDate));
	}

}
