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
    	if (listOuput == null) {
    		listOuput = new ArrayList<>();
    	}
    	List<String> listHeader = new ArrayList<>();
    	listHeader.add("KDM002_11");
    	listHeader.add("KDM002_12");
    	listHeader.add("KDM002_13");
    	listHeader.add("KDM002_14");
    	listHeader.add("KDM002_15");
    	listHeader.add("KDM002_16");
    	listHeader.add("KDM002_9");
    	List<Map<String, Object>> dataSource = new ArrayList<>();
    	List<String> header = this.getTextHeader(listHeader);
    	if (!listOuput.isEmpty()) {
	    	List<Integer> sizePlannedVacationListCommand = new ArrayList<>();
	    	for (ExcelInforCommand excelInforCommand : listOuput) {
	    		sizePlannedVacationListCommand.add(excelInforCommand.getPlannedVacationListCommand().size());	 
			}
	    	ExcelInforCommand maxSizeExcelInforCommand = listOuput.get(Collections.max(sizePlannedVacationListCommand));
	    	for (PlannedVacationListCommand plannedVacation : maxSizeExcelInforCommand.getPlannedVacationListCommand()) {
	    		listHeader.add(plannedVacation.getWorkTypeName());
	    		listHeader.add(plannedVacation.getWorkTypeName()+"（上限）");
			}
	    	
	    	List<String> head = this.getTextHeader(listHeader);  
			dataSource = listOuput
					.stream()
	        		.map(infoLine -> {
			        	 Map<String, Object> map = new HashMap<>();
						 map.put(head.get(0), infoLine.getName());
						 map.put(head.get(1), infoLine.getDateStart());
						 map.put(head.get(2), infoLine.getDateEnd());
						 map.put(head.get(3), infoLine.getDateOffYear());
						 map.put(head.get(4), infoLine.getDateTargetRemaining());
						 map.put(head.get(5), infoLine.getDateAnnualRetirement());
						 map.put(head.get(6), infoLine.getDateAnnualRest());
						 for(int i = 0; i < Collections.max(sizePlannedVacationListCommand); i++){
							 map.put(head.get(i+7), infoLine.getPlannedVacationListCommand().get(i).getMaxNumberDays());
							 map.put(head.get(i+7), infoLine.getNumberOfWorkTypeUsedImport().get(i).getAttendanceDaysMonth());
						 }
						 return map;
			        })
	        		.collect(Collectors.toList());
			header = head;
    	}
		

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
