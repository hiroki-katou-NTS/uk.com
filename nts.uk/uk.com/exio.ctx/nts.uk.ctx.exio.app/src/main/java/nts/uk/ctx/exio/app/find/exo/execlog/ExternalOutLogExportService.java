package nts.uk.ctx.exio.app.find.exo.execlog;

import java.util.ArrayList;
import java.util.Arrays;
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

	@Override
	protected void handle(ExportServiceContext<ErrorContentDto> context) {
		
		/** The Constant LST_NAME_ID. */
		List<String> listNameIdHead = Arrays.asList( 
								 "",TextResource.localize("CMF002_336"), TextResource.localize("CMF002_337"),
									TextResource.localize("CMF002_338"),TextResource.localize("CMF002_339"));
		
		ErrorContentDto lstError = context.getQuery();
		if (lstError == null) {
			return;
		}
		List<String> header = this.getTextHeader(listNameIdHead);
		String fileName = lstError.getNameSetting();
		List<String> resultLog = new ArrayList<>();
		List<Map<String, Object>> dataSource = new ArrayList<>();

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

//		if (lstError.getErrorLog() != null) {
//			for (ExternalOutLogDto data : lstError.getErrorLog()) {
//				dataSource.add(data.getErrorItem().toString());
//				dataSource.add(data.getErrorTargetValue().toString());
//				dataSource.add(data.getErrorContent().toString() + "(" + TextResource.localize("CMF002_356")
//						+ data.getErrorEmployee() + ")");
//			}
//		}
		
		if (lstError.getErrorLog() != null) { 
			for (int i=0; i< lstError.getErrorLog().length; i++) {
				ExternalOutLogDto errorContentList = lstError.getErrorLog()[i];
				Map<String, Object> errorItem = new HashMap<>();
				errorItem.put(header.get(0), i);
				errorItem.put(header.get(2), errorContentList.getErrorItem());
				errorItem.put(header.get(3), errorContentList.getErrorTargetValue());
				errorItem.put(header.get(4), errorContentList.getErrorContent() + "(" + TextResource.localize("CMF002_356")
				+ errorContentList.getErrorEmployee() + ")");
				dataSource.add(errorItem);
			}
		}
		ExecLogFileDataCSV dataExport = new ExecLogFileDataCSV(fileName, resultLog, header, dataSource);
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
