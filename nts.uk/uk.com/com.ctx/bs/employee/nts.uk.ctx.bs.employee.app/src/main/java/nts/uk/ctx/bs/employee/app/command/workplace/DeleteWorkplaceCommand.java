/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class DeleteWorkplaceCommand.
 */
@Data
public class DeleteWorkplaceCommand {

    /** The history id wkp config info. */
    private String historyIdWkpConfigInfo;
    
    /** The start D wkp config info. */
    private GeneralDate startDWkpConfigInfo;
    
    /** The wkp id selected. */
    private String wkpIdSelected;
}
