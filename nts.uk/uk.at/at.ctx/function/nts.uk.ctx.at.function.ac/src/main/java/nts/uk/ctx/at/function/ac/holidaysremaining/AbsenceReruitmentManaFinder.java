package nts.uk.ctx.at.function.ac.holidaysremaining;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AbsenceReruitmentManaAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AbsenceReruitmentManaImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AbsenceleaveCurrentMonthOfEmployeeImported;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.export.MonthlyAbsenceleaveRemainExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentManaQuery;

@Stateless
public class AbsenceReruitmentManaFinder implements AbsenceReruitmentManaAdapter {
	
	@Inject
	private AbsenceReruitmentManaQuery absenceReruitmentManaQuery;
	
	@Inject
	private MonthlyAbsenceleaveRemainExport monthlyAbsenceleaveRemainExport;

	@Override
	public List<AbsenceReruitmentManaImported> getAbsRecRemainAggregate(String employeeId, GeneralDate baseDate,
			YearMonth startMonth, YearMonth endMonth) {
		//270
		absenceReruitmentManaQuery.getAbsRecRemainAggregate(employeeId, baseDate, startMonth, endMonth);
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<AbsenceleaveCurrentMonthOfEmployeeImported> getDataCurrentMonthOfEmployee(String employeeId,
			YearMonth startMonth, YearMonth endMonth) {
		//260
		monthlyAbsenceleaveRemainExport.getDataCurrentMonthOfEmployee(employeeId, startMonth, endMonth);
		// TODO Auto-generated method stub
		return null;
	}

}
