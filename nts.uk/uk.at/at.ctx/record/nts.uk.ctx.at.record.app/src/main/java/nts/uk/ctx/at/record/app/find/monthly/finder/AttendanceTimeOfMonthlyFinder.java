package nts.uk.ctx.at.record.app.find.monthly.finder;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.AttendanceTimeOfMonthlyDto;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.MonthlyFinderFacade;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Stateless
public class AttendanceTimeOfMonthlyFinder extends MonthlyFinderFacade {

	@Inject
	private AttendanceTimeOfMonthlyRepository repo;
	
	@Override
	@SuppressWarnings("unchecked")
	public AttendanceTimeOfMonthlyDto find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		return AttendanceTimeOfMonthlyDto.from(this.repo.find(employeeId, yearMonth, closureId, closureDate).orElse(null));
	}

}
