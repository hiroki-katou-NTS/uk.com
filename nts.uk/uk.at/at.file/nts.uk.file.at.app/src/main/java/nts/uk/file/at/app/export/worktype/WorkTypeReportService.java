package nts.uk.file.at.app.export.worktype;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.at.app.export.worktype.data.WorkTypeReport;
import nts.uk.file.at.app.export.worktype.data.WorkTypeReportData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkTypeReportService extends ExportService<WorkTypeReportQuery> {
	@Inject
	private WorkTypeGenerator generate;
	@Inject
	private WorkTypeReportRepository  workTypeReportRepository;
	
	
	@Override
	protected void handle(ExportServiceContext<WorkTypeReportQuery> context) {
		
		String companyId = AppContexts.user().companyId();
		
		List<WorkTypeReportData> listWorkTypeReport = workTypeReportRepository.findAllWorkType(companyId)
				.stream()
				.sorted(Comparator.comparing(WorkTypeReportData::getDispOrder, Comparator.nullsLast(Integer::compareTo)))
				.collect(Collectors.toList());
		
		WorkTypeReport dataReport = new WorkTypeReport();
		dataReport.setData(listWorkTypeReport);
		
		this.generate.generate(context.getGeneratorContext(), dataReport);
	}


}
