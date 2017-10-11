/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class WorkplaceStateDto.
 */
@NoArgsConstructor
@Data
public class WorkplaceStateDto {

    /** The is less max hierarchy. */
    private boolean isLessMaxHierarchy;
    
    /** The is less max siblings. */
    private boolean isLessMaxSiblings;
}
