package nts.uk.ctx.at.shared.ac.workplace.group;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceExportServiceAdapter;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceInforExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

@Stateless
public class WorkplaceExportServiceAdapterImpl implements WorkplaceExportServiceAdapter{

	@Inject
	private WorkplacePub workplacePub;

	@Override
	public List<WorkplaceInforExport> getWorkplaceInforByWkpIds(String companyId, List<String> listWorkplaceId,
			GeneralDate baseDate) {
		List<WorkplaceInforExport> exports = workplacePub.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate)
				.stream().map(x-> new WorkplaceInforExport(x.getWorkplaceId(), x.getHierarchyCode(), 
						x.getWorkplaceCode(), x.getWorkplaceName(), x.getWorkplaceDisplayName(), 
						x.getWorkplaceGenericName(), x.getWorkplaceExternalCode())).collect(Collectors.toList());
		return exports;
	}
}
