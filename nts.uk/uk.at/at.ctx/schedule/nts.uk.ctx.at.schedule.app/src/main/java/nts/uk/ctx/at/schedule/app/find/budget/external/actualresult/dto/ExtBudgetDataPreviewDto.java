/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto;

import java.util.List;

import lombok.Builder;

/**
 * The Class DataPreviewDto.
 */
@Builder
public class ExtBudgetDataPreviewDto {
    
    /** The is daily unit. */
    public Boolean isDailyUnit;
    
    /** The data. */
    public List<ExternalBudgetValDto> data;
    
    /** The total record. */
    public Integer totalRecord;
}
