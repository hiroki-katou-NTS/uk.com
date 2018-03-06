package nts.uk.ctx.exio.app.find.exi.execlog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.app.find.exi.execlog.ErrorContentDto;
import nts.uk.ctx.exio.dom.exi.execlog.csv.ExecLogCSVFileData;
import nts.uk.ctx.exio.dom.exi.execlog.csv.ExecLogCSVReportGenerator;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class ExiExecLogExportService extends ExportService<List<ErrorContentDto>> {

	/** The generator. */
    @Inject
    private ExecLogCSVReportGenerator generator;
    
    /** The Constant LST_NAME_ID. */
    private static final List<String> LST_NAME_ID_HEADER = Arrays.asList("","レコード番号","CSV項目名",
    		"受入項目","値","エラーメッセージ");
    /** The Constant FILE NAM CSV. */
    private static final String FILE_NAME = "エラー一覧.csv";
    
    @Override
	protected void handle(ExportServiceContext<List<ErrorContentDto>> context) {
    	List<ErrorContentDto> lstError = context.getQuery();
    	List<String> header = this.getTextHeader();  
		String nameSetting;
		List<String> condImport = new ArrayList<>();
		List<String> dateTime = new ArrayList<>();
		List<String> totalCount = new ArrayList<>();
		List<String> normalCount = new ArrayList<>();
		List<String> errorCount = new ArrayList<>();
		List<Map<String, Object>> dataSource = new ArrayList<>();
		
    	for (ErrorContentDto errorContentDto : lstError) {
    		nameSetting = errorContentDto.getNameSetting();
    		condImport.add("受入する条件");
    		condImport.add(errorContentDto.getResultLog().getExternalProcessId() + "	"+ nameSetting);
    		dateTime.add("受入開始日時");
    		dateTime.add(errorContentDto.getResultLog().getProcessStartDatetime().toString());
    		totalCount.add("トータル件数");
    		totalCount.add(errorContentDto.getResultLog().getTargetCount()+"");
    		totalCount.add("件");
    		normalCount.add("正常件数");
    		normalCount.add(errorContentDto.getResultLog().getTargetCount() - errorContentDto.getResultLog().getErrorCount() + "" );
    		normalCount.add("件");
    		errorCount.add("エラー件数");
    		errorCount.add(errorContentDto.getResultLog().getErrorCount() + "" );
    		errorCount.add("件");
    		 
    		for (int i=0; i< errorContentDto.getErrorLog().length; i++) {
    			ExacErrorLogDto errorContentList = errorContentDto.getErrorLog()[i];
    			Map<String, Object> errorItem = new HashMap<>();
    			errorItem.put(header.get(0), i);
    			errorItem.put(header.get(1), errorContentList.getRecordNumber());
    			errorItem.put(header.get(2), errorContentList.getCsvErrorItemName());
    			errorItem.put(header.get(3), errorContentList.getItemName());
    			errorItem.put(header.get(4), errorContentList.getCsvAcceptedValue());
    			errorItem.put(header.get(5), errorContentList.getErrorContents());
    			
    			dataSource.add(errorItem);
			}
    		
		}
    	
    	ExecLogCSVFileData dataExport = new ExecLogCSVFileData(FILE_NAME, condImport, dateTime, totalCount, normalCount, errorCount, header, dataSource);
        // generate file
        this.generator.generate(context.getGeneratorContext(), dataExport);		
	}
    /**
     * Fin header.
     *
     * @return the list
     */
    private List<String> getTextHeader() {
        List<String> lstHeader = new ArrayList<>();
        for (String nameId : LST_NAME_ID_HEADER) {
            lstHeader.add(TextResource.localize(nameId));
        }
        return lstHeader;
    }

	

}
