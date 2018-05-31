package nts.uk.ctx.at.record.ac.employee;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
@Stateless
public class EmployeeRecordAdapterImpl implements EmployeeRecordAdapter{
	@Inject
	private SyEmployeePub employeePub;

	@Override
	public EmployeeRecordImport getPersonInfor(String employeeId) {
		EmployeeBasicInfoExport infor = employeePub.findBySId(employeeId);
		if (Objects.isNull(infor))
			return null;
		EmployeeRecordImport data = new EmployeeRecordImport(infor.getPId(),
				infor.getPName(),
				infor.getEntryDate(),
				infor.getGender(),
				infor.getBirthDay(),
				infor.getEmployeeId(),
				infor.getEmployeeCode(),
				infor.getRetiredDate());
		
		return data;
	}

}
