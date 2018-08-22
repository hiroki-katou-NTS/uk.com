package nts.uk.ctx.exio.app.find.exo.execlog;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.exio.app.find.exo.executionlog.ErrorContentDto;
import nts.uk.ctx.exio.dom.exo.execlog.csv.ExecLogFileDataCSV;
import nts.uk.ctx.exio.dom.exo.execlog.csv.ExecLogReportCSVGenerator;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class ExternalOutLogExportService extends ExportService<ErrorContentDto> {

	@Inject
	private ExecLogReportCSVGenerator generator;

	@Override
	protected void handle(ExportServiceContext<ErrorContentDto> context) {
		ErrorContentDto lstError = context.getQuery();
		if (lstError == null) {
			return;
		}
		String fileName = lstError.getNameSetting();
		List<String> resultLog = new ArrayList<>();
		List<String> dataSource = new ArrayList<>();

		resultLog.add("出力条件設定");
		resultLog.add(lstError.getResultLog().getNameSetting().toString());
		resultLog.add(TextResource.localize("CMF002_223"));
		resultLog.add(lstError.getResultLog().getSpecifiedStartDate().toString());
		resultLog.add(lstError.getResultLog().getSpecifiedEndDate().toString());
		resultLog.add(TextResource.localize("CMF002_329"));
		resultLog.add(lstError.getResultLog().getProcessStartDateTime().toString());
		resultLog.add(TextResource.localize("CMF002_331"));
		resultLog.add(lstError.getResultLog().getTotalCount() + "");
		resultLog.add(TextResource.localize("CMF002_332"));
		resultLog.add(lstError.getResultLog().getTotalCount() - lstError.getResultLog().getTotalErrorCount() + "");
		resultLog.add(TextResource.localize("CMF002_333"));
		resultLog.add(lstError.getResultLog().getTotalErrorCount() + "");

		if (lstError.getErrorLog() != null) {
			for (ExternalOutLogDto data : lstError.getErrorLog()) {
				dataSource.add(data.getErrorItem().toString());
				dataSource.add(data.getErrorTargetValue().toString());
				dataSource.add(data.getErrorContent().toString() + "(" + TextResource.localize("CMF002_356")
				+ data.getErrorEmployee() + ")");
			}
		}
		ExecLogFileDataCSV dataExport = new ExecLogFileDataCSV(fileName, resultLog, dataSource);
		this.generator.generate(context.getGeneratorContext(), dataExport);
	}

}
