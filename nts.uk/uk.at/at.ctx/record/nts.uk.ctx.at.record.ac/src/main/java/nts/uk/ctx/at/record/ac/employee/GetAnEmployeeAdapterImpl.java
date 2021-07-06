package nts.uk.ctx.at.record.ac.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.record.dom.adapter.employee.GetAnEmployeeAdapter;
import nts.uk.ctx.bs.employee.pub.spr.EmployeeSprPub;
import nts.uk.ctx.bs.employee.pub.spr.export.EmpSprExport;

/**
 * 
 * @author chungnt
 *
 */

@Stateless
public class GetAnEmployeeAdapterImpl implements GetAnEmployeeAdapter {

	@Inject
	private EmployeeSprPub employeeSprPub;
	
	@Override
	public Optional<EmployeeImport> get(String cid, String employeeCode) {
		Optional<EmpSprExport> opEmployeeSpr = employeeSprPub.getEmployeeID(cid, employeeCode);
		
		if (opEmployeeSpr.isPresent()) {
			EmployeeImport result = new EmployeeImport(opEmployeeSpr.get().getCompanyID(),
					opEmployeeSpr.get().getEmployeeCD(),
					opEmployeeSpr.get().getEmployeeID(),
					opEmployeeSpr.get().getPersonID(),
					opEmployeeSpr.get().getPerName());
			
			return Optional.of(result);
		}
		
		return Optional.empty();
	}

}
