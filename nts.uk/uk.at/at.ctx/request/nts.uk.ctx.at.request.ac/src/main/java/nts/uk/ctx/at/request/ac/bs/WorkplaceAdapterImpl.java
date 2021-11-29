package nts.uk.ctx.at.request.ac.bs;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.WorkplaceNameImported;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.shr.com.context.AppContexts;

@Stateless(name = "WorkplaceAdapterName")
public class WorkplaceAdapterImpl implements WorkplaceAdapter {

	@Inject
	private WorkplacePub workplacePub;

	@Override
	public List<WorkplaceNameImported> findWkpInfo(List<WorkplaceId> wplIds, GeneralDate baseDate) {
		String companyId = AppContexts.user().companyId();
		return wplIds.stream().map(wkpId -> {
			// [Inported]職場ID、基準日から職場情報を取得する
			Optional<SWkpHistExport> export = this.workplacePub.findByWkpIdNew(companyId, wkpId.v(), baseDate);
			return export
					.map(t -> new WorkplaceNameImported(t.getWorkplaceId(), t.getWorkplaceCode(), t.getWorkplaceName()))
					.orElse(null);
		}).collect(Collectors.toList());
	}

}
