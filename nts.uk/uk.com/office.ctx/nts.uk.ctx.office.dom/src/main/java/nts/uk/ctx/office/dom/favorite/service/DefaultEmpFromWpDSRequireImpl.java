package nts.uk.ctx.office.dom.favorite.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeBelongWorkplaceAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeWorkplaceIdAdapter;
import nts.uk.ctx.office.dom.favorite.service.EmployeeListFromWpDomainService.Require;

@AllArgsConstructor
public class DefaultEmpFromWpDSRequireImpl implements Require {
	@Inject
	private EmployeeWorkplaceIdAdapter employeeWorkplaceIdAdapter;

	@Inject
	private EmployeeBelongWorkplaceAdapter employeeBelongWorkplaceAdapter;

	@Override
	public Map<String, String> getWorkplaceId(List<String> sIds, GeneralDate baseDate) {
		return this.employeeWorkplaceIdAdapter.getWorkplaceId(sIds, baseDate);
	}

	@Override
	public List<String> acquireToTheWorkplace(List<String> wkps, GeneralDate baseDate) {
		return this.employeeBelongWorkplaceAdapter.getEmployeeByWplAndBaseDate(wkps, baseDate);
	}
}