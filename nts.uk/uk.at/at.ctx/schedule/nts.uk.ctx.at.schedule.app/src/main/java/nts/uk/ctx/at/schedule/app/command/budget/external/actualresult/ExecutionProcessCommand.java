/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.budget.external.actualresult;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.LineStart;

/**
 * The Class ExecutionProcessCommand.
 */
@Setter
@Getter
public class ExecutionProcessCommand {
    
    /** The execute id. */
    private String executeId;
    
    /** The external budget code. */
    private String externalBudgetCode;
    
    /** The file id. */
    private String fileId;
    
    /** The file name. */
    private String fileName;
    
    /** The encoding. */
    private Integer encoding;
    
    /** The start line. */
    private LineStart startLine;
    
    /** The is override. */
    private Boolean isOverride;
}
