package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class EmployeeAdapterInforFinder {
	@Inject
	private EmployeeAdapter employeeInfor;
	
	/**
	 * find employee by workplaceId and base date
	 * @param workplaceIds
	 * @param baseDate
	 * @return
	 */
	public List<EmployeeImport> findEmployeeByWpIdAndBaseDate(List<String> workplaceIds, GeneralDate baseDate){
		return employeeInfor.findByWpkIds(AppContexts.user().companyId(), workplaceIds, baseDate);
	}
}
