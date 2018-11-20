package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.*;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableCode;

import java.util.Optional;

@AllArgsConstructor
@Value
public class PaymentItemDetailCommand {
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

    public PaymentItemDetailSet toDomain() {
        return new PaymentItemDetailSet(histId, salaryItemId, totalObj, proportionalAtr, proportionalMethod, calcMethod,
                calcFomulaCd, personAmountCd, commonAmount, wageTblCode, workingAtr);
    }
}
