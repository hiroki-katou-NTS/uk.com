package nts.uk.ctx.basic.app.query.organization.workplace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.organization.workplace.WorkPlaceFinder;
import nts.uk.ctx.basic.app.query.organization.department.DepartmentDto;
import nts.uk.ctx.basic.dom.organization.department.Department;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlace;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceMemo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkPlaceQueryProcessor {

	@Inject
	private WorkPlaceFinder workPlaceFinder;

	private static final String ROOT = "ROOT";

	public WorkPlaceQueryResult handle(WorkPlaceQuery query) {
		WorkPlaceQueryResult queryResult = new WorkPlaceQueryResult();
		String companyCode = AppContexts.user().companyCode();
		if (!workPlaceFinder.checkExist(companyCode)) {
			return queryResult;
		}
		List<WorkPlaceHistoryDto> histories = workPlaceFinder.findHistories(companyCode).stream().map(e -> {
			return new WorkPlaceHistoryDto(e.getHistoryId(), e.getStartDate().date(), e.getEndDate().date());
		}).collect(Collectors.toList());
		if (!histories.isEmpty()) {
			List<WorkPlace> workPlaces = workPlaceFinder.findAllByHistory(companyCode, histories.get(0).getHistoryId());
			queryResult.setWorkPlaces(workPlaces.isEmpty() ? null : convertToTree(workPlaces));
			Optional<WorkPlaceMemo> workPlaceMemo = workPlaceFinder.findMemo(companyCode,
					histories.get(0).getHistoryId());
			queryResult.setMemo(workPlaceMemo.isPresent()
					? new WorkPlaceMemoDto(workPlaceMemo.get().getHistoryId(), workPlaceMemo.get().getMemo().toString())
					: null);
		}
		queryResult.setHistories(histories);
		return queryResult;
	}

	private List<WorkPlaceDto> convertToTree(List<WorkPlace> workPlaces) {
		Function<WorkPlace, WorkPlaceDto> converter = e -> {
			return new WorkPlaceDto(e.getWorkPlaceCode().toString(), e.getExternalCode().toString(),
					e.getFullName().toString(), e.getHierarchyCode().toString(), e.getName().toString());
		};
		Map<String, List<WorkPlaceDto>> childrens = new HashMap<>();
		childrens.put(ROOT, new ArrayList<>());
		createTree(workPlaces, childrens, converter);
		return childrens.get(ROOT);
	}

	private void createTree(List<WorkPlace> workPlaces, Map<String, List<WorkPlaceDto>> childrens,
			Function<WorkPlace, WorkPlaceDto> converter) {
		WorkPlace dto = workPlaces.remove(0);
		WorkPlaceDto organizationDto = converter.apply(dto);
		childrens.put(dto.getHierarchyCode().toString(), organizationDto.getChildren());

		String inLevelCd = dto.getHierarchyCode().toString();
		if (inLevelCd.length() > 3) {
			int toIndex = inLevelCd.length() - 3;
			String parentInLevelCd = inLevelCd.substring(0, toIndex);

			if (childrens.containsKey(parentInLevelCd)) {
				List<WorkPlaceDto> childrenDepts = childrens.get(parentInLevelCd);
				childrenDepts.add(organizationDto);
			} else {
				childrens.get(ROOT).add(organizationDto);
			}
		} else {
			childrens.get(ROOT).add(organizationDto);
		}
		this.createTree(workPlaces, childrens, converter);

	}

}
