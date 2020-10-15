package nts.uk.ctx.at.shared.ac.workplace;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisImport;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

@Stateless
public class SharedAffWorkPlaceHisAdapterImpl implements SharedAffWorkPlaceHisAdapter{
	
//	@Inject
//	private SyWorkplacePub wkpPub;
	
	@Inject
	private WorkplacePub workplacePub;

	@Override
	public Optional<SharedAffWorkPlaceHisImport> getAffWorkPlaceHis(String employeeId, GeneralDate processingDate) {

		Optional<SWkpHistExport> opSWkpHistExport = this.workplacePub.findBySid(employeeId, processingDate);
		
		if (!opSWkpHistExport.isPresent()) {
			return Optional.empty();
		}
		
		SharedAffWorkPlaceHisImport sharedAffWorkPlaceHisImport = new SharedAffWorkPlaceHisImport(opSWkpHistExport.get().getDateRange(),
				opSWkpHistExport.get().getEmployeeId(), opSWkpHistExport.get().getWorkplaceId(),
				opSWkpHistExport.get().getWorkplaceCode(), opSWkpHistExport.get().getWorkplaceName(),
				opSWkpHistExport.get().getWkpDisplayName());
		
		return Optional.of(sharedAffWorkPlaceHisImport);
	}

	@Override
	public List<String> findParentWpkIdsByWkpId(String companyId, String workplaceId, GeneralDate date) {
		List<String> workPlaceIDList = this.workplacePub.getWorkplaceIdAndChildren(companyId, date, workplaceId);
		return workPlaceIDList;
	}
	
	@Override
	public List<String> findAffiliatedWorkPlaceIdsToRootRequire(CacheCarrier cacheCarrier, String companyId,String employeeId, GeneralDate baseDate) {
		//Require対応まち
		return this.workplacePub.findWpkIdsBySid(companyId ,employeeId, baseDate);
	}
	
	@Override
	public List<String> findAffiliatedWorkPlaceIdsToRoot(String companyId,String employeeId, GeneralDate baseDate) {
		return this.workplacePub.findWpkIdsBySid(companyId ,employeeId, baseDate);
	}

}
