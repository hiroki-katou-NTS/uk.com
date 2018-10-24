package nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.NumberOfWorkTypeUsedImport;
import nts.uk.ctx.at.request.dom.application.remainingnumer.ExcelInforCommand;
import nts.uk.ctx.at.request.dom.application.remainingnumer.PlannedVacationListCommand;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
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
    	Collections.sort(listOuput, Comparator.comparing(ExcelInforCommand :: getEmployeeCode));
    	if (listOuput == null) {
    		listOuput = new ArrayList<>();
    	}
    	List<String> listHeader = new ArrayList<>();
    	// update ver 6  社員コード　ASC
    	listHeader.add("KDM002_35");
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
	    	
	    	HashMap<String, NumberOfWorkTypeUsedImport>  htbPlanneds = new HashMap<>();
	    	for (int i = 0; i < listOuput.get(0).getPlannedVacationListCommand().size(); i++) {
	    		final String workTypeCode = listOuput.get(0).getPlannedVacationListCommand().get(i).getWorkTypeCode();
	    		for(int j = 0; j < listOuput.size(); j++) {
	    			Optional<NumberOfWorkTypeUsedImport> optNumWTUse = 
	    					listOuput.get(j).getNumberOfWorkTypeUsedImport().stream().filter((item) -> item.getWorkTypeCode().equals(workTypeCode)).findFirst();
	    			if (optNumWTUse.isPresent()) {
	    				htbPlanneds.put(workTypeCode, optNumWTUse.get());
	    				break;
	    			}
	    		}
			}
	    	for (String wtCode :  htbPlanneds.keySet()) {
	    		Optional<PlannedVacationListCommand> opPlanVa = listOuput.get(0).getPlannedVacationListCommand().stream().filter(x -> x.getWorkTypeCode().equals(wtCode)).findFirst();
	    		if (opPlanVa.isPresent()) {
	    			listHeader.add(opPlanVa.get().getWorkTypeName());
	    			listHeader.add(opPlanVa.get().getWorkTypeName()+TextResource.localize("KDM002_34"));
	    		}
			}
	    	List<String> head = this.getTextHeader(listHeader);  
			dataSource = listOuput
					.stream()
	        		.map(infoLine -> {
			        	 Map<String, Object> map = new HashMap<>();
			        	 map.put(head.get(0), infoLine.getEmployeeCode());
						 map.put(head.get(1), infoLine.getName());
						 map.put(head.get(2), infoLine.getDateStart());
						 map.put(head.get(3), infoLine.getDateEnd());
						 map.put(head.get(4), infoLine.getDateOffYear());
						 map.put(head.get(5), infoLine.getDateTargetRemaining());
						 map.put(head.get(6), infoLine.getDateAnnualRetirement());
						 map.put(head.get(7), infoLine.getDateAnnualRest());
						 //for(int i = 0; i < finalMaxSize; i++){
						 int i = 0;
						for (String wtCode :  htbPlanneds.keySet()) {
							Optional<PlannedVacationListCommand> opPlanVa = infoLine.getPlannedVacationListCommand().stream().filter(x -> x.getWorkTypeCode().equals(wtCode)).findFirst();
				    		if (opPlanVa.isPresent()) {
				    			map.put(head.get(i+7), opPlanVa.get().getMaxNumberDays());
				    		}
				    		Optional<NumberOfWorkTypeUsedImport> opNumber = infoLine.getNumberOfWorkTypeUsedImport().stream().filter(x -> x.getWorkTypeCode().equals(wtCode)).findFirst();
				    		if (opNumber.isPresent()) {
								 map.put(head.get(i+8), opNumber.get().getAttendanceDaysMonth());
				    		}
				    		i = i + 2;						 
						 }
						 return map;
			        })
	        		.collect(Collectors.toList());
			header = head;
    	}
		
    	LoginUserContext loginUserContext = AppContexts.user();
		Date now = new Date();
		String fileName = "KDM002_" +new SimpleDateFormat("yyyyMMddHHmmss").format(now.getTime()).toString()+ "_" + loginUserContext.employeeCode() + ".xls";
    	CSVFileData dataExport = new CSVFileData(fileName, header, dataSource);
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
