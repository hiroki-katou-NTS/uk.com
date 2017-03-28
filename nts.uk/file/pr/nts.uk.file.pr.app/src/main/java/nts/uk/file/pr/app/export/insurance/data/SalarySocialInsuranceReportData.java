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
    private SalarySocialInsuranceHeaderReportData headerData;
    
    /** The report item. */
    private List<InsuranceOfficeDto> officeItems;
    
    /** The total all office. */
    private DataRowItem totalAllOffice;
    
    /** The configure output. */
    private ChecklistPrintSettingDto configureOutput;
}