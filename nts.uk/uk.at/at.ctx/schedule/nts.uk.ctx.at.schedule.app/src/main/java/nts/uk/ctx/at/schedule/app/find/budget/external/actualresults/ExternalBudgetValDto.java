package nts.uk.ctx.at.schedule.app.find.budget.external.actualresults;

import java.util.List;

import lombok.Setter;

/**
 * The Class ExternalBudgetValDto.
 */
@Setter
public class ExternalBudgetValDto {
    
    /** The code. */
    public String code;
    
    /** The date. */
    public String date;
    
    /** The list value. */
    @SuppressWarnings("rawtypes")
    public List listValue;
    
    /**
     * New external budget val.
     *
     * @param code the code
     * @param date the date
     * @param listValue the list value
     * @return the external budget val dto
     */
    @SuppressWarnings("rawtypes")
    public static ExternalBudgetValDto newExternalBudgetVal(String code, String date, List listValue) {
        ExternalBudgetValDto dto = new ExternalBudgetValDto();
        dto.setCode(code);
        dto.setDate(date);
        dto.setListValue(listValue);
        return dto;
    }
}
