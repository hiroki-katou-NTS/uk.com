package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.PaymentItemDetailSet;

@AllArgsConstructor
@Value
public class PaymentItemDetailDto {
    private String histId;
    private String salaryItemId;
    private int totalObj;
    private int proportionalAtr;
    private Integer proportionalMethod;
    private int calcMethod;
    private String calcFomulaCd;
    private String personAmountCd;
    private Long commonAmount;
    private String wageTblCode;
    private Integer workingAtr;

    PaymentItemDetailDto(PaymentItemDetailSet domain) {
        this.histId = domain.getHistId();
        this.salaryItemId = domain.getSalaryItemId();
        this.totalObj = domain.getTotalObj().value;
        this.proportionalAtr = domain.getProportionalAtr().value;
        this.proportionalMethod = domain.getProportionalMethod().map(i -> i.value).orElse(null);
        this.calcMethod = domain.getCalcMethod().value;
        this.calcFomulaCd = domain.getCalcFomulaCd().map(i -> i.v()).orElse(null);
        this.personAmountCd = domain.getPersonAmountCd().map(i -> i.v()).orElse(null);
        this.commonAmount = domain.getCommonAmount().map(i -> i.v()).orElse(null);
        this.wageTblCode = domain.getWageTblCode().map(i -> i.v()).orElse(null);
        this.workingAtr = domain.getWorkingAtr().map(i -> i.value).orElse(null);
    }
}
