package nts.uk.ctx.at.record.app.command.remainingnumber.checkfunc;

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
public class ExcelExportService extends ExportService<ExcelInforCommand> {

	/** The generator. */
    @Inject
    private CSVReportGenerator generator;
    
    @Override
	protected void handle(ExportServiceContext<ExcelInforCommand> context) {
    	ExcelInforCommand listOuput = context.getQuery();
    	if (listOuput == null) {
    		return;
    	}
    	List<String> listHeader = new ArrayList<>();
    	listHeader.add("氏名");
    	listHeader.add("入社年月日");
    	listHeader.add("退職年月日");
    	listHeader.add("年休付与日");
    	listHeader.add("残数の対象日");
    	listHeader.add("年休付与後残数");
    	listHeader.add("年休残数");
    	
    	for (PlannedVacationListCommand plannedVacation : listOuput.getPlannedVacationListCommand()) {
    		listHeader.add(plannedVacation.getWorkTypeName());
    		listHeader.add(plannedVacation.getWorkTypeName()+"（上限）");
		}
    	
    	List<String> header = this.getTextHeader(listHeader);  
		
		List<Map<String, Object>> dataSource = new ArrayList<>();
		
		Date now = new Date();
    	CSVFileData dataExport = new CSVFileData("", header, dataSource);
        // generate file
        this.generator.generate(context.getGeneratorContext(), dataExport);		
	}
    
    /**
     * Fin header.
     *
     * @return the list
     */
    private List<String> getTextHeader(List<String> listHeader) {
        List<String> lstHeader = new ArrayList<>();
        for (String nameId : listHeader) {
            lstHeader.add(TextResource.localize(nameId));
        }
        return lstHeader;
    }
}
