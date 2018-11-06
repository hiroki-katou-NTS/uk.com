package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import lombok.Getter;
import lombok.Setter;
import java.util.Optional;

@Getter
@Setter
public class SettingByItemCustom extends SettingByItem {
    String shortName;
    Optional<DeductionItemDetailSet> deductionItemDetailSet;
    Optional<PaymentItemDetailSet> paymentItemDetailSet;

    public SettingByItemCustom(int itemPosition, String itemId, String shortName,
           DeductionItemDetailSet deductionItemDetailSet, PaymentItemDetailSet paymentItemDetailSet) {
        super(itemPosition, itemId);
        this.shortName = shortName;
        this.deductionItemDetailSet = Optional.ofNullable(deductionItemDetailSet);
        this.paymentItemDetailSet = Optional.ofNullable(paymentItemDetailSet);
    }
}
