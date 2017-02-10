package nts.uk.ctx.basic.app.query.organization.department;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.organization.department.DepartmentFinder;
import nts.uk.ctx.basic.dom.organization.department.DepartmentMemo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DepartmentQueryProcessor {

	@Inject
	private DepartmentFinder departmentFinder;

	public DepartmentQueryResult handle(DepartmentQuery query) {
		DepartmentQueryResult queryResult = new DepartmentQueryResult();
		String companyCode = AppContexts.user().companyCode();
		if (!departmentFinder.checkExist(companyCode)) {
			return queryResult;
		}
		List<DepartmentHistoryDto> histories = departmentFinder.findHistories(companyCode).stream().map(e -> {
			return new DepartmentHistoryDto(e.getHistoryId(), e.getStartDate().date(), e.getEndDate().date());
		}).collect(Collectors.toList());
		if (!histories.isEmpty()) {
			List<DepartmentDto> departments = departmentFinder
					.findAllByHistory(companyCode, histories.get(0).getHistoryId()).stream().map(e -> {
						return new DepartmentDto(e.getDepartmentCode().toString(), e.getExternalCode().toString(),
								e.getFullName().toString(), e.getHierarchyCode().toString(), e.getName().toString());
					}).collect(Collectors.toList());
			queryResult.setDepartments(departments);
			Optional<DepartmentMemo> departmentMemo = departmentFinder.findMemo(companyCode,
					histories.get(0).getHistoryId());
			queryResult.setMemo(departmentMemo.isPresent() ? new DepartmentMemoDto(departmentMemo.get().getHistoryId(),
					departmentMemo.get().getMemo().toString()) : null);
		}
		queryResult.setHistories(histories);
		return queryResult;
	}
}
