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
 * The Class SocialInsuranceItemDto.
 *
 * @author duongnd
 */

@Setter
@Getter
public class OfficeDto {

    /** The number of employee. */
    private int numberOfEmployee;
    
    /** The office code. */
    private String officeCode;
    
    /** The office name. */
    private String officeName;
    
    /** The employee dto. */
    private List<EmployeeDto> employeeDtos;
    
}
