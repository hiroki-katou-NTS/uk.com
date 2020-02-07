package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.AccountingClosureDate;

@AllArgsConstructor
@Value
public class AccountingClosureDateDto {
    /**
     * 処理月
     */
    private int processMonth;
    /**
     * 処理日
     */
    private int disposalDay;

    public static AccountingClosureDateDto fromDomain(AccountingClosureDate domain) {
        return new AccountingClosureDateDto(domain.getProcessMonth().value, domain.getDisposalDay().value);
    }
}
