package nts.uk.ctx.at.schedule.app.find.budget.external.actualresults;

import java.util.List;

import lombok.Builder;

/**
 * The Class DataPreviewDto.
 */
@Builder
public class ExtBudgetDataPreviewDto {
    
    /** The data. */
    public List<ExternalBudgetValDto> data;
    
    /** The total record. */
    public Integer totalRecord;
}
