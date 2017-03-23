/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.detailpaymentsalary.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class RowItemDto.
 *
 * @author duongnd
 */

@Setter
@Getter
public class RowItemDto {

    /** The category name. */
    private String categoryName;
    
    /** The employees. */
    private List<EmployeeDto> employees;
    
    /** The departments. */
    private List<DepartmentDto> departments;
    
    /**
     * Calculate total employee.
     *
     * @return the double
     */
    public double calculateTotalEmployee() {
        return this.employees.stream()
                .mapToDouble(employee -> employee.getAmount())
                .sum();
    }
}
