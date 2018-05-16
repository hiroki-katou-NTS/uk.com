package nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

@Stateless
public class ErrorInfoExportService extends ExportService<List<OutputErrorInfoCommand>> {

	/** The generator. */
    @Inject
    private CSVReportGenerator generator;
    
    /** The Constant LST_NAME_ID. */
    private static final List<String> LST_NAME_ID_HEADER = Arrays.asList("KDM002_25","KDM002_26","KDM002_27");
    
    @Override
	protected void handle(ExportServiceContext<List<OutputErrorInfoCommand>> context) {
    	List<OutputErrorInfoCommand> lstError = context.getQuery();
    	if (lstError == null) {
    		return;
    	}
    	List<String> header = this.getTextHeader();  
		
		List<Map<String, Object>> dataSource = lstError
        		.stream()
        		.map(errorLine -> {
		        	 Map<String, Object> map = new HashMap<>();
					 map.put(header.get(0), errorLine.getEmployeeCode());
					 map.put(header.get(1), errorLine.getEmployeeName());
					 map.put(header.get(2), errorLine.getErrorMessage());
					 return map;
		        })
        		.collect(Collectors.toList());
		
		LoginUserContext loginUserContext = AppContexts.user();
		Date now = new Date();
		String fileName = "KDM002_" +new SimpleDateFormat("yyyyMMddHHmmss").format(now.getTime()).toString()+ "_"+loginUserContext.employeeCode() + ".csv";
    	CSVFileData dataExport = new CSVFileData(fileName, header, dataSource);
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
