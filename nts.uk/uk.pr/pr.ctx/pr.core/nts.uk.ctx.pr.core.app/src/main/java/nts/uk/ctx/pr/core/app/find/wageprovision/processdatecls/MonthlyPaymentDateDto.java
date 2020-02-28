package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;


import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.MonthlyPaymentDate;

@AllArgsConstructor
@Value
public class MonthlyPaymentDateDto {
    /**
     * 支払日
     */
    private int datePayMent;


    public static MonthlyPaymentDateDto fromDomain(MonthlyPaymentDate domain ){
        return new MonthlyPaymentDateDto(domain.getDatePayMent().value);
    }
}
