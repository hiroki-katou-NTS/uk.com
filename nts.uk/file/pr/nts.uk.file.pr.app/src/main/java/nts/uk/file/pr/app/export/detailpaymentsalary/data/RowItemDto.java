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

// TODO: Auto-generated Javadoc
/**
 * The Class RowItemDto.
 *
 * @author duongnd
 */

/**
 * Sets the amounts.
 *
 * @param amounts the new amounts
 */
@Setter

/**
 * Gets the amounts.
 *
 * @return the amounts
 */
@Getter
public class RowItemDto {
    
    /** The item name. */
    private String itemName;
    
    /** The amounts. */
    private List<DataItem> columItems;
    
    public double calAmountMonthly(String yearMonth) {
        return columItems.stream()
                .filter(p -> p.getYearMonth().equals(yearMonth))
                .mapToDouble(p -> p.getAmount())
                .sum();
    }
}
