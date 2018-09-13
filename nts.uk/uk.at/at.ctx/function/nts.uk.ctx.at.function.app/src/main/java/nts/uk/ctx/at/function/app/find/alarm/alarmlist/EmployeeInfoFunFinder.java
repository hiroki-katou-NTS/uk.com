package nts.uk.ctx.at.function.app.find.alarm.alarmlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.person.EmployeeInfoFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.person.EmployeeInfoFunAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceIdName;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeInfoFunFinder {

	@Inject
	private EmployeeInfoFunAdapter employeeInfoFun;

	@Inject
	private WorkplaceAdapter workplaceAdapter;

	public List<EmployeeSendEmail> getListEmployee(List<EmployeeInfoInput> listEmployeeInput) {
		if (listEmployeeInput.isEmpty())
			return new ArrayList<EmployeeSendEmail>();


		// 会社IDをもとにImported「社員」を取得する
		List<String> listSid = listEmployeeInput.stream().map(c -> c.getEmployeeID()).collect(Collectors.toList());
		List<EmployeeInfoFunAdapterDto> listEmployeeInfo = employeeInfoFun.getListPersonInfor(listSid);
		Map<String, EmployeeInfoFunAdapterDto> mapEmployeeInfo = listEmployeeInfo.stream()
				.collect(Collectors.toMap(EmployeeInfoFunAdapterDto::getEmployeeId, e -> e));

		// 職場IDとシステム日付をもとに「職場名称」を取得する
		List<String> wkpIds = listEmployeeInput.stream().map(c -> c.getWorkplaceID()).collect(Collectors.toList());
		List<WorkplaceIdName> listWorkplaceInfo = workplaceAdapter.findWkpByWkpId(AppContexts.user().companyId(),
				GeneralDate.today(), wkpIds);

		Map<String, WorkplaceIdName> mapWorkplaceInfo = listWorkplaceInfo.stream()
				.collect(Collectors.toMap(WorkplaceIdName::getWorkplaceId, e -> e));

		return listEmployeeInput.stream()
				.map(e -> new EmployeeSendEmail(e.getWorkplaceID(),
						mapWorkplaceInfo.get(e.getWorkplaceID()) != null ? mapWorkplaceInfo.get(e.getWorkplaceID()).getWorkplaceName() : "", e.getEmployeeID(),
						mapEmployeeInfo.get(e.getEmployeeID()) != null ? mapEmployeeInfo.get(e.getEmployeeID()).getEmployeeCode() : "",
						mapEmployeeInfo.get(e.getEmployeeID()) !=null ? mapEmployeeInfo.get(e.getEmployeeID()).getBusinessName() : ""))
				.collect(Collectors.toList());
	}
}
