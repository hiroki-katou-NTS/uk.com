package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset.StatementItemRangeSetting;

import java.util.Optional;

@Getter
@Setter
public class SettingByItemCustom extends SettingByItem {
    String shortName;
    Optional<DeductionItemDetailSet> deductionItemDetailSet;
    Optional<PaymentItemDetailSet> paymentItemDetailSet;
    Optional<StatementItemRangeSetting> itemRangeSetting;

    public SettingByItemCustom(int itemPosition, String itemId, String shortName,
           DeductionItemDetailSet deductionItemDetailSet, PaymentItemDetailSet paymentItemDetailSet, StatementItemRangeSetting itemRangeSetting) {
        super(itemPosition, itemId);
        this.shortName = shortName;
        this.deductionItemDetailSet = Optional.ofNullable(deductionItemDetailSet);
        this.paymentItemDetailSet = Optional.ofNullable(paymentItemDetailSet);
        this.itemRangeSetting = Optional.ofNullable(itemRangeSetting);
    }
}
