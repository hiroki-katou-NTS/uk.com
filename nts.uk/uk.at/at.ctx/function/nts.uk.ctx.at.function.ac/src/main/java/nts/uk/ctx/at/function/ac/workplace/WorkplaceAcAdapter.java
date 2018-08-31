package nts.uk.ctx.at.function.ac.workplace;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceIdName;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.uk.ctx.bs.employee.pub.workplace.WorkPlaceInfoExport;

@Stateless
public class WorkplaceAcAdapter implements WorkplaceAdapter {

	@Inject
	private SyWorkplacePub workplacePub;

	@Override
	public Optional<WorkplaceImport> getWorlkplaceHistory(String employeeId, GeneralDate baseDate) {

		Optional<SWkpHistExport> workplaceHistOpt = workplacePub.findBySid(employeeId, baseDate);
		if (!workplaceHistOpt.isPresent())
			return Optional.empty();
		SWkpHistExport wp = workplaceHistOpt.get();
		return Optional.of(WorkplaceImport.builder().dateRange(wp.getDateRange()).employeeId(wp.getEmployeeId())
				.wkpDisplayName(wp.getWkpDisplayName()).workplaceCode(wp.getWorkplaceCode())
				.workplaceId(wp.getWorkplaceId()).workplaceName(wp.getWorkplaceName()).build());
	}

	@Override
	public List<WorkplaceIdName> findWkpByWkpId(String companyId, GeneralDate baseDate, List<String> wkpIds) {
		List<WorkPlaceInfoExport> listWorkPlaceInfoExport = workplacePub.findWkpByWkpId(companyId, baseDate, wkpIds);
		return listWorkPlaceInfoExport.stream().map(e -> new WorkplaceIdName(e.getWorkplaceId(), e.getWorkPlaceName()))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<WorkplaceImport> getWorlkplaceHistory(List<String> employeeId, GeneralDate baseDate) {
		//TODO: Select by SQL
		return employeeId.stream().map(e -> workplacePub.findBySid(e, baseDate)).filter(wp -> wp.isPresent()).map(wp -> {
			return WorkplaceImport.builder().dateRange(wp.get().getDateRange()).employeeId(wp.get().getEmployeeId())
					.wkpDisplayName(wp.get().getWkpDisplayName()).workplaceCode(wp.get().getWorkplaceCode())
					.workplaceId(wp.get().getWorkplaceId()).workplaceName(wp.get().getWorkplaceName()).build();
		}).collect(Collectors.toList());
	}

	@Override
	public List<WorkplaceImport> getWorlkplaceHistoryByIDs(List<String> employeeIds) {
		List<SWkpHistExport> data = workplacePub.findBySId(employeeIds);
		if(data.isEmpty())
			return Collections.emptyList();
		List<WorkplaceImport> dataConvert = data.stream().map(c->convertToSWkpHistExport(c)).collect(Collectors.toList());
		return dataConvert;
	}
	private WorkplaceImport convertToSWkpHistExport(SWkpHistExport wp) {
		return WorkplaceImport.builder().dateRange(wp.getDateRange()).employeeId(wp.getEmployeeId())
				.wkpDisplayName(wp.getWkpDisplayName()).workplaceCode(wp.getWorkplaceCode())
				.workplaceId(wp.getWorkplaceId()).workplaceName(wp.getWorkplaceName()).build();
	}
	

}
