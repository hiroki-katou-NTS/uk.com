package nts.uk.ctx.sys.shared.ac;

import java.util.Optional;

import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.sys.shared.dom.employee.syworkplace.SyaWkpHistAdapter;
import nts.uk.ctx.sys.shared.dom.employee.syworkplace.SyaWkpHistImport;

public class SyaWkpHistAdapterImpl implements SyaWkpHistAdapter {
	
	@Inject
	private WorkplacePub workplacePub;

	@Override
	public Optional<SyaWkpHistImport> findBySid(String employeeId, GeneralDate baseDate) {
		Optional<SWkpHistExport> optExport = workplacePub.findBySidNew(employeeId, baseDate);
		if (optExport.isPresent()) {
			val export = optExport.get();
			return Optional.of(new SyaWkpHistImport(
					export.getEmployeeId(), 
					export.getWorkplaceId(), 
					export.getWorkplaceName(),
					export.getDateRange()));
		} else {
			return Optional.empty();
		}
	}
}
