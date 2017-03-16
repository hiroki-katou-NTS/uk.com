/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.insurance.salary;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.file.pr.app.export.insurance.data.InsuranceOfficeDto;

/**
 * The Class SocialInsuranceQuery.
 *
 * @author duongnd
 */

@Setter
@Getter
public class SalarySocialInsuranceQuery {

    /** The year month. */
    private String yearMonth;
    
    /** The is equal. */
    private boolean isEqual;
    
    /** The is deficient. */
    private boolean isDeficient;
    
    /** The is redundant. */
    private boolean isRedundant;
    
    /** The insurance offices. */
    private List<InsuranceOfficeDto> insuranceOffices;
}
