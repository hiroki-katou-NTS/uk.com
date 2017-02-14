package nts.uk.ctx.basic.app.query.organization.department;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.organization.department.DepartmentFinder;
import nts.uk.ctx.basic.dom.organization.department.Department;
import nts.uk.ctx.basic.dom.organization.department.DepartmentMemo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DepartmentQueryProcessor {

	@Inject
	private DepartmentFinder departmentFinder;

	private static final String ROOT = "ROOT";

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
			List<Department> departments = departmentFinder.findAllByHistory(companyCode,
					histories.get(0).getHistoryId());
			queryResult.setDepartments(departments.isEmpty() ? null : convertToTree(departments));
			Optional<DepartmentMemo> departmentMemo = departmentFinder.findMemo(companyCode,
					histories.get(0).getHistoryId());
			queryResult.setMemo(departmentMemo.isPresent() ? new DepartmentMemoDto(departmentMemo.get().getHistoryId(),
					departmentMemo.get().getMemo().toString()) : null);
		}
		queryResult.setHistories(histories);
		return queryResult;
	}

	private List<DepartmentDto> convertToTree(List<Department> departments) {
		Function<Department, DepartmentDto> converter = e -> {
			return new DepartmentDto(e.getDepartmentCode().toString(), e.getExternalCode().toString(),
					e.getFullName().toString(), e.getHierarchyCode().toString(), e.getName().toString());
		};
		Map<String, List<DepartmentDto>> childrens = new HashMap<>();
		childrens.put(ROOT, new ArrayList<>());
		createTree(departments, childrens, converter);
		return childrens.get(ROOT);
	}

	private void createTree(List<Department> departments, Map<String, List<DepartmentDto>> childrens,
			Function<Department, DepartmentDto> converter) {
		Department dto = departments.remove(0);
		DepartmentDto organizationDto = converter.apply(dto);
		childrens.put(dto.getHierarchyCode().toString(), organizationDto.getChildren());

		String inLevelCd = dto.getHierarchyCode().toString();
		if (inLevelCd.length() > 3) {
			int toIndex = inLevelCd.length() - 3;
			String parentInLevelCd = inLevelCd.substring(0, toIndex);

			if (childrens.containsKey(parentInLevelCd)) {
				List<DepartmentDto> childrenDepts = childrens.get(parentInLevelCd);
				childrenDepts.add(organizationDto);
			} else {
				childrens.get(ROOT).add(organizationDto);
			}
		} else {
			childrens.get(ROOT).add(organizationDto);
		}
		this.createTree(departments, childrens, converter);

	}
}
