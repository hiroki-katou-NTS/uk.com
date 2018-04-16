package nts.uk.ctx.at.function.app.find.alarm.alarmlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.person.EmployeeInfoFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.person.EmployeeInfoFunAdapterDto;

@Stateless
public class EmployeeInfoFunFinder {

	@Inject
	private EmployeeInfoFunAdapter employeeInfoFun;

	public List<EmployeeSendEmail> getListEmployee(List<EmployeeInfoInput> listEmployeeInput) {
		List<EmployeeSendEmail> result = new ArrayList<EmployeeSendEmail>();

		// 会社IDをもとにImported「社員」を取得する
		List<String> listSid = listEmployeeInput.stream().map(c -> c.getEmployeeID()).collect(Collectors.toList());
		List<EmployeeInfoFunAdapterDto> listEmployeeInfo = employeeInfoFun.getListPersonInfor(listSid);

		// 職場IDとシステム日付をもとに「職場名称」を取得する

		Map<String, String> employeeToWorkplaceId = listEmployeeInput.stream().collect(Collectors.toMap(EmployeeInfoInput::getEmployeeID, e -> e.workplaceID));

		result = listEmployeeInfo.stream().map(e -> EmployeeSendEmail.builder().workplaceId(employeeToWorkplaceId.get(e.getEmployeeId())).workplaceName("").employeeId(e.getEmployeeId())
				.employeeCode(e.getEmployeeCode()).employeeName(e.getBusinessName()).build()).collect(Collectors.toList());
		return result;
	}
}
