/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.detailpaymentsalary.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class DepartmentDto.
 *
 * @author duongnd
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DepartmentDto {

    /** The year month. */
    private String yearMonth;
    
    /** The code. */
    private String code;
    
    /** The name. */
    private String name;
    
    /** The dep path. */
    private String depPath;
    
    /** The dep level. */
    private int depLevel;
}
