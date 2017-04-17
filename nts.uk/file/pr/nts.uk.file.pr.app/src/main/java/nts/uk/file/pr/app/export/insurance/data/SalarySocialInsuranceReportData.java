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
    
    /** The delivery notice amount. */
    private double deliveryNoticeAmount;
    
    /** The insured collect amount. */
    private double insuredCollectAmount;
    
    /** The child raising total business. */
    private double childRaisingTotalBusiness;
    
    private Boolean isPrintBusiness;
    
    /** The configure output. */
    private ChecklistPrintSettingDto configureOutput;
    
}