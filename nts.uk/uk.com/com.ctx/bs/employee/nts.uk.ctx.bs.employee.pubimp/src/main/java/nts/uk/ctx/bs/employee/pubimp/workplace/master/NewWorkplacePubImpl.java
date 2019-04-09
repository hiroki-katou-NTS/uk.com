package nts.uk.ctx.bs.employee.pubimp.workplace.master;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceExportService;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplaceInforExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

@Stateless
public class NewWorkplacePubImpl implements WorkplacePub {

	@Inject
	private WorkplaceExportService wkpExpService;

	@Override
	public List<WorkplaceInforExport> getWorkplaceInforByWkpIds(String companyId, List<String> listWorkplaceId,
			GeneralDate baseDate) {
		return wkpExpService.getWorkplaceInforFromWkpIds(companyId, listWorkplaceId, baseDate).stream()
				.map(i -> new WorkplaceInforExport(i.getWorkplaceId(), i.getHierarchyCode().v(),
						i.getWorkplaceCode().v(), i.getWorkplaceName().v(), i.getWorkplaceDisplayName().v(),
						i.getWorkplaceGeneric().v(),
						i.getWorkplaceExternalCode().isPresent() ? i.getWorkplaceExternalCode().get().v() : null))
				.collect(Collectors.toList());
	}

}
