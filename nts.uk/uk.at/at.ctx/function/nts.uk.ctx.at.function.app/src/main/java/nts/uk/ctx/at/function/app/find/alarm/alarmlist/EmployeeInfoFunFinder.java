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

		List<String> wkpIds = new ArrayList<>();
		List<String> listSid = new ArrayList<>();
		for (EmployeeInfoInput temp : listEmployeeInput) {
			if (!"".equals(temp.getWorkplaceID())) {
				wkpIds.add(temp.getWorkplaceID());
			}
			if (!"".equals(temp.getEmployeeID())) {
				listSid.add(temp.getEmployeeID());
			}
		}
		// 会社IDをもとにImported「社員」を取得する
		List<EmployeeInfoFunAdapterDto> listEmployeeInfo = employeeInfoFun.getListPersonInfor(listSid);
		Map<String, EmployeeInfoFunAdapterDto> mapEmployeeInfo = listEmployeeInfo.stream()
				.collect(Collectors.toMap(EmployeeInfoFunAdapterDto::getEmployeeId, e -> e));

		// 職場IDとシステム日付をもとに「職場名称」を取得する
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
