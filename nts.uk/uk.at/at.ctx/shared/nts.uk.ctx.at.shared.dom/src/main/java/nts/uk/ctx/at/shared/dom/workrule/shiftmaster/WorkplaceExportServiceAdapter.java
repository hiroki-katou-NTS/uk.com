package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface WorkplaceExportServiceAdapter {
	public List<WorkplaceInforExport> getWorkplaceInforByWkpIds(String companyId, List<String> listWorkplaceId,
			GeneralDate baseDate);
}
