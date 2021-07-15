package nts.uk.ctx.sys.auth.ac.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.pub.companyhistory.EmpComHisPub;
import nts.uk.ctx.sys.auth.dom.adapter.employee.EmpCompanyHistoryAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.employee.EmpEnrollPeriodImport;
import nts.uk.ctx.sys.auth.dom.adapter.employee.SecondSituation;
/**
 * 社員の所属会社履歴Adapter
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.権限管理.Imported.contexts.社員.社員の所属会社履歴Adapter
 * @author lan_lt
 *
 */
public class EmpCompanyHistoryAdapterImpl  implements EmpCompanyHistoryAdapter{
	@Inject
	private EmpComHisPub empComHisPub;
	@Override
	public List<EmpEnrollPeriodImport> getEnrollmentPeriod(List<String> lstEmpId, DatePeriod datePeriod) {
		return empComHisPub
				.getEnrollmentPeriod(lstEmpId, datePeriod).stream().map(c -> new EmpEnrollPeriodImport(c.getEmpId()
						, c.getDatePeriod(), SecondSituation.valueOf(c.getSecondedSituation().value)))
				.collect(Collectors.toList());
	}
	
}
