package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.*;

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

    public PaymentItemDetailSet toDomain(String cid, String statementCode) {
        return new PaymentItemDetailSet(histId, cid, statementCode, salaryItemId, totalObj, proportionalAtr, proportionalMethod, calcMethod,
                calcFomulaCd, personAmountCd, commonAmount, wageTblCode, workingAtr);
    }
}
