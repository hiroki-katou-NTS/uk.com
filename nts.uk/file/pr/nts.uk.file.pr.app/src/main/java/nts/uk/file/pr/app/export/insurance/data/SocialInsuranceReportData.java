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
public class SocialInsuranceReportData {

    /** The header data. */
    public SocialInsuranceHeaderReportData headerData;
    
    /** The columns.*/
    private List<ColumnInformation> columns;
    
    /** The report item. */
    public List<SocialInsuranceItemDto> reportItems;
}