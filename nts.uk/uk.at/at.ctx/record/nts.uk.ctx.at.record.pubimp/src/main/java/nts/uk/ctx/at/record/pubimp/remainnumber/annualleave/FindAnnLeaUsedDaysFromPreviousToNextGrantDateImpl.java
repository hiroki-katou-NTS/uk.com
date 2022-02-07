package nts.uk.ctx.at.record.pubimp.remainnumber.annualleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.FindAnnLeaUsedDaysFromPreviousToNextGrantDate;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.FindAnnLeaUsedDaysFromPreviousToNextGrantDatePub;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;


@Stateless
public class FindAnnLeaUsedDaysFromPreviousToNextGrantDateImpl implements FindAnnLeaUsedDaysFromPreviousToNextGrantDatePub{
	
	@Inject
	private RecordDomRequireService requireService;
	@Inject
	private EmpEmployeeAdapter employeeAdapter;

	@Override
	public AnnualLeaveUsedDayNumber findUsedDays(String employeeId, GeneralDate criteriaDate) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		// 2022.02.07 - 3S - chinh.hm  - issues #122665- 追加 START
		val employee = employeeAdapter.findByEmpIdRequire(cacheCarrier,employeeId);
		
		return FindAnnLeaUsedDaysFromPreviousToNextGrantDate.findUsedDays( employeeId,  criteriaDate,
				 require,  cacheCarrier, employee);
		// 2022.02.07 - 3S - chinh.hm  - issues #122665- 追加 END
	}

}
