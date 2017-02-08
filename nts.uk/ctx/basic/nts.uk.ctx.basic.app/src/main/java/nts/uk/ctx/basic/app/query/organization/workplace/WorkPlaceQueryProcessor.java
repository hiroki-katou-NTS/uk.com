package nts.uk.ctx.basic.app.query.organization.workplace;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.organization.workplace.WorkPlaceFinder;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceMemo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkPlaceQueryProcessor {

	@Inject
	private WorkPlaceFinder workPlaceFinder;

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
			List<WorkPlaceDto> workPlaces = workPlaceFinder
					.findAllByHistory(companyCode, histories.get(0).getHistoryId()).stream().map(e -> {
						return new WorkPlaceDto(e.getWorkPlaceCode().toString(), e.getExternalCode().toString(),
								e.getFullName().toString(), e.getHierarchyCode().toString(), e.getName().toString());
					}).collect(Collectors.toList());
			queryResult.setWorkPlaces(workPlaces);
			Optional<WorkPlaceMemo> workPlaceMemo = workPlaceFinder.findMemo(companyCode,
					histories.get(0).getHistoryId());
			queryResult.setMemo(workPlaceMemo.isPresent()
					? new WorkPlaceMemoDto(workPlaceMemo.get().getHistoryId(), workPlaceMemo.get().getMemo().toString())
					: null);
		}
		queryResult.setHistories(histories);
		return queryResult;
	}

}
