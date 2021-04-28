/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.LineStart;

/**
 * The Class ExtBudgetExtractCondition.
 */
@Setter
@Getter
public class ExtBudgetExtractCondition {
    
    /** The external budget code. */
    private String externalBudgetCode;
    
    /** The file id. */
    private String fileId;
    
    /** The encoding. */
    private Integer encoding;
    
    /** The start line. */
    private LineStart startLine;
    
    /** The is override. */
    private Boolean isOverride;
}
