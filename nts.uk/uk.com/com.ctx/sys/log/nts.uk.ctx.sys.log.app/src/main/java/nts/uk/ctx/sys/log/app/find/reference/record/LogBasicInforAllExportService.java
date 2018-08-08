package nts.uk.ctx.sys.log.app.find.reference.record;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.log.app.find.reference.LogOutputItemDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

@Stateless
public class LogBasicInforAllExportService extends ExportService<LogParams> {
	@Inject
	private CSVReportGenerator generator;

	private static final String FILE_EXTENSION = ".csv";
	private static final String PRIMAKY = "primarykey";
	private static final String PGID = "CLI003";

	private List<String> getTextHeader(LogParams params) {
		List<String> lstHeader = new ArrayList<>();
		List<LogOutputItemDto> lstOutputItemDto = params.getLstHeaderDto();
		for (LogOutputItemDto logOutputItemDto : lstOutputItemDto) {
			if (!PRIMAKY.equals(logOutputItemDto.getItemName())) {
				lstHeader.add(logOutputItemDto.getItemName());
			}
		}
		return lstHeader;
	}

	@Override
	protected void handle(ExportServiceContext<LogParams> context) {
		LogParams params = context.getQuery();
		List<Map<String, Object>> dataSource = new ArrayList<>();
		List<String> headers = this.getTextHeader(params);
		dataSource=params.getListDataExport();
		String employeeCode = AppContexts.user().employeeCode();
		CSVFileData fileData = new CSVFileData(
				PGID + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + "_" + employeeCode + FILE_EXTENSION,
				headers, dataSource);

		generator.generate(context.getGeneratorContext(), fileData);
		

	}
}
