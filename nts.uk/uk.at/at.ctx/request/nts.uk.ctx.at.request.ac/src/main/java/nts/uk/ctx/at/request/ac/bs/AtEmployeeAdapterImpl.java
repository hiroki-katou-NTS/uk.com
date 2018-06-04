package nts.uk.ctx.at.request.ac.bs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInforImport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class AtEmployeeAdapterImpl implements AtEmployeeAdapter{

	@Inject
	private SyEmployeePub syEmployeePub;
	@Override
	public List<String> getListSid(String sId, GeneralDate baseDate) {
		List<String> lstEmployeeId = syEmployeePub.GetListSid(sId, baseDate);
		return lstEmployeeId == null ? new ArrayList<String>() : lstEmployeeId;
	}
	// Import RequestList228
	@Override
	public List<EmployeeInforImport> getEmployeeInfor(List<String> employeeIDs) {
		List<EmployeeInforImport> result = new ArrayList<>();
		List<EmployeeInfoExport> employeeInforExports = syEmployeePub.getByListSid(employeeIDs);
		for(EmployeeInfoExport emp : employeeInforExports){
			EmployeeInforImport empInfors = new EmployeeInforImport(emp.getSid(), emp.getScd(), emp.getBussinessName());
			result.add(empInfors);
		}
		return result;
	}

}
