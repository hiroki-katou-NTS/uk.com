package nts.uk.ctx.at.function.app.find.alarm.alarmlist;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.person.EmployeeInfoFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.person.EmployeeInfoFunAdapterDto;
@Stateless
public class EmployeeInfoFunFinder {
	
	@Inject
	private EmployeeInfoFunAdapter employeeInfoFun;
	
	public void getListEmployee(List<EmployeeInfoInput> listEmployeeInput) {
		//会社IDをもとにImported「社員」を取得する
		List<String> listSid = listEmployeeInput.stream().map(c -> c.getEmployeeID()).collect(Collectors.toList());
		List<EmployeeInfoFunAdapterDto> listEmployeeInfo = employeeInfoFun.getListPersonInfor(listSid);
		//職場IDとシステム日付をもとに「職場名称」を取得する
		
		
	}
}
