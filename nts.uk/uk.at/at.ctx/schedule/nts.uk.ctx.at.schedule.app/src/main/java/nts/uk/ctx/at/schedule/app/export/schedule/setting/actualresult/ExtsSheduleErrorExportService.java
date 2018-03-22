/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.export.schedule.setting.actualresult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.command.processbatch.ErrorContentDto;
import nts.uk.ctx.at.schedule.app.export.budget.external.actualresult.ExtBudgetErrorGenerator;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

/**
 * The Class ExtscheduleErrorExportService.
 */
@Stateless
public class ExtsSheduleErrorExportService  extends ExportService<List<ErrorContentDto>> {

	  /** The generator. */
    @Inject
    private CSVReportGenerator generator;

    
    /** The Constant LST_NAME_ID. */
    private static final List<String> LST_NAME_ID_HEADER = Arrays.asList("KSU007_16", "KSU007_17", "KSU007_18",
            "KSU007_19");
    
    /** The Constant FILE NAM CSV. */
    private static final String FILE_NAME = "個人スケジュール.csv";
    /*
     * (non-Javadoc)
     * @see nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file.export.ExportServiceContext)
     */
	@Override
	protected void handle(ExportServiceContext<List<ErrorContentDto>> context) {
		 
		List<ErrorContentDto> lstError = context.getQuery();
		List<String> header = this.getTextHeader();   
        
        List<Map<String, Object>> dataSource = lstError
        		.stream()
        		.map(errorLine -> {
		        	 Map<String, Object> map = new HashMap<>();
					 map.put(header.get(0), errorLine.getEmployeeCode());
					 map.put(header.get(1), errorLine.getEmployeeName());
					 map.put(header.get(2), errorLine.getDateYMD());
					 map.put(header.get(3), errorLine.getMessage());
					 return map;
		        })
        		.collect(Collectors.toList());
        
        CSVFileData dataExport = new CSVFileData(FILE_NAME, header, dataSource);
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
