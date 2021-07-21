package nts.uk.ctx.sys.gateway.ac.find.login;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.spr.EmployeeSprPub;
import nts.uk.ctx.bs.employee.pub.spr.export.EmpSprExport;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.getaneemployee.GetAnEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.getaneemployee.GetAnEmployeeImported;

/**
 * 
 * @author chungnt
 *
 */

public class GetAnEmployeeAdapterImp implements GetAnEmployeeAdapter {

	@Inject
	private EmployeeSprPub employeeSprPub;

	@Override
	public Optional<GetAnEmployeeImported> getEmployee(String cid, String employeeCode) {
		Optional<EmpSprExport> opEmployeeSpr = employeeSprPub.getEmployeeID(cid, employeeCode);

		if (opEmployeeSpr.isPresent()) {
			GetAnEmployeeImported result = new GetAnEmployeeImported(opEmployeeSpr.get().getCompanyID(),
					opEmployeeSpr.get().getPersonID(), opEmployeeSpr.get().getEmployeeID(),
					opEmployeeSpr.get().getEmployeeCD());

			return Optional.of(result);
		}

		return Optional.empty();
	}

}
