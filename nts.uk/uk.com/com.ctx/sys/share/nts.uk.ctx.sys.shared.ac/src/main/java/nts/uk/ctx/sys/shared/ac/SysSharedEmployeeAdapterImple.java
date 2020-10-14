package nts.uk.ctx.sys.shared.ac;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoDtoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.sys.shared.dom.adapter.SysSharedEmployeeAdapter;
import nts.uk.ctx.sys.shared.dom.dto.EmployeeImport;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SysSharedEmployeeAdapterImple implements SysSharedEmployeeAdapter {
	
	@Inject
	private EmployeeInfoPub employeeInfoPub;
	
	@Override
	public Optional<EmployeeImport> getByEmployeeCode(String companyId, String employeeCode) {
		// TODO 自動生成されたメソッド・スタブ		
		Optional<EmployeeInfoDtoExport> emExport = employeeInfoPub.getEmployeeInfo(companyId, employeeCode);
		if(!emExport.isPresent()){
			return Optional.empty();
		}
		EmployeeImport emImport = new EmployeeImport(
				emExport.get().getCompanyId(), 
				emExport.get().getPersonId(), 
				emExport.get().getEmployeeId(),
				emExport.get().getEmployeeCode());
		return Optional.of(emImport);
	}

}
