package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmployeeExtractionReferenceDate;

@AllArgsConstructor
@Value
public class EmployeeExtractionReferenceDateDto {
    /**
     * 参照月
     */
    private int refeMonth;

    /**
     * 参照日
     */
    private int refeDate;


    public static EmployeeExtractionReferenceDateDto fromDomain(EmployeeExtractionReferenceDate domain){
        return new EmployeeExtractionReferenceDateDto(domain.getRefeMonth().value,domain.getRefeDate().value);
    }
}
