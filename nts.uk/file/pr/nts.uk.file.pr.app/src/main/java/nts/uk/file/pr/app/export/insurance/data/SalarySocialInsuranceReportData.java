/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.insurance.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class SocialInsuranceReportData.
 *
 * @author duongnd
 */

@Setter
@Getter
public class SalarySocialInsuranceReportData {

    /** The header data. */
    public SalarySocialInsuranceHeaderReportData headerData;
    
    /** The columns.*/
    private List<ColumnInformation> columns;
    
    /** The report item. */
    public List<InsuranceOfficeDto> officeItems;
}