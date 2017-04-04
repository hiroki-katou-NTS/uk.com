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
    
    /** The item name. */
    private String itemName;
    
    /** The amount employees. */
    private List<DataItem> amountEmployees;
    
    /** The amount departments. */
    private List<DataItem> amountDepartments;
    
    /** The amount hierarchy departments. */
    private List<DataItem> amountHierarchyDepartments;
    
    /** The total month. */
    private double totalMonth;
    
    /** The total. */
    private double total;
    
    /**
     * Calculate employee month total.
     *
     * @param yearMonth the year month
     * @return the double
     */
    public double calculateEmployeeMonthTotal(String yearMonth) {
        return this.amountEmployees.stream()
                .filter(item -> item.getYearMonth().equals(yearMonth))
                .mapToDouble(item -> item.getAmount())
                .sum();
    }
    
    /**
     * Calculate employee total.
     *
     * @return the double
     */
    public double calculateEmployeeTotal() {
        return this.amountEmployees.stream()
                .mapToDouble(item -> item.getAmount())
                .sum();
    }
    
    /**
     * Calculate department total.
     *
     * @return the double
     */
    public double calculateDepartmentTotal() {
        return this.amountDepartments.stream()
                .mapToDouble(item -> item.getAmount())
                .sum();
    }
    
    /**
     * Calculate hierarchy department total.
     *
     * @return the double
     */
    public double calculateHierarchyDepartmentTotal() {
        return this.amountHierarchyDepartments.stream()
                .mapToDouble(department -> department.getAmount())
                .sum();
    }
}
