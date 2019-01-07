package nts.uk.ctx.exio.app.find.exo.execlog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
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
		
		/** The Constant LST_NAME_ID. */
		List<String> listNameIdHead = Arrays.asList(TextResource.localize("CMF002_336"), TextResource.localize("CMF002_337"),
									TextResource.localize("CMF002_338"),TextResource.localize("CMF002_339"));
			
		ErrorContentDto lstError = context.getQuery();
		if (lstError == null) {
			return;
		}
		List<String> header = this.getTextHeader(listNameIdHead);
		String fileName = "エラーログ" + lstError.getNameSetting() + ".csv";
		List<List<String>> resultLogs = new ArrayList<>();
		List<Map<String, Object>> dataSource = new ArrayList<>();

		resultLogs.add(new ArrayList<>(Arrays.asList("出力条件設定", lstError.getResultLog().getNameSetting().toString())));
		resultLogs.add(new ArrayList<>(Arrays.asList(TextResource.localize("CMF002_223"),
				lstError.getResultLog().getSpecifiedStartDate().toString(),
				lstError.getResultLog().getSpecifiedEndDate().toString())));
		resultLogs.add(new ArrayList<>(Arrays.asList(TextResource.localize("CMF002_329"),
				lstError.getResultLog().getProcessStartDateTime().toString())));
		resultLogs.add(new ArrayList<>(
				Arrays.asList(
						TextResource.localize("CMF002_331"), 
						String.valueOf(lstError.getResultLog().getTotalCount()),
						lstError.getResultLog().getProcessUnit())
				));
		int processCount = Arrays.asList(lstError.getErrorLog()).stream().collect(Collectors.groupingBy(x -> x.getProcessCount())).size();
		resultLogs.add(new ArrayList<>(Arrays.asList(TextResource.localize("CMF002_332"),
				String.valueOf(lstError.getResultLog().getTotalCount() - processCount) ,
				lstError.getResultLog().getProcessUnit())));
		resultLogs.add(new ArrayList<>(Arrays.asList(TextResource.localize("CMF002_333"), String.valueOf(lstError.getResultLog().getTotalErrorCount())
				,"件")));

		if (lstError.getErrorLog() != null) { 
			for (int i=0; i< lstError.getErrorLog().length; i++) {
				ExternalOutLogDto errorContentList = lstError.getErrorLog()[i];
				Map<String, Object> errorItem = new HashMap<>();
				//errorItem.put(header.get(0), i);
				errorItem.put(header.get(0), errorContentList.getProcessCount());
				errorItem.put(header.get(1), errorContentList.getErrorItem());
				errorItem.put(header.get(2), errorContentList.getErrorTargetValue());
				String str = errorContentList.getErrorEmployee() != null ? "(" + TextResource.localize("CMF002_356") + errorContentList.getErrorEmployee() + ")" : "";
				errorItem.put(header.get(3), errorContentList.getErrorContent() + str);
				dataSource.add(errorItem);
			}
		}
		ExecLogFileDataCSV dataExport = new ExecLogFileDataCSV(fileName, resultLogs, header, dataSource);
		this.generator.generate(context.getGeneratorContext(), dataExport);
	}
	
	/**
     * Fin header.
     *
     * @return the list
     */
    private List<String> getTextHeader(List<String> listNameIdHead) {
        List<String> lstHeader = new ArrayList<>();
        for (String nameId : listNameIdHead) {
            lstHeader.add(TextResource.localize(nameId));
        }
        return lstHeader;
    }

}
