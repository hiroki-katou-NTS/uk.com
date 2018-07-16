package nts.uk.ctx.exio.app.find.exo.execlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private static final String FILE_NAME = "エラー一覧.csv";

	@Override
	protected void handle(ExportServiceContext<ErrorContentDto> context) {
		ErrorContentDto lstError = context.getQuery();
		if (lstError == null) {
			return;
		}
		List<String> resultLog = new ArrayList<>();
		List<String> dataSource = new ArrayList<>();

		resultLog.add("出力条件設定");
		resultLog.add(lstError.getResultLog().getNameSetting().toString());
		resultLog.add("処理する年月日");
		resultLog.add(lstError.getResultLog().getSpecifiedStartDate().toString());
		resultLog.add(lstError.getResultLog().getSpecifiedEndDate().toString());
		resultLog.add("処理開始日時");
		resultLog.add(lstError.getResultLog().getProcessStartDateTime().toString());
		resultLog.add("トータル件数");
		resultLog.add(lstError.getResultLog().getTotalCount() + "");
		resultLog.add("正常件数");
		resultLog.add(lstError.getResultLog().getTotalCount() - lstError.getResultLog().getTotalErrorCount() + "");
		resultLog.add("エラー件数");
		resultLog.add(lstError.getResultLog().getTotalErrorCount() + "");

		if (lstError.getErrorLog() != null) {
			for (int i = 0; i < lstError.getErrorLog().length; i++) {
				dataSource.add(lstError.getErrorLog()[i].getErrorItem().toString());
				dataSource.add(lstError.getErrorLog()[i].getErrorTargetValue().toString());
				dataSource.add(lstError.getErrorLog()[i].getErrorContent().toString() + "(" + TextResource.localize("CMF002_356")
				+ lstError.getErrorLog()[i].getErrorEmployee() + ")");
			}
		}
		ExecLogFileDataCSV dataExport = new ExecLogFileDataCSV(FILE_NAME, resultLog, dataSource);
		this.generator.generate(context.getGeneratorContext(), dataExport);
	}

}
