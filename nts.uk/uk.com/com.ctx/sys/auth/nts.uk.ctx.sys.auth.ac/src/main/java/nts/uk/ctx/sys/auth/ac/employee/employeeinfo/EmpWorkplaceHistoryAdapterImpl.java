package nts.uk.ctx.sys.auth.ac.employee.employeeinfo;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmpWorkplaceHistoryAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmpWorkplaceHistoryImport;
@Stateless
public class EmpWorkplaceHistoryAdapterImpl implements EmpWorkplaceHistoryAdapter {

	@Inject
	private SyWorkplacePub syWorkplacePub;
	
	private EmpWorkplaceHistoryImport toImport(Optional<SWkpHistExport> ex){
		return new EmpWorkplaceHistoryImport ( 
				ex.get().getEmployeeId(),
				ex.get().getWorkplaceId(),
				ex.get().getWorkplaceCode(),
				ex.get().getWorkplaceName(),
				ex.get().getWkpDisplayName(),
				ex.get().getDateRange());
	}
		
	
	public Optional<EmpWorkplaceHistoryImport> findBySid(String employeeID, GeneralDate baseDate) {
		//Lay request 30
		val exportData = syWorkplacePub.findBySid(employeeID, baseDate);
		if(exportData == null){
			return Optional.empty();
		}
		return Optional.of(toImport(syWorkplacePub.findBySid(employeeID, baseDate)));
}


	@Override
	public List<String> getListWorkPlaceIDByDate(GeneralDate date) {
		 List<String> data = syWorkplacePub.findListWorkplaceIdByBaseDate(date);
		return data;
	}
}
