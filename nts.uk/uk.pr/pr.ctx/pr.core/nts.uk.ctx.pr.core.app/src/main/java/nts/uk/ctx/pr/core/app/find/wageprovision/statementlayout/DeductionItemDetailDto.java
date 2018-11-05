package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.DeductionItemDetailSet;

@AllArgsConstructor
@Value
public class DeductionItemDetailDto {
    private String histId;
    private String salaryItemId;
    private int totalObj;
    private int proportionalAtr;
    private Integer proportionalMethod;
    private int calcMethod;
    private String calcFormulaCd;
    private String personAmountCd;
    private Long commonAmount;
    private String wageTblCd;
    private String supplyOffset;

    public DeductionItemDetailDto(DeductionItemDetailSet domain) {
        this.histId = domain.getHistId();
        this.salaryItemId = domain.getSalaryItemId();
        this.totalObj = domain.getTotalObj().value;
        this.proportionalAtr = domain.getProportionalAtr().value;
        this.proportionalMethod = domain.getProportionalMethod().map(i -> i.value).orElse(null);
        this.calcMethod = domain.getCalcMethod().value;
        this.calcFormulaCd = domain.getCalcFormulaCd().map(i -> i.v()).orElse(null);
        this.personAmountCd = domain.getPersonAmountCd().map(i -> i.v()).orElse(null);
        this.commonAmount = domain.getCommonAmount().map(i -> i.v()).orElse(null);
        this.wageTblCd = domain.getWageTblCd().map(i -> i.v()).orElse(null);
        this.supplyOffset = domain.getSupplyOffset().orElse(null);
    }
}
