package nts.uk.ctx.sys.auth.ac.employee.employeeinfo;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmpWorkplaceHistoryAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmpWorkplaceHistoryImport;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class EmpWorkplaceHistoryAdapterImpl implements EmpWorkplaceHistoryAdapter {

	@Inject
	private SyWorkplacePub syWorkplacePub;
	
	private EmpWorkplaceHistoryImport toImport(SWkpHistExport ex){
		return new EmpWorkplaceHistoryImport ( 
				ex.getEmployeeId(),
				ex.getWorkplaceId(),
				ex.getWorkplaceCode(),
				ex.getWorkplaceName(),
				ex.getWkpDisplayName(),
				ex.getDateRange());
	}
		
	
	public Optional<EmpWorkplaceHistoryImport> findBySid(String employeeID, GeneralDate baseDate) {
		//Lay request 30 NEW
		return syWorkplacePub.findBySidNew(AppContexts.user().companyId(), employeeID, baseDate).map(c -> toImport(c));
	}


	@Override
	public List<String> getListWorkPlaceIDByDate(GeneralDate date) {
		 List<String> data = syWorkplacePub.findListWorkplaceIdByBaseDate(date);
		return data;
	}
}
