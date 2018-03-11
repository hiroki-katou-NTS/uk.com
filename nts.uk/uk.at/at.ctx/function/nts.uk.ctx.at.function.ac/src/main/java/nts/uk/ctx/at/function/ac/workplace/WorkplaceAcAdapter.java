package nts.uk.ctx.at.function.ac.workplace;

import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;

@Stateless
public class WorkplaceAcAdapter implements WorkplaceAdapter {

	@Inject
	private SyWorkplacePub workplacePub;

	@Override
	public WorkplaceImport getWorlkplaceHistory(String employeeId, GeneralDate baseDate) {
		
		Optional<SWkpHistExport> workplaceHistOpt = workplacePub.findBySid(employeeId, baseDate);
		if (!workplaceHistOpt.isPresent())
			throw new RuntimeException("Not found Workplace History domain!");
		SWkpHistExport wp = workplaceHistOpt.get();
		return WorkplaceImport.builder().dateRange(wp.getDateRange()).employeeId(wp.getEmployeeId())
				.wkpDisplayName(wp.getWkpDisplayName()).workplaceCode(wp.getWorkplaceCode())
				.workplaceId(wp.getWorkplaceId()).workplaceName(wp.getWorkplaceName()).build();
	}

}
