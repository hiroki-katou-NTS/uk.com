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
public class ExternalOutLogExportService  extends ExportService<ErrorContentDto> {
	
	@Inject
	private ExecLogReportCSVGenerator generator;
	
    private static final List<String> LST_NAME_ID_HEADER = Arrays.asList("","","","","","");
    private static final String FILE_NAME = "エラー一覧.csv";
    
	@Override
	protected void handle(ExportServiceContext<ErrorContentDto> context) {
		ErrorContentDto lstError = context.getQuery();
    	if (lstError == null) {
    		return;
    	}
    	String nameSetting;
    	List<String> resultLog = new ArrayList<>();
//		List<String> errorLog = new ArrayList<>();
		List<String> header = new ArrayList<>();
		List<Map<String, Object>> dataSource = new ArrayList<>();
		
		nameSetting = lstError.getNameSetting();
		resultLog.add("出力条件設定");
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
			for (int i=0; i< lstError.getErrorLog().length; i++) {
				ExternalOutLogDto errorContentList = lstError.getErrorLog()[i];
				Map<String, Object> errorItem = new HashMap<>();
				errorItem.put(header.get(0), i);
				errorItem.put(header.get(1), errorContentList.getErrorItem());
				errorItem.put(header.get(2), errorContentList.getErrorTargetValue());
				errorItem.put(header.get(3), errorContentList.getErrorContent() + "(" + TextResource.localize("CMF002_356")
								+ errorContentList.getErrorEmployee() + ")");
				dataSource.add(errorItem);
			}
		}
		ExecLogFileDataCSV dataExport = new ExecLogFileDataCSV(FILE_NAME, resultLog, header, dataSource);
		this.generator.generate(context.getGeneratorContext(), dataExport);	
	}
	private List<String> getTextHeader() {
        List<String> lstHeader = new ArrayList<>();
        for (String nameId : LST_NAME_ID_HEADER) {
            lstHeader.add(TextResource.localize(nameId));
        }
        return lstHeader;
    }

}
