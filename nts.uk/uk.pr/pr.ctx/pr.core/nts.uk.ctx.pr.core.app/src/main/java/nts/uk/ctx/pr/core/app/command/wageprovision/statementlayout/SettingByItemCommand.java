package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItemCustom;

@AllArgsConstructor
@Value
public class SettingByItemCommand {
    private int itemPosition;
    private String itemId;
    private String shortName;
    private PaymentItemDetailCommand paymentItemDetailSet;
    private DeductionItemDetailCommand deductionItemDetailSet;
    private ItemRangeSetCommand itemRangeSet;

    public SettingByItemCustom toDomain(String cid, String statementCode, Integer ctgAtr) {
        return new SettingByItemCustom(itemPosition, itemId, shortName,
                deductionItemDetailSet == null ? null : deductionItemDetailSet.toDomain(cid, statementCode),
                paymentItemDetailSet == null ? null : paymentItemDetailSet.toDomain(cid, statementCode),
                itemRangeSet == null ? null : itemRangeSet.toDomain(cid, statementCode, ctgAtr));
    }
}
