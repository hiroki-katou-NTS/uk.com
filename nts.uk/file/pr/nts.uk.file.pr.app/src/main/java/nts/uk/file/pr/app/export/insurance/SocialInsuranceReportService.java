/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.insurance;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.pr.app.export.insurance.data.ColumnInformation;
import nts.uk.file.pr.app.export.insurance.data.SocialInsuranceHeaderReportData;
import nts.uk.file.pr.app.export.insurance.data.SocialInsuranceItemDto;
import nts.uk.file.pr.app.export.insurance.data.SocialInsuranceReportData;

/**
 * @author duongnd
 *
 */

@Stateless
public class SocialInsuranceReportService extends ExportService<SocialInsuranceQuery> {

    @Inject
    SocialInsuranceGenerator generator;
    
    @Override
    protected void handle(ExportServiceContext<SocialInsuranceQuery> context) {
        // get query
        SocialInsuranceQuery query = context.getQuery();
        // get data from repository follow query.
        SocialInsuranceReportData reportData = fakeData();
        this.generator.generate(context.getGeneratorContext(), reportData);
    }
    
    private SocialInsuranceReportData fakeData() {
        SocialInsuranceReportData reportData = new SocialInsuranceReportData();
        
        SocialInsuranceHeaderReportData headerData = new SocialInsuranceHeaderReportData();
        reportData.setHeaderData(headerData);
        
        List<ColumnInformation> columns = new ArrayList<>();
        ColumnInformation column1 = new ColumnInformation();
        column1.setColumnName("Idetified Employee");
        
        ColumnInformation column2 = new ColumnInformation();
        column2.setColumnName("Employee Name");
        
        columns.add(column1);
        columns.add(column2);
        reportData.setColumns(columns);
        
        List<SocialInsuranceItemDto> reportItems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String code = "A0000" + String.valueOf(i + 1);
            String name = "User " + String.valueOf(i + 1);
            SocialInsuranceItemDto item = new SocialInsuranceItemDto();
            item.setCode(code);
            item.setName(name);
            reportItems.add(item);
        }
        
        reportData.setReportItems(reportItems);
        
        return reportData;
    }

}
