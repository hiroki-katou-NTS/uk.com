package nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

@Stateless
public class ExcelExportService extends ExportService<List<ExcelInforCommand>> {

	/** The generator. */
    @Inject
    private CSVReportGenerator generator;
    
    @Override
	protected void handle(ExportServiceContext<List<ExcelInforCommand>> context) {
    	List<ExcelInforCommand> listOuput = context.getQuery();
    	if (listOuput.isEmpty()) {
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
    	List<Integer> sizePlannedVacationListCommand = new ArrayList<>();
    	for (ExcelInforCommand excelInforCommand : listOuput) {
    		sizePlannedVacationListCommand.add(excelInforCommand.getPlannedVacationListCommand().size());	 
		}
    	ExcelInforCommand maxSizeExcelInforCommand = listOuput.get(Collections.max(sizePlannedVacationListCommand));
    	for (PlannedVacationListCommand plannedVacation : maxSizeExcelInforCommand.getPlannedVacationListCommand()) {
    		listHeader.add(plannedVacation.getWorkTypeName());
    		listHeader.add(plannedVacation.getWorkTypeName()+"（上限）");
		}
    	
    	List<String> header = this.getTextHeader(listHeader);  
		
		List<Map<String, Object>> dataSource = listOuput
				.stream()
        		.map(infoLine -> {
		        	 Map<String, Object> map = new HashMap<>();
					 map.put(header.get(0), infoLine.getName());
					 map.put(header.get(1), infoLine.getDateStart());
					 map.put(header.get(2), infoLine.getDateEnd());
					 map.put(header.get(3), infoLine.getDateOffYear());
					 map.put(header.get(4), infoLine.getDateTargetRemaining());
					 map.put(header.get(5), infoLine.getDateAnnualRetirement());
					 map.put(header.get(6), infoLine.getDateAnnualRest());
					 for(int i = 0; i < Collections.max(sizePlannedVacationListCommand); i++){
						 map.put(header.get(i+7), infoLine.getPlannedVacationListCommand().get(i).getMaxNumberDays());
						 map.put(header.get(i+7), infoLine.getNumberOfWorkTypeUsedImport().get(i).getAttendanceDaysMonth());
					 }
					 return map;
		        })
        		.collect(Collectors.toList());
		
		

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
