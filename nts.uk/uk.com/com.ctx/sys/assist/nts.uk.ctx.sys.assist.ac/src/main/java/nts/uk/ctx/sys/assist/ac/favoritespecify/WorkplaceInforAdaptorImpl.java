package nts.uk.ctx.sys.assist.ac.favoritespecify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employee.workplace.export.WorkplaceExportPub;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplaceInforExport;
import nts.uk.ctx.sys.assist.dom.favorite.adapter.WorkplaceInforAdaptor;
import nts.uk.ctx.sys.assist.dom.favorite.adapter.WorkplaceInforImport;
import nts.uk.shr.com.context.AppContexts;

public class WorkplaceInforAdaptorImpl implements WorkplaceInforAdaptor {

	@Inject
	public WorkplaceExportPub pub;

	@Override
	public Map<String, WorkplaceInforImport> getWorkplaceInfor(List<String> lstWorkplaceID, GeneralDate baseDate) {
		Map<String, WorkplaceInforImport> result = new HashMap<String, WorkplaceInforImport>();
		List<WorkplaceInforExport> lstWkP = this.pub.getWkpRQ560(AppContexts.user().companyId(), lstWorkplaceID, baseDate);
		for (WorkplaceInforExport x : lstWkP) {
			result.put(x.getWorkplaceId(),
					new WorkplaceInforImport(x.getWorkplaceId(), x.getHierarchyCode(), x.getWorkplaceCode(),
							x.getWorkplaceName(), x.getWorkplaceDisplayName(), x.getWorkplaceGenericName(),
							x.getWorkplaceExternalCode()));
		}

		return result;
	}

}
