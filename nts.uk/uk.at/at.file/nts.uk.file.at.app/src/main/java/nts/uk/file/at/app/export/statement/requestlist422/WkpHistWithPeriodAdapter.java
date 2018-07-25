package nts.uk.file.at.app.export.statement.requestlist422;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public class WkpHistWithPeriodAdapter {
	
	@Inject
	private WorkplaceRepository workplaceRepo;
	
	@Inject
	private WorkplaceInfoRepository workplaceInfoRepo;
	
	public List<WkpHistWithPeriodExport> getLstHistByWkpsAndPeriod(List<String> wkpIds,
			DatePeriod period) {

		List<Workplace> workplaces = this.workplaceRepo.findWorkplaces(wkpIds, period);

		List<String> historyIds = workplaces.stream()
				.flatMap(item -> item.getWorkplaceHistory().stream())
				.map(WorkplaceHistory::identifier).collect(Collectors.toList());

		List<WorkplaceInfo> optWorkplaceInfos = workplaceInfoRepo.findByHistory(historyIds);

		Map<String, WorkplaceInfo> mapWorkplaceInfo = optWorkplaceInfos.stream()
				.collect(Collectors.toMap(WorkplaceInfo::getHistoryId, Function.identity()));

		return workplaces.stream().map(item -> {
			List<WkpInfoHistExport> wkpInfoHistLst = item.getWorkplaceHistory().stream()
					.map(hist -> {
						WorkplaceInfo info = mapWorkplaceInfo.get(hist.identifier());
						String wkpCode = info.getWorkplaceCode().v();
						String wkpDisplayName = info.getWkpDisplayName().v();
						return WkpInfoHistExport.builder().period(hist.span()).wkpCode(wkpCode)
								.wkpDisplayName(wkpDisplayName).build();
					}).collect(Collectors.toList());
			return WkpHistWithPeriodExport.builder().wkpId(item.getWorkplaceId())
					.wkpInfoHistLst(wkpInfoHistLst).build();
		}).collect(Collectors.toList());
	}
}
