/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.detailpaymentsalary.data;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class EmployeeDto.
 *
 * @author duongnd
 */

@Setter
@Getter
public class EmployeeDto {

    /** The year month. */
    private String yearMonth;

    /** The code. */
    private String code;
    
    /** The name. */
    private String name;
    
    /** The department. */
    private DepartmentDto department;
}
