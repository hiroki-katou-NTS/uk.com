package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.DeductionItemDetailSet;

@AllArgsConstructor
@Value
public class DeductionItemDetailCommand {
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

    public DeductionItemDetailSet toDomain() {
        return new DeductionItemDetailSet(histId, salaryItemId, totalObj, proportionalAtr, proportionalMethod,
                calcMethod, calcFormulaCd, personAmountCd, commonAmount, wageTblCd, supplyOffset);
    }
}
